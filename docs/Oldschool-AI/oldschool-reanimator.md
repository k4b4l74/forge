# Reanimator

[Library](Oldschool-AI.md) · [Rules](Oldschool-Rules.md) · [Engine gaps](Oldschool-Implementation.md)

ID: `reanimator` · Family: **Combo** · Rules: `swedish-fe-burn-london-v1`
Aliases: Reanimation, Bazaar Reanimator.

## Identity and construction

Put a high-impact creature in the graveyard, then convert a cheap reanimation spell into board advantage. Outlet, target, reanimation and colored mana are separate components.

For the submitted deck, see [KabaL Reanimator combos and sequencing](#kabal-reanimator-combos-and-sequencing).
That analysis is separate from the BGR reference fixture below.
The opt-in [OS Reanimator runtime profile](OS-Reanimator-AI.md) implements a
documented subset of these ideas; the guide itself is not executable AI.

**Authored reference fixture v1 (60 + 15)**, created for these decision examples.
The linked [Wak-Wak archetype overview](https://www.wak-wak.se/9394decks/reanimator) supports the identity, not
this exact list or the proposed lines below. This is an educational candidate,
not a verified tournament winner or an optimized configuration. Saved user
decks are unchanged. The fixture deliberately fixes one variant; do not silently
transfer its sideboard quantities to another list.

The BGR fixture uses Bazaar/Jalum, Animate Dead and All Hallow's Eve. It excludes the imported Earthquake Dragon because it is outside this pool; Colossus is legal but has a real untap restriction.

```forge-deck
[Main]
4 Animate Dead
3 All Hallow's Eve
4 Triskelion
2 Shivan Dragon
2 Colossus of Sardia
3 Jalum Tome
4 Birds of Paradise
4 Dark Ritual
2 Terror
1 Demonic Tutor
1 Mind Twist
1 Regrowth
1 Sol Ring
1 Mox Jet
1 Mox Emerald
1 Black Lotus
1 Chaos Orb
4 Bazaar of Baghdad
4 Bayou
4 Badlands
4 City of Brass
1 Strip Mine
4 Swamp
3 Forest
[Sideboard]
3 Red Elemental Blast
3 Crumble
3 Tranquility
2 Terror
2 Tormod's Crypt
2 Avoid Fate
```

## Mulligan priorities

A functional hand needs a way to put a useful target in the graveyard and cast reanimation, or a credible hard-cast plan. Bazaar is a card-selection outlet, not a land that produces mana.
Evaluate retained cards, colored development, opponent speed, and play/draw.
The no-mana and flood controls are rejected at seven, not automatic rules for
every desperate five. H5/H6 revisit H1/H2 after one/two mulligans to specify
bottoming rather than assuming every seven-card keep remains a good five.

## Opening hands

| ID | Seat / opponent | Mulligans | Seven cards | Decision | Bottom | Reason |
| --- | --- | --- | --- | --- | --- | --- |
| H1 | Play / unknown | 0 | 1 Bazaar of Baghdad;1 Bayou;1 Swamp;1 Birds of Paradise;1 Animate Dead;1 Triskelion;1 Jalum Tome | Keep | - | Real mana plus outlet, target and reanimation are present. |
| H2 | Draw / unknown | 0 | 1 Bazaar of Baghdad;1 Bayou;1 Swamp;1 Animate Dead;1 Triskelion;1 Terror;1 Shivan Dragon | Keep | - | The main line is assembled while Terror can buy time. |
| H3 | Play or draw / unknown | 0 | 4 Animate Dead; 3 All Hallow's Eve | Mulligan | - | No reusable mana or functioning development sequence; free spells alone are not a plan. |
| H4 | Play or draw / unknown | 0 | 4 Bayou; 3 Badlands | Mulligan | - | No pressure, interaction, or draw engine; a seventh land is not a substitute for action. |
| H5 | Play / unknown | 1 | 1 Bazaar of Baghdad;1 Bayou;1 Swamp;1 Birds of Paradise;1 Animate Dead;1 Triskelion;1 Jalum Tome | Keep six | 1 Jalum Tome | Bazaar already supplies the outlet; retain the complete core. |
| H6 | Draw / unknown | 2 | 1 Bazaar of Baghdad;1 Bayou;1 Swamp;1 Animate Dead;1 Triskelion;1 Terror;1 Shivan Dragon | Keep five | 1 Shivan Dragon;1 Terror | At five retain the actual outlet/mana/target/reanimation package. |

## Sequencing and resources

- **Early:** Plan the discard before activating Bazaar: draw two and discard three is card disadvantage, not free Ancestral Recall.
- **Middle:** Choose the target fitting the board. Triskelion gives immediate removal, Dragon is evasive, while Colossus can become stranded tapped after one attack.
- **Late:** Animate Dead removal causes sacrifice, not a regeneration opportunity. All Hallow's Eve is delayed and can return opposing creatures too.
- **Mana burn:** Ritual can accelerate Eve but unused BBB still burns. Reanimating Su-Chi in a variant introduces mandatory death mana; do not carry that assumption into every target.

## Matchup plans

These are proposed roles, not a ranking or measured matchup percentages.

| Opposing family | Role and priorities |
| --- | --- |
| Aggro | Reanimate a stabilizer when racing would lose; immediate Triskelion impact can beat raw size. |
| Midrange | Favor evasion or immediate value over a large creature that is easily chumped or stolen. |
| Control | Protect the relevant spell and expect Swords/Disenchant; hard-cast routes can punish overfocus on the graveyard. |
| Combo | Crypt timing and the faster real clock matter more than maximum creature size. |
| Prison | A lock may prevent casting reanimation but not graveyard filling; don't confuse an available outlet with a winning route. |

### Named exceptions and common mistakes

- The Deck: Swords bypasses recursion by exiling; Disenchant on Animate Dead creates a sacrifice instruction.
- KabaL Reanimator: Rasputin, Copy Artifact and blue spells form a distinct value variant. These BGR cuts are not instructions for that saved deck.

## Sideboard plans

Apply **one row from the original fixture**, after confirming the opposing
build. Each row preserves 60 main and 15 sideboard cards. These are authored
starting hypotheses, not prescribed swaps for all members of a family. On the
other seat, reassess curve and tempo before changing quantities; do not mix rows.

| Opponent / seat | In | Out | Reason |
| --- | --- | --- | --- |
| The Deck / play | 3 Red Elemental Blast;2 Avoid Fate | 2 Terror;2 All Hallow's Eve;1 Colossus of Sardia | Reduce delayed/symmetric or narrow cards and contest relevant answers; Fate does not protect a spell on the stack. |
| Artifact Aggro / draw | 3 Crumble | 2 All Hallow's Eve;1 Colossus of Sardia | Gain early interaction instead of waiting for a delayed mass return. |

## Decision rules

Legality and forced survival override these preferences. Conditions use only
your hand and public or legitimately revealed information; no hidden-hand or
future-library inspection. `R` denotes a rules constraint; `H` denotes an
untested heuristic. A high tactical priority is not high empirical confidence.

| Priority / ID | Observable condition | Preferred action | Exception / guard | Rationale / evidence |
| --- | --- | --- | --- | --- |
| 100 / reanimator-D1 | Bazaar is counted in an opening mana plan | Assign no mana production | Its activation still supplies selection/discard | Land type alone does not mean mana source (R) |
| 90 / reanimator-D2 | Several creatures can be reanimated | Choose impact and survival fit rather than highest mana value | Immediate lethal or a unique blocker can change preference | Size is not the only payoff (H) |
| 80 / reanimator-D3 | Animate Dead is about to leave the battlefield | Plan for the required sacrifice | Regeneration cannot replace sacrifice | Aura removal attacks the creature indirectly (R) |

## Acceptance scenarios

Proposed regression specifications, **not executed engine tests**. Use the
shared rules contract, ordinary priority, and no other relevant effects unless
stated; implementation must additionally exercise adversarial responses.
Opening cases H1-H6 and both sideboard rows are further acceptance inputs.

- **S1** A Bazaar-only opening hand has no mana production. Do not count Bazaar as the black source for Animate Dead.
- **S2** Colossus has attacked and tapped. Without paying its upkeep-only untap cost or another untap effect, it does not naturally untap next turn.

## KabaL Reanimator combos and sequencing

Reviewed 2026-09-27 against the submitted list and the local saved deck
`data/profile/decks/constructed/[Reanimator] - KabaL Reanimator.dck`.
The following snapshot preserves that deck, not the authored fixture above:

| Package | Cards in the submitted main deck |
| --- | --- |
| Creatures (14) | 3 Birds of Paradise; 4 Erhnam Djinn; 3 Rasputin Dreamweaver; 4 Triskelion |
| Lands (18) | 4 Bayou; 3 Bazaar of Baghdad; 4 City of Brass; 4 Tropical Island; 3 Underground Sea |
| Mana artifacts (7) | 1 Black Lotus; 1 Sol Ring; 1 each of Mox Emerald, Jet, Pearl, Ruby and Sapphire |
| Reanimation and copying (7) | 4 Animate Dead; 3 Copy Artifact |
| Interaction (6) | 1 Balance; 1 Chaos Orb; 1 Mana Drain; 3 Psionic Blast |
| Cards, recursion and extra turn (8) | 1 Ancestral Recall; 1 Braingeyser; 1 Demonic Tutor; 1 Recall; 1 Regrowth; 1 Time Walk; 1 Timetwister; 1 Wheel of Fortune |

Sideboard (15): **2 Blue Elemental Blast; 2 Circle of Protection: Red;
2 Disenchant; 2 Red Elemental Blast; 3 Swords to Plowshares; 4 Underworld Dreams.**

**Reading of the deck:** a base-blue/black/green, five-color reanimation and
artifact-value deck. Rasputin supplies a burst of colorless mana; Triskelion
turns reanimation and copies into removal, pressure and reach. Erhnam is a
credible hard-cast threat, so a hand need not assemble Bazaar plus Animate Dead
to function. The sideboard adds a possible draw-seven/Dreams damage plan.
These are authored tactical inferences, not measured win rates or implemented
AI behavior. The examples assume ordinary priority, no intervening answers,
no cost modifiers and sufficient library cards unless stated otherwise.

### Mana and mulligan constraints specific to this list

Only **15 of the 18 lands produce mana**. Bazaar is an outlet, not a source.
There are no basics; Blood Moon and nonbasic disruption can attack the whole
land plan. White and red rely on City of Brass, the appropriate Mox, Birds or
Lotus, rather than white/red dual lands. Newly cast Birds cannot immediately
tap for mana. Blue for Copy Artifact, double blue for Braingeyser/Mana Drain,
and black for Animate Dead remain real requirements even with seven Rasputin
mana available.

Prefer a retained hand with usable colored development and either a complete
reanimation line or a plausible hard-cast/draw plan. Do not keep Bazaar plus
expensive cards merely because Bazaar is labeled a land, or mulligan a sound
Erhnam/mana-rock hand just because it lacks reanimation. Unlike the BGR fixture,
this list has no Dark Ritual, All Hallow's Eve or Jalum Tome.

### 1. Animate Rasputin into Triskelion plus Copy Artifact

[Rasputin Dreamweaver](../../forge-gui/res/cardsfolder/r/rasputin_dreamweaver.txt)
enters with seven dream counters, each convertible to one colorless mana.
[Animate Dead](../../forge-gui/res/cardsfolder/a/animate_dead.txt) returns him
with all seven, as a **3/1** because of its power penalty.

**Concrete line:** Rasputin is in your graveyard; Animate Dead, Triskelion and
Copy Artifact are in hand; you can produce **one generic mana, B and U** in
your main phase.

1. Pay `1B` to animate Rasputin.
2. Remove six dream counters for six colorless mana and cast Triskelion.
3. Remove the last dream counter; spend that mana plus the reserved `U` on
   Copy Artifact, copying Triskelion as it enters.
4. You now have Rasputin and **two 4/4 Triskelions, each with three counters**:
   six potential immediate one-damage activations, or two substantial threats.

Rasputin's mana and Triskelion's counter-removal abilities have no tap cost,
so they work immediately. These newly controlled creatures cannot yet attack.
Copying does not duplicate existing counters or Animate Dead's power penalty;
the copy gets its own entry counters. These distinctions follow the modern
[Comprehensive Rules, 302.6 and 707](https://media.wizards.com/2026/downloads/MagicCompRules%2020260925.pdf).

This is a strong **value/tempo sequence**, not an automatic kill. If no Copy
Artifact is available, spend only six counters for Triskelion and keep the
seventh rather than creating unused mana. Other useful sinks are:

| Available colored mana / cards | Use for Rasputin's colorless mana |
| --- | --- |
| `UU` and Braingeyser | Seven colorless pays `X = 7`; usually refill your own hand. |
| `U`, Recall and three expendable hand cards | Six colorless pays `X = 3` in Recall's `XXU` cost; discard three and recover three graveyard cards. Leave the seventh dream counter unspent. Recall exiles itself. |
| `G` and Erhnam Djinn | Three colorless pays Erhnam's generic cost; retain four dream counters. |
| `U` and Copy Artifact, with a useful artifact already present | One colorless pays the generic part; copying mana or removal may be better than waiting for a robot. |

See [Braingeyser](../../forge-gui/res/cardsfolder/b/braingeyser.txt),
[Recall](../../forge-gui/res/cardsfolder/r/recall.txt) and
[Erhnam Djinn](../../forge-gui/res/cardsfolder/e/erhnam_djinn.txt).
Dream counters are stored resources; floating mana is not. Under this guide's
mana-burn contract, spend it before the phase ends. Mana Drain creates a
similar colorless-mana budgeting problem in the next main phase, but its
delayed mana is not optional.

### 2. Copy Artifact is a flexible engine, not just another creature

The key pairing is
[Copy Artifact](../../forge-gui/res/cardsfolder/c/copy_artifact.txt) with
[Triskelion](../../forge-gui/res/cardsfolder/t/triskelion.txt): even copying a
Triskelion with **zero counters** gives a fresh three-counter **4/4**. A copy
of an animated Triskelion is likewise 4/4, not 3/4. The original may be yours
or the opponent's; the copy remains an enchantment as well as an artifact
creature, so enchantment removal also works on it.

Other relevant choices in this exact list:

- **Sol Ring:** `1U` buys another immediately usable two-colorless-mana source.
- **A Mox:** fix the missing color, especially white/red splashes or black for
  the Dreams pivot. Deckbuilding restrictions do not forbid battlefield copies.
- **Black Lotus:** pay `1U`, then sacrifice the copy for three mana of one
  color. This can convert Rasputin mana plus `U` into `BBB`. **Keep the original
  Lotus on the battlefield until Copy Artifact resolves**; sacrificing the
  only Lotus to pay for the copying spell removes that option.
- **Chaos Orb:** pay `1U` for the copy and another `1` to activate it, potentially
  keeping the original as a template. This buys another removal attempt, not a
  guaranteed hit; apply the shared Swedish Orb convention and its Forge caveat.

Copy Artifact chooses an artifact as it enters, rather than targeting one
when cast. Removal while the spell is on the stack can therefore erase the
intended choice. It cannot copy Rasputin, who is not an artifact, or a card in
the graveyard. If a copied Triskelion dies, its card is **Copy Artifact, an
enchantment, in the graveyard**: Animate Dead cannot recover it. Regrowth or
Recall can return that card to hand instead. See also
[Black Lotus](../../forge-gui/res/cardsfolder/b/black_lotus.txt) and
[Chaos Orb](../../forge-gui/res/cardsfolder/c/chaos_orb.txt).

### 3. Reloading counters through finite reanimation sequences

**Triskelion reset:** an ordinary Triskelion with no counters is still 1/1;
one carrying Animate Dead is 0/1, not dead. To deliberately recycle an
undamaged actual Triskelion card, spend two counters for useful damage and
reserve the last to shoot itself. Removing that last counter leaves one
toughness, so the self-ping kills it. Another Animate Dead can then bring it
back with three fresh counters. This costs a new Animate Dead, `1B`, and one
counter spent on itself. It is not a free three-damage loop; doing it to a
Copy Artifact copy does not produce a reanimatable creature card.

**Rasputin reset:** after using his dream counters for useful mana, a
Triskelion ping can kill the one-toughness Rasputin. A fresh Animate Dead
returns him with seven counters. Float only mana you can spend in this main
phase, and count the black source, reanimation card and ping consumed. An
attached Animate Dead going to the graveyard does not return itself to hand.

A second actual Rasputin can also replace an exhausted one: take any desired
mana from the old copy **before** the new one resolves, then keep the fresh
one under the modern legend rule. There is no priority window to harvest
both after entry before choosing which survives.
[Comprehensive Rules, 704.5j](https://media.wizards.com/2026/downloads/MagicCompRules%2020260925.pdf).

These are finite resource conversions, **not a demonstrated self-contained
infinite-mana or infinite-damage combo**. Do not reset a useful creature merely
to inflate counters if the resulting hand, colored mana or defense is worse.

### 4. Combat, Psionic Blast and Time Walk

- An unblocked, attack-ready Triskelion with three counters can deal **four
  combat damage, then three damage from counters: seven total**. With Animate
  Dead attached, the corresponding total is **three plus three: six**. Count
  counters spent before combat damage against its power; combat damage is not
  put on the stack.
- Do not assume all counters remain usable after blocking. A 4/4 Triskelion
  with three damage marked becomes a lethally damaged 3/3 after paying for
  its first ping. It dies before another activation; only that queued ping
  remains. Evaluate lethal damage after each counter payment.
- [Psionic Blast](../../forge-gui/res/cardsfolder/p/psionic_blast.txt) plus one
  Triskelion counter can kill an otherwise unmodified 4/5 Erhnam. Blast plus
  three counters can finish an opponent at seven, but account for Blast's two
  damage to you, City damage and mana burn before declaring a winning line.
- Deploy threats, then [Time Walk](../../forge-gui/res/cardsfolder/t/time_walk.txt)
  to untap, draw and attack with them on the extra turn. Rasputin gains at most
  **one** dream counter in that upkeep, and only if he **started that turn
  untapped**. Untapping a previously tapped Rasputin does not qualify. Floating
  mana does not carry into the extra turn.
- Rasputin can spend counters to prevent damage **to himself**, not to you or
  another creature. Reserving a counter can protect him from a one-damage
  effect; surviving a Lightning Bolt needs three such preventions.
- Erhnam is not drawback-free: Bayou and Tropical Island give this deck eight
  lands with the Forest type. Consider the next opposing attack when choosing
  which opposing non-Wall receives forestwalk.
- Against [The Abyss](../../forge-gui/res/cardsfolder/t/the_abyss.txt), prefer
  the robot route when appropriate: Triskelion and its Copy Artifact versions
  are artifacts and cannot be chosen by that effect. Rasputin, Erhnam and Birds
  lack that protection; Rasputin's damage prevention does not stop destruction.

### 5. Bazaar plus Balance: rebuild faster than the opponent

[Bazaar](../../forge-gui/res/cardsfolder/b/bazaar_of_baghdad.txt) both loads
reanimation targets and reduces hand size by one. Noncreature Moxen and Sol
Ring survive [Balance](../../forge-gui/res/cardsfolder/b/balance.txt), setting
up an asymmetric rebuild despite the spell's symmetric instructions.

**Example:** you have no creatures and the opponent has three. After casting
Balance you hold two cards, including Animate Dead; the opponent holds five.
Respond with Bazaar: draw two, discard three, retaining Animate Dead and
putting a useful creature in the graveyard. Balance sees your one-card hand,
makes the opponent discard four and sacrifice all three creatures, then you
can reanimate in the same main phase if you preserved `1B`. Land counts are
balanced separately; Bazaar counts as a land despite producing no mana.
An opposing creature sacrificed to Balance can also become your Animate Dead
target, since the Aura can use either graveyard.

This is conditional, not a one-sided sweeper by default. Birds and every
Triskelion, including Copy Artifact versions, count as your creatures. Spend
useful counters or float needed mana **before** Balance resolves if those
creatures will be lost; there is no priority between its land, hand and
creature instructions. Protect the mana and follow-up needed after the reset.

### 6. Choose the right refill and recursion target

- **Wheel of Fortune** discards fatties into the graveyard and draws seven;
  it supports an existing reanimation plan rather than erasing its targets.
- **Timetwister** shuffles graveyards back. When possible, animate the creature
  you need **before** casting it. Your deployed creature stays while you refill;
  unused graveyard targets and recursion options do not. Twister can recycle
  spent Lotus or Time Walk, but does not untap permanents or reset counters.
- Spend useful cheap mana artifacts before a draw-seven when safe; a small
  hand gains more cards, but the opponent also receives seven new resources.
- **Regrowth** may need to recover Animate Dead, a destroyed Copy Artifact,
  Lotus for colored mana, or removal rather than the most prestigious power
  card. **Recall** can discard a creature while recovering Animate Dead and
  other spent cards, but needs `XXU`, enough cards to discard and useful
  graveyard choices. It exiles itself and is not Ancestral Recall.
- **Demonic Tutor** should complete a payable sequence: outlet, target,
  reanimation, colored source or immediate answer, whichever is actually missing.

Local text: [Wheel of Fortune](../../forge-gui/res/cardsfolder/w/wheel_of_fortune.txt),
[Timetwister](../../forge-gui/res/cardsfolder/t/timetwister.txt),
[Regrowth](../../forge-gui/res/cardsfolder/r/regrowth.txt).

### 7. Sideboard pivot: Underworld Dreams plus forced draws

The four [Underworld Dreams](../../forge-gui/res/cardsfolder/u/underworld_dreams.txt)
are **sideboard cards**, not part of game one's combo. Against a suitably slow,
creature-light or graveyard-hating opponent, boarding some in offers damage
that does not rely on a reanimated attacker. This is a proposed adaptation,
not a command to board all four against every opponent; use this actual list,
not the BGR fixture's swaps.

The casting cost is **BBB**, not three generic mana. Eleven lands can produce
black, plus Mox Jet, active Birds and Lotus; Rasputin cannot pay the black
symbols. Copying an available Jet or Lotus can supply missing colored mana.

| Already resolved Dreams | Opponent draws seven from Wheel or Timetwister | Damage if all triggers resolve normally |
| --- | --- | --- |
| 1 | 7 cards | 7 |
| 2 | 7 cards | 14, not lethal from 20 by itself |
| 3 | 7 cards | 21 |

With Dreams on the battlefield, Rasputin's seven colorless plus `UU` can fund
**Braingeyser for seven targeting the opponent**: seven damage per Dreams.
[Ancestral Recall](../../forge-gui/res/cardsfolder/a/ancestral_recall.txt) can
likewise target the opponent for three draws/damage per Dreams, but giving them
cards is usually undesirable unless it secures the finish. Compare this with
drawing your own answers or deploying Triskelions instead.

The opponent receives the cards before the Dreams damage triggers resolve and
can respond; do not mark the game won merely because lethal triggers exist.
Your own Bazaar, draw spells and extra-turn draw **do not trigger your Dreams**.

There is also a defensive sideboard synergy: once
[Circle of Protection: Red](../../forge-gui/res/cardsfolder/c/circle_of_protection_red.txt)
is in play, Rasputin's counters can fund its generic-mana activations during
the opponent's turn. Each shield covers the next damage event from the chosen
red source, not every red source for the whole turn. Generate only the mana
needed; the Circle does not prevent mana burn or blue Psionic Blast damage.

### KabaL-specific AI priorities

Use only public information and your own hand. These extend the base guide;
they are not weights already consumed by Forge. `R` and `H` retain their shared
rules/heuristic meanings, and immediate survival overrides value preferences.

| ID | Observable condition | Preferred action | Guard / evidence |
| --- | --- | --- | --- |
| kabal-D1 | Animate Dead can return Rasputin, Triskelion or Erhnam | Compare a payable Rasputin follow-up with immediate Triskelion removal and Erhnam's clock | Seven colorless is not seven mana of any color (R); ranking depends on the board (H). |
| kabal-D2 | Copy Artifact is available with a depleted Triskelion in play | Recognize a fresh three-counter robot as an option | Preserve a suitable artifact until resolution; copy counters and Aura penalties are not inherited (R). |
| kabal-D3 | Rasputin has counters and a proposed spell sequence | Reserve exact colored sources and remove only counters with a use | Optional mana generation can cause avoidable phase-end burn (R). |
| kabal-D4 | Considering a Triskelion or Rasputin reset | Debit the ping, another Animate Dead and black mana before valuing refreshed counters | A dead Copy Artifact is not an Animate Dead target; zero Triskelion counters alone do not cause death (R). |
| kabal-D5 | Balance and Bazaar are available | Project both players' land, hand and creature counts, then retain a playable rebuild | Mana rocks survive only if noncreatures; do not discard the only follow-up blindly (R/H). |
| kabal-D6 | Wheel and Timetwister compete as refill options | Prefer the line preserving the relevant graveyard or deployed threat | Giving the opponent seven cards can outweigh your refill (H). |
| kabal-D7 | Dreams are boarded in and a forced-draw spell is available | Check BBB development, actual opposing draws and damage per Dreams | Own draws add no damage; allow for answers before triggers resolve (R/H). |

### KabaL acceptance examples

Additional proposed scenarios, **not executed engine tests**. The offline
library validator does not verify these tactical lines.

- **K1:** Rasputin in the graveyard, Animate Dead/Triskelion/Copy Artifact in
  hand, and `1B` plus `U` available in your main phase: the unopposed sequence
  produces Rasputin with zero dreams and two three-counter Triskelions, with
  no unused mana and no same-turn creature attacks.
- **K2:** Copy an undamaged, zero-counter Triskelion carrying Animate Dead.
  Original remains 0/1; copy enters 4/4 with three counters. Killing the copy
  puts an enchantment card, not a reanimation target, in the graveyard.
- **K3:** An undamaged Triskelion spends its first two counters on the opponent
  and its last on itself. Opponent takes two; Triskelion dies and a new Animate
  Dead can reload it. Spending all three on the opponent instead leaves it
  alive, so that reset is unavailable without another way to kill it.
- **K4:** The only Black Lotus is sacrificed to pay for Copy Artifact. With no
  Lotus remaining when the spell resolves, the intended Lotus-copy line fails.
- **K5:** Apply the Balance example with zero own creatures, three opposing
  creatures, and two own cards after casting Balance. Bazaar retains one
  Animate Dead; the opponent's five-card hand becomes one. Verify usable `1B`
  after land sacrifices before proposing the reanimation follow-up.
- **K6:** Two Dreams plus a successful seven-card opposing draw yield fourteen
  damage, not twenty-one. Activating your own Bazaar adds none. Rasputin alone
  supplies neither Dreams' BBB nor Braingeyser's UU.
- **K7:** A tapped Rasputin starts an extra turn, then untaps. His upkeep does
  not add a dream counter; starting untapped would add one, up to seven.
- **K8:** A three-counter Triskelion has three damage marked. Removing one
  counter for a ping makes the creature die before a second activation; do not
  forecast three further damage from that position.

## Evidence and implementation boundary

- [Wak-Wak: Reanimator](https://www.wak-wak.se/9394decks/reanimator): historical archetype description; reviewed 2026-09-26.
- [Shared rules and evidence labels](Oldschool-Rules.md): overrides outdated restriction or mulligan assumptions.
- [Implementation and benchmark roadmap](Oldschool-Implementation.md): the current AI does not read this document.

Local card definitions: [Animate Dead](../../forge-gui/res/cardsfolder/a/animate_dead.txt), [All Hallow's Eve](../../forge-gui/res/cardsfolder/a/all_hallows_eve.txt).

All fixture construction, opening judgments, matchup adaptations, sideboard
plans, and heuristic priorities here are authored proposals. Validate card
interactions against the corresponding scripts in `forge-gui/res/cardsfolder/`
and engine effects before turning them into executable behavior.
