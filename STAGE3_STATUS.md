# ZAWA NeoForge 1.21.1 — Stage 3

## Completed

- Recovered the original mod ID and 22-item plushie list from bytecode.
- Established the NeoForge 1.21.1 `@Mod` entrypoint around an `IEventBus`.
- Created the first NeoForge networking seam using `RegisterPayloadHandlersEvent` / `PayloadRegistrar`.
- Confirmed from the original bytecode that the legacy network layer had exactly two packets:
  - `SyncEntityStatsPacket` — play-to-client
  - `UpdateAnimalNamePacket` — play-to-server
- Preserved the original protocol version (`1`) and network channel name (`main`) as migration metadata.

## Original registration architecture recovered

The 1.20.1 constructor initializes and registers:

1. Item entities
2. Living entities
3. Entity spawn/attribute listeners
4. Items
5. Blocks
6. Creative tabs
7. Features
8. Sounds
9. Block entities
10. Menus
11. Villager professions / POI types
12. Enrichment types
13. Common/client event listeners
14. Datapack reload listeners
15. Config (`zawa/main.toml`)

## Next source-recovery targets

The next classes should be reconstructed in dependency order:

1. `ZawaEntityRegistry` + its `MobBuilder` / `EntityBuilder`
2. `ZawaEntities` and `ZawaItemEntities`
3. `ZawaItems`
4. `ZawaBlocks`
5. `ZawaBlockEntities`
6. `ZawaMenuTypes`
7. `ZawaNetwork.protocol.*`
8. resource/data managers
9. event handlers
10. client models/renderers

This ordering minimizes cascading compile failures and lets the registration graph become testable before migrating the 117 entity classes and 141 model classes.
