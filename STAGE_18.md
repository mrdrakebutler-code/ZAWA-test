# Stage 18 — Final Ambient Species Batch

Completed the remaining seven ambient species from the original ZAWA entity roster.

## Species
- Butterfly
- Brown Rat
- Honey Bee
- Leafcutter Ant
- Praying Mantis
- Scorpion
- Tarantula

## Recovered from 1.20.1 bytecode
- EntityType dimensions and client tracking range
- Default attributes
- Original spawn weights/group sizes
- Variant spawn tables for Praying Mantis, Scorpion and Tarantula
- Climbing and venomous API contracts
- Ambient flying hierarchy for Butterfly and Honey Bee

## Registry corrections
The original registry bytecode was rechecked and Stage 17's Plecostomus and Salmon dimensions were corrected to their compiled values.

## NeoForge migration
- Deferred entity registration
- Default attribute event registration
- Spawn-placement registration
- Biome spawn modifiers for the recovered custom spawn categories

The original ZAWA spawn categories are not native Minecraft biome tags. The Stage 18 biome modifiers therefore use explicit NeoForge/Minecraft biome-tag approximations and are documented as a migration layer; species-specific biome resolution remains a later fidelity pass.

## Validation
- All Stage 18 biome-modifier JSON files parse successfully.
- Java source brace balance verified.
- An unrelated pre-existing asset JSON (`assets/zawa/models/block/plushies/giraffe_plush.json`) remains malformed in the inherited project; it was not changed in Stage 18.
- No claim of a complete Gradle compilation is made because the required Minecraft/NeoForge Gradle dependency environment is not available in this runtime.
