# Stage 4: original 1.20.1 JAR bytecode findings

Inspected user-supplied `zawa-1.20.1-1.1.0-IntelligentAnimals3-alpha5(2).jar` locally using `zipfile` and JDK `javap -p -c`. Original JAR is **not** committed.

## Orca.class
- Extends `ZawaAquaticEntity`.
- Attributes: MOVEMENT_SPEED = 0.30000001192092896, MAX_HEALTH = 120.0, ATTACK_DAMAGE = 20.0.
- `registerGoals` calls superclass, then registers `BreachGoal(this,5)` priority 4; `ZawaMeleeAttackGoal(this,4.0,1.33,true)` priority 5; `HurtByTargetGoal(this)` target priority 3.
- Offspring factory uses the orca entity type.
- Successful attack plays `ORCA_ATTACKING`; ambient sound is `ORCA_AMBIENT`; hurt sound is `ORCA_HURT`.
- Eye height is 0.5 × entity dimensions height.

## Original renderer/model
- Three separate original model layer implementations: `OrcaModel$AdultMale`, `OrcaModel$AdultFemale`, `OrcaModel$Child`.
- Renderer marks the species sexually dimorphic.
- Renderer uses adult scale 0.8, child scale 0.6 and positional translation (0,-0.3,-0.3).
- Original model classes implement separate idle and movement animation methods. Need recover layer geometry and motion formulas before porting.

## Shared aquatic base
- Original `ZawaAquaticEntity.handleAirSupply` decreases air when alive and NOT in water/bubble; replenishes to 300 when in water/bubble. This is the original code, though not biologically realistic for an orca. Fidelity policy: preserve original unless explicitly directed otherwise.
- Base adds `TryFindWaterGoal` priority 0 and `RandomSwimmingGoal(this,1.0,10)` priority 4.

## Changes already made
- Updated experimental branch `Orca.java` attributes and goals to match original.
- Not yet copied/ported original sound registration, model geometry, animation, or standalone registries.
- No build or in-game verification. The current branch still contains the full ZAWA project; it is NOT yet a standalone mod.
- Shared goal implementations in the reconstructed branch may differ from original bytecode and require separate comparison.
