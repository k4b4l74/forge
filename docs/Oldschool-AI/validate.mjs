import assert from 'node:assert/strict';
import {readFileSync, readdirSync, existsSync} from 'node:fs';
import {dirname, resolve, join} from 'node:path';
import {fileURLToPath} from 'node:url';

const directory = dirname(fileURLToPath(import.meta.url));
const root = resolve(directory, '../..');
const read = filename => readFileSync(filename, 'utf8').replace(/^\uFEFF/, '');
const rules = JSON.parse(read(join(directory, 'rules.json')));
const catalog = JSON.parse(read(join(directory, 'catalog.json')));
const eligible = new Set();
const definitions = new Map();
const errors = [];
let handCount = 0;
let swapCount = 0;

function collectCards(folder) {
    for (const entry of readdirSync(folder, {withFileTypes: true})) {
        const filename = join(folder, entry.name);
        if (entry.isDirectory()) {
            collectCards(filename);
        } else if (entry.name.endsWith('.txt')) {
            const definition = read(filename);
            for (const match of definition.matchAll(/^Name:(.+)$/gm)) {
                definitions.set(match[1].trim(), definition);
            }
        }
    }
}

for (const edition of rules.editions) {
    let inCards = false;
    for (const line of read(join(root, 'forge-gui/res/editions', `${edition}.txt`)).split(/\r?\n/)) {
        if (line.startsWith('[')) {
            inCards = line.trim() === '[cards]';
        } else if (inCards) {
            const match = /^\S+\s+[A-Z]\s+(.+?)(?:\s+@.*)?$/.exec(line.trim());
            if (match) {
                eligible.add(match[1]);
            }
        }
    }
}
collectCards(join(root, 'forge-gui/res/cardsfolder'));

function quantities(value) {
    const result = new Map();
    if (value.trim() === '-') {
        return result;
    }
    for (const entry of value.split(/[;\n]/).map(part => part.trim()).filter(Boolean)) {
        const match = /^(\d+) (.+)$/.exec(entry);
        assert(match, `Malformed card entry: ${entry}`);
        const count = Number(match[1]);
        assert(count > 0, `Nonpositive count: ${entry}`);
        result.set(match[2], (result.get(match[2]) || 0) + count);
    }
    return result;
}

function total(cards) {
    return [...cards.values()].reduce((sum, count) => sum + count, 0);
}

function available(cards, pool, context) {
    for (const [name, count] of cards) {
        assert((pool.get(name) || 0) >= count, `${context}: unavailable ${count} ${name}`);
    }
}

function validateDeck(main, sideboard) {
    assert.equal(total(main), rules.minimumMain, 'Reference main must have exactly 60 cards');
    assert.equal(total(sideboard), rules.maximumSideboard, 'Reference sideboard must have exactly 15 cards');
    for (const name of new Set([...main.keys(), ...sideboard.keys()])) {
        assert(definitions.has(name), `Unknown Forge card: ${name}`);
        assert(eligible.has(name), `Outside card pool: ${name}`);
        assert(!rules.banned.includes(name), `Banned card: ${name}`);
        const count = (main.get(name) || 0) + (sideboard.get(name) || 0);
        const limit = rules.basicLands.includes(name) ? Infinity : rules.restricted.includes(name) ? 1 : 4;
        assert(count <= limit, `${name}: ${count} copies exceeds ${limit}`);
    }
}

function table(document, heading) {
    const start = document.indexOf(`## ${heading}\n`);
    assert(start >= 0, `Missing section: ${heading}`);
    const remainder = document.slice(start + heading.length + 4);
    const end = remainder.indexOf('\n## ');
    return (end < 0 ? remainder : remainder.slice(0, end)).split('\n')
        .filter(line => line.startsWith('|'))
        .slice(2)
        .map(line => line.split('|').slice(1, -1).map(cell => cell.trim()));
}

const expectedFamilies = {Aggro: 13, Midrange: 8, Control: 9, Combo: 13, Prison: 7};
assert.equal(catalog.length, 50, 'Expected all 50 Wak-Wak archetypes');
assert.equal(new Set(catalog.map(entry => entry.id)).size, 50, 'Duplicate ID');
assert.equal(new Set(catalog.map(entry => entry.source)).size, 50, 'Duplicate archetype source');
for (const [family, count] of Object.entries(expectedFamilies)) {
    assert.equal(catalog.filter(entry => entry.family === family).length, count, family);
}

for (const entry of catalog) {
    try {
        assert(/^[a-z0-9]+(?:-[a-z0-9]+)*$/.test(entry.id), 'Unstable ID format');
        assert.equal(entry.file, `oldschool-${entry.id}.md`, 'Filename/ID mismatch');
        assert(entry.source.startsWith('https://www.wak-wak.se/9394decks/'), 'Unexpected source');
        assert(Array.isArray(entry.aliases), 'Missing aliases');
        const document = read(join(directory, entry.file)).replace(/\r\n/g, '\n');
        assert(document.startsWith(`# ${entry.name}\n`), 'Title/catalog mismatch');
        assert(document.includes(entry.source), 'Missing archetype citation');
        assert(document.includes('Authored reference fixture'), 'Missing fixture provenance');
        const blocks = [...document.matchAll(/```forge-deck\n([\s\S]*?)\n```/g)];
        assert.equal(blocks.length, 1, 'Expected one reference fixture');
        const sections = /^\[Main\]\n([\s\S]*?)\n\[Sideboard\]\n([\s\S]+)$/.exec(blocks[0][1]);
        assert(sections, 'Malformed reference sections');
        const main = quantities(sections[1]);
        const sideboard = quantities(sections[2]);
        validateDeck(main, sideboard);
        const hands = table(document, 'Opening hands');
        assert(hands.length >= 6, 'Need six opening cases');
        assert.equal(new Set(hands.map(row => row[0])).size, hands.length, 'Duplicate hand ID');
        assert(hands.some(row => row[4].startsWith('Mulligan')), 'Missing rejected hand');
        assert(hands.some(row => row[2] === '1' && row[4].startsWith('Keep')), 'Missing retained six');
        assert(hands.some(row => row[2] === '2' && row[4].startsWith('Keep')), 'Missing retained five');
        for (const row of hands) {
            assert.equal(row.length, 7, 'Opening table columns');
            const cards = quantities(row[3]);
            const bottom = quantities(row[5]);
            assert.equal(total(cards), 7, `${row[0]}: must display seven cards`);
            available(cards, main, row[0]);
            available(bottom, cards, `${row[0]} bottom`);
            assert(/^[012]$/.test(row[2]), 'Unexpected mulligan depth');
            assert(/^(Keep|Mulligan)/.test(row[4]), 'Unknown mulligan decision');
            assert.equal(total(bottom), row[4].startsWith('Keep') ? Number(row[2]) : 0, `${row[0]}: bottom count`);
            handCount++;
        }
        const swaps = table(document, 'Sideboard plans');
        assert(swaps.length >= 2, 'Need two sideboard plans');
        for (const row of swaps) {
            assert.equal(row.length, 4, 'Sideboard table columns');
            const incoming = quantities(row[1]);
            const outgoing = quantities(row[2]);
            assert(total(incoming) > 0, 'Empty sideboard plan');
            assert.equal(total(incoming), total(outgoing), 'Unbalanced swap');
            available(incoming, sideboard, 'Sideboard in');
            available(outgoing, main, 'Sideboard out');
            const postMain = new Map(main);
            const postSide = new Map(sideboard);
            for (const [name, count] of incoming) {
                postMain.set(name, (postMain.get(name) || 0) + count);
                postSide.set(name, postSide.get(name) - count);
            }
            for (const [name, count] of outgoing) {
                postMain.set(name, postMain.get(name) - count);
                postSide.set(name, (postSide.get(name) || 0) + count);
            }
            validateDeck(postMain, postSide);
            swapCount++;
        }
        const matchups = table(document, 'Matchup plans');
        for (const family of Object.keys(expectedFamilies)) {
            assert(matchups.some(row => row[0] === family), `Missing matchup family: ${family}`);
        }
        assert(table(document, 'Decision rules').length >= 3, 'Need three decision rules');
        assert((document.match(/^- \*\*S\d+\*\*/gm) || []).length >= 2, 'Need two acceptance scenarios');
    } catch (error) {
        errors.push(`${entry.id}: ${error.message}`);
    }
}

for (const filename of readdirSync(directory).filter(name => name.endsWith('.md'))) {
    const document = read(join(directory, filename));
    for (const match of document.matchAll(/\]\(([^)]+)\)/g)) {
        const link = match[1];
        if (!/^(https?:|#)/.test(link)) {
            const target = decodeURIComponent(link.split('#')[0]);
            if (!existsSync(resolve(directory, target))) {
                errors.push(`${filename}: broken local link ${link}`);
            }
        }
    }
}

if (errors.length) {
    console.error(errors.join('\n'));
    process.exitCode = 1;
} else {
    console.log(`Validated ${catalog.length} archetypes, ${handCount} opening cases, ${swapCount} sideboard plans, local links and card-pool/copy limits.`);
    console.log('Static documentation validation only; no games or Java acceptance tests executed.');
}
