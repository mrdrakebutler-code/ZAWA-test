# ZAWA Orca — standalone extraction (work in progress)

This is an **independent, incomplete NeoForge 1.21.1 project**. It is not
currently a compiled or playable mod. The full ZAWA port in the repository root
is deliberately kept separate.

## Build prerequisites

- Java 21
- Gradle 8.10.2
- Network access to NeoForge's Maven repository

From the repository root:

```bash
cd standalone-orca
gradle build --stacktrace
```

**Expected at this stage:** compilation cannot succeed until the referenced
ZAWA orca classes and their transitive dependencies are moved or adapted into
this isolated source tree. Do not distribute the resulting artifact as a
working orca mod without successful compilation and Minecraft testing.

## Implemented scaffolding

- `OrcaOnlyMod`: NeoForge entrypoint and orca attributes/sounds registration
- `OrcaOnlyEntities`: only the `zawa:orca` entity
- `OrcaOnlyClient`: male, female and calf layer/renderer registration
- Independent Gradle configuration and NeoForge metadata

## Outstanding extraction

1. Bring over `Zawa`, `Orca`, `OrcaSounds`, `OrcaRenderer`, the three layer
   definitions, `OrcaModel`, and `ZawaModelLayers`.
2. Identify and migrate/adapt `ZawaAquaticEntity`, `ZawaBaseEntity`, their AI
   goals, and the shared diet/stats, gestation and breeding dependencies.
3. Replace `Orca#getBreedOffspring`'s dependency on the full
   `ZawaEntities.ORCA` registry with the standalone registry.
4. Copy only the required orca textures, sound definitions, and OGG files,
   preserving the recorded asset hashes. Verify resource paths.
5. Compile, fix API errors, and test spawning, movement, attacks, breeding,
   calves, rendering and audio in a NeoForge 1.21.1 client and server.

## Compatibility

The standalone project currently uses mod ID `zawa` to preserve the original
resource namespace. It must **not** be loaded alongside the full ZAWA mod
because they share the same mod ID and entity registry name.

## Provenance

The source files and asset fidelity baseline are held in the parent project.
Preserving their behavior is the goal; fidelity to the original 1.20.1 JAR
has not been independently revalidated in this standalone project.
