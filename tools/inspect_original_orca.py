#!/usr/bin/env python3
"""Inventory original ZAWA 1.20.1 JAR/decompiled ZIP for faithful orca port.

Usage: python tools/inspect_original_orca.py /path/to/zawa-original.jar
       python tools/inspect_original_orca.py /path/to/zawa-decompiled.zip
Only reads the archive. Prints matching paths; does not extract copyrighted assets.
"""
import argparse
import zipfile
from pathlib import Path

KEYWORDS = (
    "orca", "killerwhale", "killer_whale", "cetacean", "aquatic",
    "breed", "pregnan", "birth", "animation", "model", "sound",
    "geckolib", "geo/", "animations/"
)

def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("archive", type=Path)
    parser.add_argument("--all", action="store_true", help="Include broad shared classes/assets")
    args = parser.parse_args()
    if not args.archive.is_file():
        parser.error(f"File not found: {args.archive}")
    if not zipfile.is_zipfile(args.archive):
        parser.error("Expected a valid JAR or ZIP archive")
    with zipfile.ZipFile(args.archive) as z:
        paths = [i.filename for i in z.infolist() if not i.is_dir()]
    direct = [p for p in paths if any(x in p.lower() for x in ("orca", "killerwhale", "killer_whale"))]
    related = [p for p in paths if any(x in p.lower() for x in KEYWORDS) and p not in direct]
    print(f"Archive: {args.archive.name} ({len(paths)} files)")
    print(f"\nOrca-specific paths ({len(direct)}):")
    for p in direct:
        print("  " + p)
    if args.all:
        print(f"\nPotential shared dependencies ({len(related)}):")
        for p in related:
            print("  " + p)
    if not direct:
        print("\nNo orca-named entries. Inspect package naming and archive contents manually.")

if __name__ == "__main__":
    main()
