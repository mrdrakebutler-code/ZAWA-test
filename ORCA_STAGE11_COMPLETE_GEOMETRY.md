# Stage 11 — Original Orca geometry fully decoded

The bytecode parser was missing `CubeListBuilder.m_171555_(Z)`, the mirrored-cuboid operation. Supporting this instruction resolved all extraction failures.

Recovered part counts from the original user-supplied ZAWA 1.20.1 JAR:
- Adult male: 52/52
- Adult female: 52/52
- Child: 27/27

All original cuboid dimensions, UV offsets, mirror flags, pivot offsets and part rotations are represented in the generated Java LayerDefinition sources distributed in the Stage 11 conversation ZIP.

**Not yet committed:** Generated Java layer sources. **Not yet done:** verify atlas dimensions, wire models to renderer, port animations, compile, and test in-game.
