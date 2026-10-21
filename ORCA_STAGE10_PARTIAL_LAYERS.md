# Stage 10 — Partial NeoForge LayerDefinition generation

Generated three Java `LayerDefinition` classes from the original ZAWA 1.20.1 JAR bytecode extraction:
- `OrcaAdultMaleLayer.java`: 38 recovered geometry records / 52 named parts.
- `OrcaAdultFemaleLayer.java`: 39 / 52.
- `OrcaChildLayer.java`: 21 / 27.

The generated sources include original recovered cube UV offsets, boxes, pivots and rotations. Missing records currently have empty builders and zero transforms to keep the named hierarchy intact. Texture atlas dimensions are 512 × 256 as confirmed by original adult male bytecode.

The Java source files are provided in the conversation's `orca_stage10_partial_layers.zip`; they have **not** been committed as source files to GitHub.

**Not ready for use:** Missing parts, original animations and renderer still need reconstruction. No compilation or runtime test has been performed. Next step is fixing the bytecode interpreter to handle all original builder operations and regenerating complete layers.
