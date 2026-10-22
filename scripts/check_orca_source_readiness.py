#!/usr/bin/env python3
"""Fail-fast source checks for the standalone orca extraction branch.

These checks do not replace a NeoForge build or in-game testing.
Run from the repository root: python3 scripts/check_orca_source_readiness.py
"""
from pathlib import Path
import re

ROOT = Path(__file__).resolve().parents[1] / "src/main/java/org/zawamod/zawa"
REQUIRED = {
    "world/entity/animal/ZawaBaseEntity.java": [
        'childTag.putString("id",',
        'if (!level.addFreshEntity(child)) remaining.add(childTag.copy());',
        'if (mother.children.isEmpty()) return;',
        'if (!(offspring instanceof ZawaBaseEntity child)) continue;',
    ],
    "world/entity/ai/goal/ZawaBirthGoal.java": [
        "boolean requiresUpdateEveryTick()",
        "retryCooldown = 0;",
    ],
    "client/renderer/OrcaRenderer.java": [
        "entity.isBaby() ? 0.6F : 0.8F",
        "poseStack.translate(0.0D, -0.3D, -0.3D);",
    ],
    "world/entity/animal/Orca.java": [
        "protected SoundEvent getDeathSound()",
        "protected SoundEvent getHurtSound(",
        "protected SoundEvent getAmbientSound()",
        "protected float getStandingEyeHeight(",
        "return dimensions.height * 0.5F;",
    ],
}

def main():
    problems = []
    for name, needles in REQUIRED.items():
        path = ROOT / name
        if not path.is_file():
            problems.append(f"MISSING {name}")
            continue
        source = path.read_text(encoding="utf-8")
        problems.extend(f"INCOMPATIBLE {name}: missing {needle}"
                        for needle in needles if needle not in source)
    # The current entrypoint and species registry still include non-orca animals.
    # Report that honestly; source-readiness does not imply an isolated JAR.
    entrypoint = ROOT / "NeoForgeZawaMod.java"
    registry = ROOT / "world/entity/animal/ZawaEntities.java"
    if entrypoint.is_file() and registry.is_file():
        entry_source = entrypoint.read_text(encoding="utf-8")
        registry_source = registry.read_text(encoding="utf-8")
        other_species = sorted(set(re.findall(r"ZawaEntities\.([A-Z_]+)\.get\(\)", entry_source)) - {"ORCA"})
        other_registrations = sorted(set(re.findall(r'REGISTRAR\.register\("([^"]+)"', registry_source)) - {"orca"})
        print(f"Standalone dependency audit: {len(other_species)} non-orca attribute registrations; "
              f"{len(other_registrations)} non-orca species registry entries.")
        if other_species or other_registrations:
            print("NOT STANDALONE: current mod entrypoint and registry still include other animals.")
    # Confirm that the user-facing spawn egg and natural spawning are wired.
    wiring = {
        "NeoForgeZawaMod.java": [
            "ZawaItems.register(modBus);",
            "modBus.addListener(NeoForgeZawaMod::addOrcaCreativeItems);",
            "event.accept(ZawaItems.ORCA_SPAWN_EGG.get());",
            "ZawaSpawnPlacements.attach(modBus);",
        ],
        "world/item/ZawaItems.java": ['registerItem("orca_spawn_egg"'],
        "world/entity/animal/ZawaEntities.java": ['REGISTRAR.register("orca"'],
        "world/entity/ZawaSpawnPlacements.java": [
            "ZawaEntities.ORCA.get()", "Orca::checkOrcaSpawnRules",
        ],
    }
    for name, needles in wiring.items():
        path = ROOT / name
        if not path.is_file():
            problems.append(f"MISSING {name}")
            continue
        source = path.read_text(encoding="utf-8")
        problems.extend(f"MISSING ORCA WIRING {name}: {needle}"
                        for needle in needles if needle not in source)
    biome = ROOT.parents[3] / "resources/data/zawa/neoforge/biome_modifier/orca_ocean_spawns.json"
    if not biome.is_file():
        problems.append("MISSING orca ocean biome modifier")
    else:
        import json
        data = json.loads(biome.read_text(encoding="utf-8"))
        if data.get("type") != "neoforge:add_spawns" or data.get("spawners", {}).get("type") != "zawa:orca":
            problems.append("INVALID orca ocean biome modifier")
    # Ensure the egg's creative icon and English names are packaged as resources.
    import json
    resources = ROOT.parents[3] / "resources/assets/zawa"
    model = resources / "models/item/orca_spawn_egg.json"
    texture = resources / "textures/item/orca_spawn_egg.png"
    language = resources / "lang/en_us.json"
    if not model.is_file():
        problems.append("MISSING orca spawn egg item model")
    else:
        definition = json.loads(model.read_text(encoding="utf-8"))
        if definition.get("textures", {}).get("layer0") != "zawa:item/orca_spawn_egg":
            problems.append("INVALID orca spawn egg model texture reference")
    if not texture.is_file():
        problems.append("MISSING orca spawn egg texture")
    if not language.is_file():
        problems.append("MISSING English localization")
    else:
        translations = json.loads(language.read_text(encoding="utf-8"))
        for key in ("item.zawa.orca_spawn_egg", "entity.zawa.orca"):
            if not translations.get(key):
                problems.append(f"MISSING localization: {key}")
    if problems:
        raise SystemExit("\n".join(problems))
    print("PASS: existing calf, gestation, rendering and sound corrections detected.")
    print("NOT BUILD VERIFIED: standalone extraction still requires NeoForge compilation.")

if __name__ == "__main__":
    main()
