package org.zawamod.zawa.world.item;

import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;
import org.zawamod.zawa.Zawa;
import org.zawamod.zawa.world.entity.animal.ZawaEntities;

/** Standalone orca mod: the spawn egg is the only added item. */
public final class ZawaItems {
    private ZawaItems() {}

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Zawa.MOD_ID);

    public static final DeferredItem<SpawnEggItem> ORCA_SPAWN_EGG =
            ITEMS.registerItem("orca_spawn_egg",
                    p -> new SpawnEggItem(ZawaEntities.ORCA.get(), 0x171C23, 0xF2F3F0, p));

    public static void register(net.neoforged.bus.api.IEventBus bus) {
        ITEMS.register(bus);
    }
}
