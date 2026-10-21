# Standalone ZAWA Orca — fidelity audit (NeoForge 1.21.1)

Source branch: `neoforge-1.21.1-stage37`. This experimental branch must not modify the main ZAWA port.

## Confirmed assets
- `src/main/java/org/zawamod/zawa/world/entity/animal/Orca.java`
- `ZawaAquaticEntity.java` and `ZawaBaseEntity.java`
- 8 adult texture variants (4 male + 4 female), 1 baby texture under `assets/zawa/textures/entity/orca/`
- 5 `orca_*.ogg` sound resources
- `data/zawa/entity_stats/orca.json`: defensive, piscivore, giant, medium fertility, one offspring, four variants; breeding item large_meat

## Verified limitations
- Current Orca class only defines attributes and offspring factory. It does NOT implement species-specific hunting, pod AI, sounds, or animation.
- Shared ZawaAquaticEntity implements water navigation, random swimming and water seeking. Its air logic currently decreases air **outside** water, and resets air **in** water; do not assume this is correct for an air-breathing orca.
- Shared ZawaBaseEntity registers breeding, birth, follow-parent, feeder, water and enrichment goals. Their presence alone does not prove original Orca behavior parity.
- ZawaAnimalModel is explicitly a reconstructed generic scaffold, not the original hand-authored orca model.
- No dedicated orca geometry/model file was found in the branch tree. Original source/bytecode or assets must be inspected before claiming animation fidelity.

## Required next implementation steps
1. Obtain and inspect original ZAWA Orca class, renderer/model, goals, animation data and sound registrations. Compare to this reconstructed branch.
2. Build a minimal independent mod with isolated registries and required common lifecycle/AI components; retain behavior and values only when supported by original code.
3. Preserve adult sex/variant textures, baby texture, sounds, breeding/pregnancy, and persistence.
4. Validate water movement, surfacing/breathing, prey selection, breeding, offspring, animations, despawn rules and save/reload.
5. Build on CI and test the resulting JAR in a clean NeoForge 1.21.1 instance.

**Status:** audit started; no standalone JAR built or tested.