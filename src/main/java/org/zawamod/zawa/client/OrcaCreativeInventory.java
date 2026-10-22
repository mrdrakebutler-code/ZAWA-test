package org.zawamod.zawa.client;

import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import org.zawamod.zawa.Zawa;
import org.zawamod.zawa.world.item.ZawaItems;

/**
 * Exposes the standalone orca's registered supplies in vanilla creative tabs.
 * This uses the NeoForge MOD event bus, not the game event bus.
 */
@EventBusSubscriber(modid = Zawa.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public final class OrcaCreativeInventory {
    private OrcaCreativeInventory() {}

    @SubscribeEvent
    public static void addCreativeItems(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            event.accept(ZawaItems.ORCA_SPAWN_EGG);
        }
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ZawaItems.PISCIVORE_KIBBLE);
            event.accept(ZawaItems.LARGE_MEAT);
            event.accept(ZawaItems.COOKED_LARGE_MEAT);
        }
    }
}
