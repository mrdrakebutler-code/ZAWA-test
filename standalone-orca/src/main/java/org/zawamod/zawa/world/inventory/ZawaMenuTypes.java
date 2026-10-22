package org.zawamod.zawa.world.inventory;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.zawamod.zawa.Zawa;

/** Only the feeder inventory menu needed by the standalone orca. */
public final class ZawaMenuTypes {
    private static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, Zawa.MOD_ID);
    public static final DeferredHolder<MenuType<?>, MenuType<FeederBlockContainer>> FEEDER_BLOCK =
            MENUS.register("feeder_block", () ->
                    new MenuType<>(FeederBlockContainer::new, FeatureFlags.DEFAULT_FLAGS));
    private ZawaMenuTypes() {}
    public static void register(IEventBus bus) { MENUS.register(bus); }
}
