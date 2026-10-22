package org.zawamod.zawa.standalone;

import net.neoforged.bus.api.IEventBus;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.fml.common.Mod;
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
 * Standalone extraction entrypoint. Only the orca entity, its attributes and
 * original sounds are registered here. Not used by the full-ZAWA build.
 *
 * The isolated Gradle source set and dependency closure remain pending.
 */
@Mod(Zawa.MOD_ID)
public final class OrcaOnlyMod {
    public OrcaOnlyMod(IEventBus modBus) {
        OrcaOnlyEntities.register(modBus);
        ZawaItems.ITEMS.register(modBus);
        ZawaBlocks.BLOCKS.register(modBus);
        ZawaBlockEntities.BLOCK_ENTITIES.register(modBus);
        ZawaMenuTypes.register(modBus);
        OrcaSounds.register(modBus);
        modBus.addListener(OrcaOnlyMod::addOrcaCreativeItems);
        modBus.addListener(OrcaOnlyMod::registerAttributes);
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

    private static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(OrcaOnlyEntities.ORCA.get(), Orca.registerOrcaAttributes().build());
    }
}
