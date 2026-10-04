# Stage 30 — NeoForge networking and synchronization

Implemented the original ZAWA networking concepts using NeoForge 1.21.1 custom payloads.

- `SyncEntityStatsPayload`: server -> client entity-stat synchronization.
- `UpdateAnimalNamePayload`: client -> server animal naming, restricted to players holding the Data Book and nearby animals.
- `ZawaNetworkManager`: payload registration through `RegisterPayloadHandlersEvent` / `PayloadRegistrar`.
- `ZawaNetworkEvents`: sends current entity stats when a player logs in.
- `ZawaNetworkSender`: client-side helper for sending name updates.

This replaces the original Forge `SimpleChannel` packet layer with NeoForge's `CustomPacketPayload` / `StreamCodec` system.
