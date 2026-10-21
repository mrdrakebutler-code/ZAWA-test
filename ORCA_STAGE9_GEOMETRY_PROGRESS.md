# Stage 9 — Partial original cuboid decoding

Input: original user-provided ZAWA 1.20.1 alpha5 JAR; model `createBodyLayer` bytecode.

First verified original Body cuboids:
- AdultMale: UV (0,0), box (-15,-17,-21) size (30,34,42), pivot (0,10.5,0).
- AdultFemale: UV (0,0), box (-14,-16,-18) size (28,32,36), pivot (0,11.2,0).
- Child: UV (0,0), box (-9,-9,-12) size (18,18,24), pivot (0,15.9,0).

An initial bytecode stack interpreter recovered 38 adult male, 39 adult female and 21 child addOrReplaceChild records. The parser currently fails on additional builder method patterns, so **the exported JSON is incomplete**. The recovered records are for analysis only, not ready to compile or render.

Next: support additional builder methods, verify counts against original hierarchy (52/52/27), generate `LayerDefinition` source and port animation transforms. No build or in-game test completed.
