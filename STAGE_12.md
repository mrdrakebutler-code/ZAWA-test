# ZAWA NeoForge 1.21.1 — Stage 12

## Concrete species batch 1
Recovered the first nine concrete animal classes from the original 1.20.1 bytecode:
- African Lion
- African Wild Dog
- Asian Elephant
- Bald Eagle
- Black-footed Ferret
- Common Chimpanzee
- Coquerel's Sifaka
- Emperor Penguin
- Flamingo

Recovered species-specific default attributes and exact original EntityType dimensions/client tracking ranges from ZawaEntities bytecode. Added NeoForge entity registration and EntityAttributeCreationEvent registration.

The remaining species-specific interfaces/AI/renderers/items are deliberately not fabricated; they remain targets for subsequent stages.

Build status: source reconstruction stage; full Gradle compilation still requires a working Gradle/NeoForge dependency environment.
