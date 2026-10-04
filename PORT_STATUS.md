# ZAWA: Evolved — NeoForge 1.21.1 Port

## Base
- Source build: ZAWA 1.20.1 Forge `1.1.0-IntelligentAnimals3-alpha5`
- Target: Minecraft 1.21.1 + NeoForge
- Java: 21
- Mod ID: `zawa`

## Stage 2 complete
- Extracted all 489 compiled classes from the supplied JAR for analysis.
- Generated complete `javap -p` signature inventory (`analysis/all-signatures.txt`).
- Performed constant-pool Forge namespace scan across all classes.
- 162/489 classes contain Forge namespace references.
- 450 unique Forge-reference occurrences were detected across those classes.
- Highest-impact classes have been identified for manual API migration.
- Preserved all original resources in `src/main/resources`.

## Important constraint
The supplied artifact contains compiled Java bytecode rather than original Java source. The upstream public repository currently exposes project information/localization but not the mod Java source. A source decompiler or the original source tree is therefore needed for a clean source-level port.

## Next stage
1. Recover/decompile the 489 classes into Java source.
2. Translate the bootstrap and registration layer to NeoForge.
3. Port networking, config, events, data generation and client registration.
4. Resolve Minecraft 1.20.1 -> 1.21.1 API changes.
5. Compile and iterate against NeoForge 21.1.x.

## Stage 5 — entity hierarchy recovery
- Recovered the concrete ZAWA animal/ambient entity hierarchy from bytecode.
- Added `ZawaSpawnCategory` with all 24 original habitat categories.
- Added `ZawaEntityMigrationPlan` covering 46 concrete entity IDs plus the NPC zookeeper identified separately in the original registry.
- Added `ZawaEntityHierarchy` and Stage 5 recovery notes.
- Core `ZawaBaseEntity` behavior remains a source-recovery target; no behavior has been replaced with a fake implementation.
- Next: translate `ZawaBaseEntity`/`ZawaEntityStat` bytecode and then wire concrete constructors/attributes into NeoForge registration.

## Stage 8 — ZawaBaseEntity core recovery

Recovered and migrated the shared `ZawaBaseEntity` core from the original 1.20.1 bytecode:
- nine synchronized entity-data fields (variant, gender, fertility, favorite food, pregnancy, gestation, hunger, thirst, enrichment)
- NeoForge 1.21.1 `SynchedEntityData.Builder#define` initialization
- species-size-scaled hunger/thirst stat construction and fixed 20-point enrichment
- common AI goal priorities
- variant/gender refresh behavior
- spawn initialization including biome-aware `SpeciesVariantsEntity` variants
- fertility/favorite-food accessors
- pregnancy/gestation state accessors
- persistent NBT for identity, food preference, stats and pregnancy children
- breeding eligibility and ZAWA diet/kibble predicates
- land, leaf, semi-aquatic, aquatic and flying spawn-rule helpers
- migrated `ZawaMainConfig` values used by the base entity, registered as a NeoForge COMMON config

The original class also depends on additional ZAWA systems (EntityStatsManager, breeding goals, species interfaces and individual animal classes); those remain subsequent migration targets. No claim of a full Gradle compile is made yet because the project is still missing those dependent reconstructed classes and a local Gradle distribution.

## Stage 9 — Species Data Layer
- Recovered `EntityStats` structure and accessors from original bytecode.
- Recovered `EntityDiet` wire format and registry-ID serialization pattern, migrated to `BuiltInRegistries.ITEM`.
- Recovered `EntityFertilityCategory` exact LOW/MEDIUM/HIGH ranges and averaging logic.
- Recovered `EntitySizeCategory`, `EntityTemperamentCategory`, and `EntitySpeedCategory` enum values/serialized names.
- Added NeoForge-side source representations under `world/entity/stats`.
- `EntityEnrichment` and resource reload managers remain the next dependency layer; no empty behavioral implementation was substituted.

## Stage 10 — data-driven species statistics
- Copied all 46 original `data/zawa/entity_stats/*.json` definitions without altering their schema.
- Copied all 13 original `data/zawa/diets/*.json` definitions.
- Copied the 12 original Forge item-tag JSONs required by the diet resources.
- Added NeoForge `EntityDietManager` using `SimpleJsonResourceReloadListener` and `BuiltInRegistries.ITEM`/`TagKey` resolution.
- Added NeoForge `EntityStatsManager` using the original field schema: temperament, kibble, size, fertility, breeding item, litter size, diet, speed, enrichment, variant count and captive variants.
- Added a resource-backed `EntityEnrichment` representation that preserves the original enrichment JSON groups and item lookup while the custom enrichment registry is migrated later.
- Registered diet/stat reload listeners through NeoForge `AddReloadListenerEvent`, with diets registered before entity stats.
- Structural validation: all 46 entity-stat JSON files and all 13 diet JSON files parse successfully and match the original filenames.
- Gradle compilation is still pending because this environment has no local Gradle distribution/wrapper and outbound dependency retrieval is unavailable.

## Stage 11 — shared animal movement hierarchy
- Recovered and migrated `ZawaLandEntity` from bytecode: FloatGoal and water-avoiding stroll priorities/fields.
- Recovered and migrated `ZawaFlyingEntity`: FlyingMoveControl, FlyingPathNavigation settings, flying-state predicate, flap animation physics, fall-damage suppression, step/flap timing, and the original tree-seeking flying stroll goal.
- Recovered and migrated `ZawaSemiAquaticEntity`: dual water/ground navigation and controls, swim-goal reassessment, baby-swim behavior, water travel multiplier hook, breathing duration, and water mob type.
- Recovered and migrated `ZawaAquaticEntity`: aquatic controls/navigation, swimming goals, despawn behavior, buoyancy correction, air-supply/drowning logic, water travel, and water mob type.
- These classes are shared by the concrete species and are now ready to be used as the superclass layer for individual animal implementations.
- Compilation remains pending because the staged project still lacks a local Gradle distribution and several downstream ZAWA classes; source recovery is being performed directly from the supplied bytecode.
