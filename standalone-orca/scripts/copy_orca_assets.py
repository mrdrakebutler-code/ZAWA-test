#!/usr/bin/env python3
"""Copy only the recorded orca PNG/OGG assets from the full port.

Run from anywhere in the checkout: python3 standalone-orca/scripts/copy_orca_assets.py
Binary files are copied byte-for-byte; the original source is not modified.
"""
from pathlib import Path
import hashlib
import shutil

ROOT = Path(__file__).resolve().parents[2]
DEST = ROOT / "standalone-orca" / "src" / "main" / "resources" / "assets" / "zawa"
SOURCE = ROOT / "src" / "main" / "resources" / "assets" / "zawa"
TEXTURES = ("orca_1_female.png", "orca_1_male.png",
            "orca_2_female.png", "orca_2_male.png",
            "orca_3_female.png", "orca_3_male.png",
            "orca_4_female.png", "orca_4_male.png", "orca_baby.png")
SOUNDS = ("orca_ambient.ogg", "orca_attacked.ogg", "orca_attacking.ogg",
          "orca_broadcast.ogg", "orca_communicating.ogg")

def copy_assets():
    count = 0
    for subfolder, names in (("textures/entity/orca", TEXTURES), ("sounds", SOUNDS)):
        for name in names:
            src = SOURCE / subfolder / name
            if not src.is_file():
                raise FileNotFoundError(f"Missing original asset: {src}")
            dst = DEST / subfolder / name
            dst.parent.mkdir(parents=True, exist_ok=True)
            shutil.copyfile(src, dst)
            if hashlib.sha256(src.read_bytes()).digest() != hashlib.sha256(dst.read_bytes()).digest():
                raise RuntimeError(f"Asset differs after copying: {name}")
            count += 1
    print(f"Copied and verified {count} orca assets (9 PNG, 5 OGG).")

if __name__ == "__main__":
    copy_assets()
