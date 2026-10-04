# Stage 16 — Natural spawning

- Added `ZawaSpawnPlacements` using NeoForge 1.21.1 `RegisterSpawnPlacementsEvent`.
- Recovered original placement type and heightmap choices for the 12 concrete entities currently registered in the port.
- Ported 10 original ZAWA Forge biome spawn modifiers that reference those 12 entities to `data/zawa/neoforge/biome_modifier/`.
- Preserved original spawn weights and min/max group sizes.
- Converted legacy Forge biome-modifier operators/tags to NeoForge equivalents.
- Intentionally did not copy spawn modifiers whose entity types are not yet registered; those will be added when their ambient/concrete entity classes are migrated.
- JSON structural validation passed for all 10 migrated modifiers.
- Full Gradle compilation remains pending because the current environment still lacks a usable Gradle/NeoForge dependency installation.
