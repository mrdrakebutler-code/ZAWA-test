#!/usr/bin/env python3
"""Audit orca texture atlas sizes and reconstructed model UV rectangles."""
from pathlib import Path
import re
import struct

ROOT = Path(__file__).resolve().parents[1]
TEXTURES = ROOT / "src/main/resources/assets/zawa/textures/entity/orca"
MODELS = ROOT / "src/main/java/org/zawamod/zawa/client/model/orca"
LAYERS = ["OrcaAdultMaleLayer.java", "OrcaAdultFemaleLayer.java", "OrcaChildLayer.java"]
errors = 0
for filename in LAYERS:
    source = (MODELS / filename).read_text()
    atlas = re.search(r"LayerDefinition\.create\(mesh,\s*(\d+),\s*(\d+)\)", source)
    if not atlas:
        print(f"FAIL {filename}: atlas declaration missing")
        errors += 1
        continue
    w, h = map(int, atlas.groups())
    print(f"{filename}: declared atlas {w}x{h}")
    # This checks the simple unrotated box UV footprint; Minecraft cube UV
    # layouts use 2*(depth+width) horizontally and depth+height vertically.
    pattern = r"texOffs\((\d+),\s*(\d+)\).*?addBox\([^)]*?,\s*([\d.]+)F,\s*([\d.]+)F,\s*([\d.]+)F\)"
    for match in re.finditer(pattern, source):
        u, v = map(int, match.group(1, 2))
        dx, dy, dz = map(float, match.group(3, 4, 5))
        if u + 2 * (dx + dz) > w + 1 or v + dy + dz > h + 1:
            print(f"  WARN: UV cuboid near ({u},{v}) dimensions ({dx},{dy},{dz}) may exceed atlas")
    selected = ([p for p in TEXTURES.glob("orca_*_male.png")] if "Male" in filename
                else [p for p in TEXTURES.glob("orca_*_female.png")] if "Female" in filename
                else [TEXTURES / "orca_baby.png"])
    for png in sorted(selected):
        data = png.read_bytes()[:24]
        if data[:8] != b"\x89PNG\r\n\x1a\n":
            print(f"FAIL invalid PNG: {png.name}")
            errors += 1
            continue
        pw, ph = struct.unpack(">II", data[16:24])
        if (pw, ph) != (w, h):
            print(f"  MISMATCH {png.name}: actual {pw}x{ph}, declared {w}x{h}")
            errors += 1
print("UV atlas audit complete")
raise SystemExit(1 if errors else 0)
