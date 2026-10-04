# Stage 17 — Ambient Fish Foundation

Migrated the shared ambient-fish hierarchy and the first nine aquatic ambient species from the original ZAWA 1.20.1 bytecode contract.

## Species
- African Lake Cichlid
- Angelfish
- Betta
- Clownfish
- Zawa Cod
- Corydoras
- Gramma
- Plecostomus
- Zawa Salmon

## Shared systems
- `ZawaBaseAmbientEntity`
- `ZawaAmbientLandEntity`
- `ZawaAmbientFishEntity`
- synchronized variant state
- aquatic navigation and swimming control
- ambient spawn predicates
- group leader/size API for schooling species
- NeoForge `WATER_AMBIENT` entity registration
- default attribute registration
- `IN_WATER` spawn-placement registration

## Fidelity notes
EntityType dimensions and core attribute values were recovered from the original compiled ZAWA 1.20.1 classes/registry bytecode. Custom ZAWA bucket items and specialized group AI are intentionally deferred to their own migration stages rather than replaced with unrelated items or fabricated behavior.
