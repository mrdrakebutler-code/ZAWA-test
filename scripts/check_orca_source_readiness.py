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
        other_species = sorted(set(re.findall(r"ZawaEntities\\.([A-Z_]+)\\.get\\(\\)", entry_source)) - {"ORCA"})
        other_registrations = sorted(set(re.findall(r'REGISTRAR\\.register\\("([^"]+)"', registry_source)) - {"orca"})
        print(f"Standalone dependency audit: {len(other_species)} non-orca attribute registrations; "
              f"{len(other_registrations)} non-orca species registry entries.")
        if other_species or other_registrations:
            print("NOT STANDALONE: current mod entrypoint and registry still include other animals.")
    if problems:
        raise SystemExit("\n".join(problems))
    print("PASS: existing calf, gestation, rendering and sound corrections detected.")
    print("NOT BUILD VERIFIED: standalone extraction still requires NeoForge compilation.")

if __name__ == "__main__":
    main()
