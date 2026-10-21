# Stage 8 — Java model part hierarchy generated

Generated three Java classes from the original ZAWA 1.20.1 orca model parent-child map:

- `OrcaAdultMaleParts.java` — 52 named parts
- `OrcaAdultFemaleParts.java` — 52 named parts
- `OrcaChildParts.java` — 27 named parts

The source ZIP is available as `orca_stage8_model_part_sources.zip` in the associated ChatGPT conversation. The Java files have **not** been committed individually to this repository yet.

These classes resolve `ModelPart` children and represent original hierarchy only. They are not complete `LayerDefinition` model implementations; cuboids, pivots, texture coordinates, animations, renderer registration, and testing remain outstanding. No build or JAR has been produced.
