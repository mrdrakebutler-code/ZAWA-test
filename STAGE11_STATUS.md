# Stage 11 — Shared Animal Movement Hierarchy

Recovered from the original ZAWA 1.20.1 Forge bytecode:

- ZawaLandEntity
- ZawaFlyingEntity
- ZawaFlyingEntity.ZawaFlyingGoal
- ZawaSemiAquaticEntity
- ZawaAquaticEntity

The migration keeps the original goal priorities and the distinctive movement behavior rather than replacing these classes with empty stubs.

The 1.21.1 entity-data architecture used by the already-migrated base class is compatible with NeoForge's documented SynchedEntityData builder approach. The next stage can therefore concentrate on concrete species classes and their attributes/variants instead of rebuilding movement foundations again.
