# Old School AI rules contract

Ruleset ID: `swedish-fe-burn-london-v1`. Documentation baseline reviewed 2026-09-26.
This is a **custom format**, not an assertion that Swedish tournaments allow Fallen
Empires or mana burn. Do not select a similarly named Forge format and assume equivalence.

## Card pool and deck construction

Card identities printed in Alpha, Beta, Unlimited, Arabian Nights, Antiquities,
Legends, The Dark, and **Fallen Empires** are eligible. Reprint art, borders, and
physical printing policies are irrelevant to these digital examples. At least 60
main-deck cards, at most 15 sideboard cards; four copies across both sections,
except basic lands and the restrictions below. A six-card sideboard is legal;
a 55-card main deck is not. All reference fixtures use exactly 60 + 15.

The restriction baseline is the published [n00bcon 2025 rules](https://n00bcon.world/),
cross-checked against the organizer's [Swedish rules and change history](https://ragingbullseries.com/index.php/swedish-os-rules/).
It is pinned rather than silently tracking future events. One copy total of each:

Ancestral Recall; Balance; Black Lotus; Braingeyser; Channel; Chaos Orb; Demonic
Tutor; Regrowth; Sol Ring; Strip Mine; Library of Alexandria; Mana Drain; Mind
Twist; Mishra's Workshop; Mox Emerald; Mox Jet; Mox Pearl; Mox Ruby; Mox Sapphire;
Time Walk; Timetwister; Wheel of Fortune.

No ante. Banned: Bronze Tablet, Contract from Below, Darkpact, Demonic Attorney,
Jeweled Bird, Rebirth, Tempest Efreet. **Four Hymn to Tourach are allowed.** Time
Vault, Recall, Fork, Mana Vault, Black Vise, Maze of Ith, Fastbond, and Mishra's
Factory are not restricted by this contract. Older articles and event-specific
lists are not authoritative for this custom format. In particular, an old
Twiddlevault article describing a singleton Vault is historical, not this policy.

## Mulligan and game rules

Two-player, 20 life, modern priority, stack, combat, and state-based actions unless
an exception is stated here. The starting player skips their first draw. Use the
[London mulligan](https://magic.wizards.com/en/news/announcements/london-mulligan-2019-06-03):
see seven cards each time; on keeping, bottom one per mulligan taken. There is no
free first mulligan or Vancouver scry in these examples. Judge the **retained**
six or five, not the seven before bottoming.

The agreed mana-burn exception empties pools at **phase ends**, not between the
steps of one phase, and loses one life per unspent mana. This follows the
phase-end convention described by [Eternal Central](https://www.eternalcentral.com/9394rules/),
but does **not** import that organization's card restrictions or other rules.
Life loss is not damage: Ali from Cairo and Circles of Protection do not prevent
it. Floating upkeep mana can survive into the draw step, but not the first main
phase. Mana floated in combat cannot pay for a postcombat sorcery.

Do not reinstate damage-on-the-stack, a separate interrupt window, delayed death
at zero life, or old artifact rules indiscriminately. Current card-specific text
still matters: Winter Orb and Howling Mine explicitly check whether they are
untapped; most artifacts do not stop working merely because they are tapped.

## Physical-card exceptions and digital limitations

Use the Swedish chosen-single-nontoken-permanent Chaos Orb convention and the
published fair-flip Falling Star convention from the
[organizer's rules](https://ragingbullseries.com/index.php/swedish-os-rules/).
Choosing an Orb victim is not targeting it. A successful flip is not guaranteed.
These documents discuss tactical intent, not a new physics or hit-probability
model. Forge's current flip implementation and pool-emptying timing differ;
see [the compatibility audit](Oldschool-Implementation.md) before benchmarking.

## Shared tactical guardrails

- Count colored, reusable mana separately from Lotus, Ritual, Vault, and
  conditional Fellwar Stone mana. Bazaar of Baghdad and Maze of Ith are not mana
  sources. Workshop mana cannot pay arbitrary spells or artifact activations.
- Check immediate lethal, survival, legal costs, targets, timing, and replacement
  effects before strategic preferences. Never spend a regeneration shield after
  destruction has already resolved. Regeneration cannot stop exile, sacrifice,
  The Abyss, or Wrath of God.
- Reserve actual mana for Counterspell, regeneration, upkeep, and pump. Do not
  tap every source automatically. Mandatory Su-Chi death mana and Mana Drain's
  delayed mana require a same-phase sink or an explicit life-loss budget.
- Life payments, damage, and life loss differ. Lich replaces life gain with
  draws; its damage trigger is not triggered by mana burn. Without an effect
  permitting survival, zero life loses immediately, before a future Mirror
  Universe activation. Mirror is usable only during its controller's upkeep.
- The Abyss and Nether Void are **world** enchantments. The world rule also
  affects Arboria, Living Plane, and Concordant Crossroads; do not plan to retain
  incompatible world permanents. The Abyss destroys a chosen legal target,
  rather than making a player sacrifice any creature.
- Sideboard from observed cards and the actual opposing list when available,
  not an archetype label alone. A transformation can invalidate a previous read.
  Return to the original 60 + 15 before applying a different example plan.

## Vocabulary and evidence

**Clock:** turns until a credible kill, accounting for blockers and self-damage.
**Role:** aggressor, stabilizer, resource controller, or engine assembler in the
current position, not a permanent label. **Virtual advantage:** cards made
temporarily ineffective, not cards actually drawn. **Soft lock:** restricts
options but has escape routes; never score it as a win. **Mana sink:** a legal,
useful expenditure before the relevant pool-emptying boundary.

`R` = card/rules constraint; `H` = authored heuristic requiring playtesting;
`O` = reported observation, not controlled evidence. Priorities in each decision
table are relative preferences, subordinate to legality and immediate survival.
No playbook supplies measured matchup percentages. Confidence in an archetype
identification is separate from confidence that its proposed strategy wins.
