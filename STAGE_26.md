# Stage 26 — Variant and texture rendering

Stage 26 connects the reconstructed renderer to ZAWA's synchronized entity state and original texture resources.

## Added
- `ZawaTextureResolver`
- Numbered variant selection using `ZawaBaseEntity.getVariant()`
- Male/female texture selection using `ZawaBaseEntity.getGender()`
- Baby texture selection
- Resource-manager existence checks with safe fallback
- Renderer now resolves the texture per entity rather than caching one texture per species

## Naming support
The resolver attempts, in order:
1. `<species>_baby.png` for babies
2. `<species>_<variant>_<male|female>.png`
3. `<species>_<variant>.png`
4. `<species>_<male|female>.png`
5. `<species>_1_<male|female>.png`
6. `<species>_1.png`
7. `<species>.png`

Variant indices are treated as zero-based entity data and mapped to the resource naming convention's one-based numbering.

## Status
Source-level reconstruction. No genuine NeoForge/Gradle compilation has been performed in this environment.
