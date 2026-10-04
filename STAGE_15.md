# Stage 15 — Final three primary species

Recovered and registered the final three primary species from the original ZAWA registry:
- Sumatran Orangutan — 1.0 x 1.5, movement 0.225, health 26, attack 6
- Tree Frog — 0.3 x 0.3, movement 0.225, health 4, attack 1
- Western Lowland Gorilla — 1.2 x 1.8, movement 0.225, health 34, attack 10

The dimensions and core attributes above were read from the supplied 1.20.1 compiled classes/registry bytecode. Specialized climbing, sitting, jumping, oviparous, sound, animation, and renderer behavior remains a later migration task; this stage does not replace those behaviors with fabricated implementations.

NeoForge natural spawning still requires explicit spawn restrictions; the next stage will migrate ZAWA's spawn-placement and biome spawn data. See NeoForge 1.21.1 biome-modifier documentation.
