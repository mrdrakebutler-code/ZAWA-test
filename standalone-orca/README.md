# ZAWA Orca — standalone extraction (work in progress)

This is an **independent, incomplete NeoForge 1.21.1 project**. It is not
currently a compiled or playable mod. The full ZAWA port in the repository root
is deliberately kept separate.

## Build prerequisites

- Java 21
- Gradle 8.10.2
- Network access to NeoForge's Maven repository

From the repository root, use the checked-in Gradle 8.10.2 wrapper:

```powershell
.\\gradlew.bat -p standalone-orca build
```

On Linux/macOS: `./gradlew -p standalone-orca build`.
Before a local build, copy the original binary assets into the isolated
source tree:

```powershell
python standalone-orca/scripts/copy_orca_assets.py
python standalone-orca/scripts/copy_orca_item_assets.py
```

The CI workflow performs these copy steps automatically.

A prior standalone GitHub Actions build succeeded on 2026-10-22 (commit
`22a0737582352b6e353eff6d27356d02b4f4e07c`). Later changes need their
own build verification. Compilation alone does not prove in-game rendering,
breeding, or behavior fidelity.

## Implemented scaffolding

- `OrcaOnlyMod`: NeoForge entrypoint and orca attributes/sounds registration
- `OrcaOnlyEntities`: only the `zawa:orca` entity
- `OrcaOnlyClient`: male, female and calf layer/renderer registration
- Independent Gradle configuration and NeoForge metadata

## Remaining validation

The isolated module now contains the orca entity, its standalone offspring
registry, three age/sex model layers, renderer, recovered animation, breeding
goals, and required feeding data. The jump goal was reconstructed from the
original `BreachGoal.class` bytecode, including forward clearance checks,
jump momentum, and pitch animation. Its creative spawn egg is exposed in the
Spawn Eggs tab. CI copies original textures, audio and feeding item artwork.

Remaining work: test the resulting JAR in clean NeoForge 1.21.1 client/server
instances, verify adult/calf geometry and animation, pregnancy and saved
offspring, aquatic movement and air logic, feeding, sounds and persistence.
Do not infer in-game parity from a successful compiler run.

## Compatibility

The standalone project currently uses mod ID `zawa` to preserve the original
resource namespace. It must **not** be loaded alongside the full ZAWA mod
because they share the same mod ID and entity registry name.

## Provenance

The source files and asset fidelity baseline are held in the parent project.
Preserving their behavior is the goal; fidelity to the original 1.20.1 JAR
has not been independently revalidated in this standalone project.


## Known standalone dependency blockers (audit)

The copied feeder block entities are **not** independent: `GroundFeederBlockEntity`
and `WallFeederBlockEntity` extend `ZawaContainerBlockEntity` and refer to
`ZawaBlockEntities` and `FeederBlockContainer`. Those classes and their
registrations must be migrated or the standalone feeder goals refactored before
compilation can succeed. Copying only the two feeder classes does not close
this dependency chain.

`ZawaBaseEntity` also references `ZawaItems` for baby formula and target
stick; those item registrations are not yet provided by the standalone module.
The original orca stats JSON references ZAWA-specific breeding food and kibble
items; simply loading the JSON is not sufficient until their registry IDs are
available. The piscivore diet also includes optional ZAWA fish that are not
registered in the standalone build.

The workflow in `.github/workflows/standalone-orca.yml` compiles and verifies
packaged assets. A prior standalone CI build completed successfully, but
Minecraft runtime testing has not been demonstrated. Do not describe the
standalone orca as gameplay-verified.
