# Stage 7 — Model hierarchy extraction

Recovered from the original user-supplied ZAWA 1.20.1 JAR using javap bytecode. Parent-child links were parsed from the original model constructors, not invented.

All three model variants are rooted at `Body`, and the tail parts form articulated chains.

The original model is not yet translated to NeoForge source. The exact original cuboids, pivots, and rotations still require bytecode-to-Java translation, followed by compilation and in-game visual verification.

See downloadable `orca_original_hierarchy.json` in the conversation for the machine-readable mapping of 52 adult male, 52 adult female and 27 calf part names to parents.

Do not confuse this dependency map with a finished renderer or model.
