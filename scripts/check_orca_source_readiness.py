#!/usr/bin/env python3
"""Fail-fast source checks for the standalone orca extraction branch.

These checks do not replace a NeoForge build or in-game testing.
Run from the repository root: python3 scripts/check_orca_source_readiness.py
"""
from pathlib import Path

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
    if problems:
        raise SystemExit("\n".join(problems))
    print("PASS: existing calf, gestation, rendering and sound corrections detected.")
    print("NOT BUILD VERIFIED: standalone extraction still requires NeoForge compilation.")

if __name__ == "__main__":
    main()
