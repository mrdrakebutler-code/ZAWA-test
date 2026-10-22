#!/usr/bin/env python3
"""Copy only the original ZAWA feeding-item art needed by standalone orcas.

No replacement textures are generated. Fail if an original model or PNG is
missing or points at unexpected art, rather than shipping a broken item model.
"""
from pathlib import Path
import hashlib
import json
import shutil

ROOT = Path(__file__).resolve().parents[2]
SOURCE = ROOT / "src/main/resources/assets/zawa"
DEST = ROOT / "standalone-orca/src/main/resources/assets/zawa"
ITEMS = ("baby_formula", "target_stick", "large_meat", "piscivore_kibble")


def copy_items():
    copied = []
    for name in ITEMS:
        model = Path("models/item") / (name + ".json")
        source_model = SOURCE / model
        if not source_model.is_file():
            raise FileNotFoundError(f"Missing original item model: {source_model}")
        definition = json.loads(source_model.read_text(encoding="utf-8"))
        expected = f"zawa:item/{name}"
        if definition.get("textures", {}).get("layer0") != expected:
            raise ValueError(f"Unexpected texture for {name}: expected {expected}")
        texture = Path("textures/item") / (name + ".png")
        for relative in (model, texture):
            src = SOURCE / relative
            if not src.is_file():
                raise FileNotFoundError(f"Missing original item asset: {src}")
            dst = DEST / relative
            dst.parent.mkdir(parents=True, exist_ok=True)
            shutil.copyfile(src, dst)
            if hashlib.sha256(src.read_bytes()).digest() != hashlib.sha256(dst.read_bytes()).digest():
                raise RuntimeError(f"Copied item art differs: {relative}")
            copied.append(relative.as_posix())
    print(f"Copied and verified {len(copied)} original ZAWA feeding-item assets")
    return copied


if __name__ == "__main__":
    copy_items()
