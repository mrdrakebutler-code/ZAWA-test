#!/usr/bin/env python3
"""Guard the recovered ZAWA orca's original geometry, texture and sound bytes.

SHA-1 values are Git blob IDs recorded from the experimental branch on
2026-10-22. This establishes branch fidelity, not independent verification
against the unavailable original 1.20.1 JAR.
Run from any directory: python3 scripts/check_orca_fidelity.py
"""
from __future__ import annotations

import hashlib
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
MODEL = "src/main/java/org/zawamod/zawa/client/model/orca"
TEXTURE = "src/main/resources/assets/zawa/textures/entity/orca"
AUDIO = "src/main/resources/assets/zawa/sounds"

BASELINE = {
    f"{MODEL}/OrcaAdultFemaleLayer.java": "f3dad5d9d1a6fc528a360fd62ec7cbd14fc79e75",
    f"{MODEL}/OrcaAdultMaleLayer.java": "0dfe2df26a89308890f890deaf26cc0a936e8c91",
    f"{MODEL}/OrcaChildLayer.java": "04566a0f68bf208f2124194625b8fe97feb52f95",
    f"{MODEL}/OrcaModel.java": "301b00c6daa5313e694aea27682a6a7224b19d36",
    f"{TEXTURE}/orca_1_female.png": "6daceb73352364b4a82b7885b154db4a0c5f59cc",
    f"{TEXTURE}/orca_1_male.png": "62ea904c57aafd4cf01fd5f1cc1ceb7c90d401dc",
    f"{TEXTURE}/orca_2_female.png": "496ddde3522e064ecb4c10728fe51e9a95267852",
    f"{TEXTURE}/orca_2_male.png": "44810183b633f8503f962c33ac6f4631fbdb08a2",
    f"{TEXTURE}/orca_3_female.png": "7cc48db979f518cc32f1008b45817f63df019a44",
    f"{TEXTURE}/orca_3_male.png": "80270c76f608dac1b50f1722abed319cc1d70b06",
    f"{TEXTURE}/orca_4_female.png": "f763d2230ac2f5f3897c1c1113e9c764e446fffc",
    f"{TEXTURE}/orca_4_male.png": "39e2c9d4ba7f7a9379daf2fc77fa611b50cf4b4d",
    f"{TEXTURE}/orca_baby.png": "8f34b2eb90f192776568016dfa8d6e156afbdded",
    f"{AUDIO}/orca_ambient.ogg": "3fa15554195020af44f60b634eeb6407561cb1ed",
    f"{AUDIO}/orca_attacked.ogg": "88bd7daa39cffd97134d0086bf3cbd0df19b6866",
    f"{AUDIO}/orca_attacking.ogg": "95d2436dce4e7d81ce4b4de3844d21118a176ede",
    f"{AUDIO}/orca_broadcast.ogg": "81c4a50ed6b164176125364f9c628e3a4c326fc8",
    f"{AUDIO}/orca_communicating.ogg": "3a0bf08116e42ae7d31bf43bb92209af4b6eae7a",
}


def git_blob_id(data: bytes) -> str:
    return hashlib.sha1(b"blob " + str(len(data)).encode("ascii") + b"\\0" + data).hexdigest()


def verify(root: Path = ROOT) -> None:
    failures = []
    for relative, expected in BASELINE.items():
        path = root / relative
        if not path.is_file():
            failures.append(f"MISSING {relative}")
            continue
        actual = git_blob_id(path.read_bytes())
        if actual != expected:
            failures.append(f"MODIFIED {relative}: expected {expected}, got {actual}")
    for folder, pattern in ((MODEL, "*.java"), (TEXTURE, "*.png"), (AUDIO, "orca_*.ogg")):
        expected_names = {Path(name).name for name in BASELINE if str(Path(name).parent) == folder}
        found_names = {p.name for p in (root / folder).glob(pattern)}
        if expected_names != found_names:
            failures.append(f"UNEXPECTED INVENTORY {folder}: missing={sorted(expected_names - found_names)}, extra={sorted(found_names - expected_names)}")
    if failures:
        raise SystemExit("Orca fidelity check FAILED:\\n" + "\\n".join(failures))
    print(f"Orca fidelity check passed: {len(BASELINE)} original branch files unchanged "
          "(4 model sources, 9 textures, 5 sound files).")


if __name__ == "__main__":
    verify()
