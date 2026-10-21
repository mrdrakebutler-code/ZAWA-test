# Orca standalone — Stage 2 extraction map

Source: `neoforge-1.21.1-stage37`; working branch: `experiment/standalone-orca-1.21.1`.

## Confirmed source mappings
| Concern | Current file | Finding |
|---|---|---|
| Entity | `world/entity/animal/Orca.java` | Only 40 health, 0.3 movement speed, 10 attack damage, offspring factory |
| Entity registration | `world/entity/animal/ZawaEntities.java` | `orca` is `WATER_CREATURE`, width 2.0, height 1.5, client tracking range 10 |
| Client renderer | `client/ZawaClient.java` | Generic renderer registration, orca scale 0.8 |
| Aquatic movement | `world/entity/animal/ZawaAquaticEntity.java` | SmoothSwimmingMoveControl, WaterBoundPathNavigation, random swim, seek water; verify air handling against original |
| Shared life cycle | `world/entity/animal/ZawaBaseEntity.java` | Shared breeding, birth, follow-parent and survival goals |
| Breeding AI | `world/entity/ai/goal/ZawaBreedGoal.java` | Partner within 8 blocks; tick to 60 and distance squared <9, then call breed |
| Model | `client/model/ZawaAnimalModel.java` | Generic reconstructed model, not original hand-authored orca geometry |
| Animation | `client/model/ZawaAnimationController.java` | Generic swimming plus orca-specific tail adjustments, not verified original animation |
| Textures | `assets/zawa/textures/entity/orca/` | Four male + four female adult variants and one baby |
| Sounds | `assets/zawa/sounds/orca_*.ogg` | ambient, attacked, attacking, broadcast, communicating |
| Stats | `data/zawa/entity_stats/orca.json` | giant, defensive, piscivore, four variants, medium fertility, litter size 1 |

## Fidelity gates before implementation
1. Obtain the original ZAWA orca geometry and original animation definitions; current repository does not contain a dedicated orca model file.
2. Verify original orca-specific AI (target selection, social interaction, surfacing) rather than inventing it.
3. Verify the original breathing mechanic. Current aquatic parent decreases air on land, which is questionable for a mammal.
4. Isolate the required breeding state, pregnancy, sex, variants, save data and food logic; avoid importing all ZAWA systems.
5. Ensure assets may be redistributed under the applicable license or permission before public release.

No gameplay logic has been changed by this document. No build or in-game tests have been performed.
