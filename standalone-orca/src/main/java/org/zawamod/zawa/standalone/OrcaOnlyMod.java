package org.zawamod.zawa.standalone;

import net.neoforged.bus.api.IEventBus;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import org.zawamod.zawa.config.ZawaMainConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import org.zawamod.zawa.resources.EntityDietManager;
import org.zawamod.zawa.resources.EntityStatsManager;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import org.zawamod.zawa.Zawa;
import org.zawamod.zawa.world.entity.animal.Orca;
import org.zawamod.zawa.world.entity.animal.OrcaSounds;
import org.zawamod.zawa.world.item.ZawaItems;
import org.zawamod.zawa.world.block.ZawaBlocks;
import org.zawamod.zawa.world.block.entity.ZawaBlockEntities;
import org.zawamod.zawa.world.inventory.ZawaMenuTypes;

/**
 * Standalone extraction entrypoint. Registers the orca and its supporting
 * resources without activating the full-ZAWA mod entrypoint.
 * The isolated build retains supporting feeding and configuration registries.
 */
@Mod(Zawa.MOD_ID)
public final class OrcaOnlyMod {
    public OrcaOnlyMod(IEventBus modBus, ModContainer container) {
        container.registerConfig(ModConfig.Type.COMMON, ZawaMainConfig.CONFIG_SPEC);
        OrcaOnlyEntities.register(modBus);
        ZawaItems.ITEMS.register(modBus);
        ZawaBlocks.BLOCKS.register(modBus);
        ZawaBlockEntities.BLOCK_ENTITIES.register(modBus);
        ZawaMenuTypes.register(modBus);
        OrcaSounds.register(modBus);
        modBus.addListener(OrcaOnlyMod::addOrcaCreativeItems);
        modBus.addListener(OrcaOnlyMod::registerAttributes);
        modBus.addListener(OrcaOnlyMod::registerSpawnPlacements);
        NeoForge.EVENT_BUS.addListener(OrcaOnlyMod::registerDataReloadListeners);
    }

    private static void addOrcaCreativeItems(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            event.accept(ZawaItems.ORCA_SPAWN_EGG.get());
        }
    }

    private static void registerDataReloadListeners(AddReloadListenerEvent event) {
        event.addListener(EntityDietManager.INSTANCE);
        event.addListener(EntityStatsManager.INSTANCE);
    }

    private static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(OrcaOnlyEntities.ORCA.get(), SpawnPlacementTypes.IN_WATER,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                WaterAnimal::checkSurfaceWaterAnimalSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }

    private static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(OrcaOnlyEntities.ORCA.get(), Orca.registerOrcaAttributes().build());
    }
}
