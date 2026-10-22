package org.zawamod.zawa;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import org.zawamod.zawa.resources.EntityDietManager;
import org.zawamod.zawa.resources.EntityStatsManager;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import org.zawamod.zawa.config.ZawaMainConfig;
import org.zawamod.zawa.world.entity.ZawaEntityRegistry;
import org.zawamod.zawa.world.item.ZawaItems;
import org.zawamod.zawa.world.inventory.ZawaMenuTypes;
import org.zawamod.zawa.world.block.ZawaBlocks;
import org.zawamod.zawa.world.block.entity.ZawaBlockEntities;
import org.zawamod.zawa.world.entity.ZawaSpawnPlacements;
import org.zawamod.zawa.world.entity.animal.ZawaEntities;

@Mod(Zawa.MOD_ID)
public final class NeoForgeZawaMod {
    public NeoForgeZawaMod(IEventBus modBus, ModContainer container) {
        container.registerConfig(ModConfig.Type.COMMON, ZawaMainConfig.CONFIG_SPEC);
        NeoForge.EVENT_BUS.addListener(NeoForgeZawaMod::addReloadListeners);

        ZawaEntityRegistry entities = new ZawaEntityRegistry(Zawa.MOD_ID);
        entities.register(modBus);
        ZawaItems.register(modBus);
        modBus.addListener(NeoForgeZawaMod::addOrcaCreativeItems);
        org.zawamod.zawa.world.entity.animal.OrcaSounds.register(modBus);
        ZawaMenuTypes.register(modBus);
        ZawaBlocks.BLOCKS.register(modBus);
        ZawaBlockEntities.BLOCK_ENTITIES.register(modBus);
        modBus.addListener(entities::registerAttributes);
        ZawaEntities.register(modBus);
        org.zawamod.zawa.world.entity.item.ZawaEnrichmentEntities.register(modBus);
        ZawaSpawnPlacements.attach(modBus);
        modBus.addListener((EntityAttributeCreationEvent event) -> {
            event.put(ZawaEntities.ORCA.get(), org.zawamod.zawa.world.entity.animal.Orca.registerOrcaAttributes().build());
        });
    }

    private static void addOrcaCreativeItems(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            event.accept(ZawaItems.ORCA_SPAWN_EGG.get());
        }
    }

    private static void addReloadListeners(AddReloadListenerEvent event) {
        event.addListener(EntityDietManager.INSTANCE);
        event.addListener(EntityStatsManager.INSTANCE);
    }
}
