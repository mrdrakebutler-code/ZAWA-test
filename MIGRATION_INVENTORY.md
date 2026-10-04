# ZAWA → NeoForge 1.21.1 migration inventory

- Classes: 489
- Classes referencing Forge: 162
- Unique Forge references across classes: 450

## Forge namespace usage (constant-pool scan)

- `net.minecraftforge.registries`: 216 class-reference occurrences
- `net.minecraftforge.common`: 80 class-reference occurrences
- `net.minecraftforge.api`: 68 class-reference occurrences
- `net.minecraftforge.client`: 25 class-reference occurrences
- `net.minecraftforge.fml`: 16 class-reference occurrences
- `net.minecraftforge.event`: 15 class-reference occurrences
- `net.minecraftforge.network`: 11 class-reference occurrences
- `net.minecraftforge.items`: 9 class-reference occurrences
- `net.minecraftforge.eventbus`: 8 class-reference occurrences
- `net.minecraftforge.data`: 1 class-reference occurrences
- `net.minecraftforge.server`: 1 class-reference occurrences

## Highest-impact classes

- `org.zawamod.zawa.Zawa` — 30 unique Forge references
- `org.zawamod.zawa.world.entity.animal.ZawaBaseEntity` — 13 unique Forge references
- `org.zawamod.zawa.data.ZawaBlockStates` — 11 unique Forge references
- `org.zawamod.zawa.event.ZawaEvents` — 11 unique Forge references
- `org.zawamod.zawa.network.ZawaNetworkManager` — 10 unique Forge references
- `org.zawamod.zawa.data.ZawaRecipeProvider` — 9 unique Forge references
- `org.zawamod.zawa.compat.BuddycardsCompat` — 8 unique Forge references
- `org.zawamod.zawa.world.block.entity.BugBoxBlockEntity` — 8 unique Forge references
- `org.zawamod.zawa.world.block.entity.HydroponicsTableBlockEntity` — 8 unique Forge references
- `org.zawamod.zawa.world.entity.ambient.ZawaBaseAmbientEntity` — 7 unique Forge references
- `org.zawamod.zawa.data.ZawaItemModels` — 6 unique Forge references
- `org.zawamod.zawa.data.ZawaTagsProviders$ZawaItemTagsProvider` — 6 unique Forge references
- `org.zawamod.zawa.resources.EntityStatsManager` — 6 unique Forge references
- `org.zawamod.zawa.resources.ZawaReference` — 6 unique Forge references
- `org.zawamod.zawa.world.block.ZawaBlocks` — 6 unique Forge references
- `org.zawamod.zawa.world.block.entity.GroundFeederBlockEntity` — 6 unique Forge references
- `org.zawamod.zawa.world.block.entity.IncubatorBlockEntity` — 6 unique Forge references
- `org.zawamod.zawa.world.block.entity.WallFeederBlockEntity` — 6 unique Forge references
- `org.zawamod.zawa.world.entity.ZawaEntityRegistry` — 6 unique Forge references
- `org.zawamod.zawa.world.entity.item.ZooCart` — 6 unique Forge references
- `org.zawamod.zawa.world.inventory.ZawaMenuTypes` — 6 unique Forge references
- `org.zawamod.zawa.world.item.CaptureNetItem` — 6 unique Forge references
- `org.zawamod.zawa.data.ZawaTagsProviders$ZawaBlockTagsProvider` — 5 unique Forge references
- `org.zawamod.zawa.world.entity.projectile.SlingshotNet` — 5 unique Forge references
- `org.zawamod.zawa.client.color.ColorEvents` — 4 unique Forge references
- `org.zawamod.zawa.client.resources.FoodTypeManager` — 4 unique Forge references
- `org.zawamod.zawa.client.screens.DataBookScreen` — 4 unique Forge references
- `org.zawamod.zawa.config.ZawaMainConfig` — 4 unique Forge references
- `org.zawamod.zawa.data.ZawaBlockModels` — 4 unique Forge references
- `org.zawamod.zawa.resources.EntityDietManager` — 4 unique Forge references
- `org.zawamod.zawa.sounds.ZawaSounds` — 4 unique Forge references
- `org.zawamod.zawa.world.block.entity.ZawaBlockEntities` — 4 unique Forge references
- `org.zawamod.zawa.world.entity.SpawnInfo` — 4 unique Forge references
- `org.zawamod.zawa.world.entity.enrichment.ZawaEnrichmentTypes` — 4 unique Forge references
- `org.zawamod.zawa.world.entity.npc.ZawaVillagers` — 4 unique Forge references
- `org.zawamod.zawa.world.feature.ZawaFeature` — 4 unique Forge references
- `org.zawamod.zawa.world.item.ZawaItems` — 4 unique Forge references
- `org.zawamod.zawa.data.ZawaBlockLoot` — 3 unique Forge references
- `org.zawamod.zawa.data.ZawaEntityLoot` — 3 unique Forge references
- `org.zawamod.zawa.network.protocol.SyncEntityStatsPacket` — 3 unique Forge references

## Recommended migration order

1. `Zawa` bootstrap/event wiring
2. `ZawaEntityRegistry` and entity attribute registration
3. `ZawaItems` / `ZawaBlocks` / block entities / menu types
4. `ZawaNetworkManager` and packet handlers
5. config + reload/datapack listeners
6. client event/render registration
7. data generation classes
8. remaining entity/item/block implementations
9. mixin/access-transformer migration and runtime testing