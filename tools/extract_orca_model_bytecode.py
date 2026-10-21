#!/usr/bin/env python3
"""Extract readable original orca model bytecode for faithful NeoForge translation.

Requires JDK javap and the user's original ZAWA 1.20.1 JAR.
Does not modify the archive, download anything, or redistribute original assets.

Example:
 python tools/extract_orca_model_bytecode.py zawa-original.jar --out build/orca-bytecode
"""
import argparse
import pathlib
import shutil
import subprocess

MODELS = (
    "org.zawamod.zawa.client.model.OrcaModel",
    "org.zawamod.zawa.client.model.OrcaModel$AdultMale",
    "org.zawamod.zawa.client.model.OrcaModel$AdultFemale",
    "org.zawamod.zawa.client.model.OrcaModel$Child",
)

def main():
    p = argparse.ArgumentParser(description=__doc__)
    p.add_argument("jar", type=pathlib.Path)
    p.add_argument("--out", type=pathlib.Path, default=pathlib.Path("build/orca-bytecode"))
    args = p.parse_args()
    if not args.jar.is_file():
        p.error(f"Missing original JAR: {args.jar}")
    if shutil.which("javap") is None:
        p.error("JDK javap executable not found in PATH")
    args.out.mkdir(parents=True, exist_ok=True)
    for cls in MODELS:
        result = subprocess.run(
            ["javap", "-classpath", str(args.jar.resolve()), "-c", "-p", cls],
            check=True, capture_output=True, text=True
        )
        output = args.out / (cls.rsplit(".", 1)[-1].replace("$", "_") + ".javap.txt")
        output.write_text(result.stdout, encoding="utf-8")
        print(f"{cls} -> {output}")

if __name__ == "__main__":
    main()
