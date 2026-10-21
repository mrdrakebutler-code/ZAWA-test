# Stage 17 — Original orca texture atlas verification

Inspected PNG headers directly from the original user-supplied ZAWA 1.20.1 IntelligentAnimals3-alpha5 JAR.

- `orca_1_male.png` through `orca_4_male.png`: **1024 × 512** each.
- `orca_1_female.png` through `orca_4_female.png`: **1024 × 512** each.
- `orca_baby.png`: **512 × 256**.

The Stage 11 generated adult LayerDefinition sources erroneously used `LayerDefinition.create(mesh, 512, 256)`. The local Stage 17 ZIP corrects the adult male and female sources to `LayerDefinition.create(mesh, 1024, 512)`, retaining `512, 256` for the calf.

**Integration blocker:** The three corrected geometry source files are not yet present in the experimental GitHub branch. The active client renderer remains generic until the corrected layers are committed and registered. `OrcaRenderer` and `OrcaModel` have been committed, but are not active. No compilation or visual test is claimed.

Next: commit corrected geometry classes, wire client registration, run compile/test, then translate original animations.
