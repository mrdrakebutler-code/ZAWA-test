package org.zawamod.zawa.world.inventory;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.zawamod.zawa.Zawa;

/** Container menu registrations shared by ZAWA machines and feeders. */
public final class ZawaMenuTypes {
    private static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, Zawa.MOD_ID);
    public static final DeferredHolder<MenuType<?>, MenuType<FeederBlockContainer>> FEEDER_BLOCK = MENUS.register("feeder_block", () -> new MenuType<>(FeederBlockContainer::new, FeatureFlags.DEFAULT_FLAGS));
    public static final DeferredHolder<MenuType<?>, MenuType<IncubatorMenu>> INCUBATOR = MENUS.register("incubator", () -> new MenuType<>(IncubatorMenu::new, FeatureFlags.DEFAULT_FLAGS));
    public static final DeferredHolder<MenuType<?>, MenuType<BugBoxMenu>> BUG_BOX = MENUS.register("bug_box", () -> new MenuType<>(BugBoxMenu::new, FeatureFlags.DEFAULT_FLAGS));
    public static final DeferredHolder<MenuType<?>, MenuType<HydroponicsTableMenu>> HYDROPONICS_TABLE = MENUS.register("hydroponics_table", () -> new MenuType<>(HydroponicsTableMenu::new, FeatureFlags.DEFAULT_FLAGS));
    public static final DeferredHolder<MenuType<?>, MenuType<MusselBoxMenu>> MUSSEL_BOX = MENUS.register("mussel_box", () -> new MenuType<>(MusselBoxMenu::new, FeatureFlags.DEFAULT_FLAGS));
    private ZawaMenuTypes() {}
    public static void register(IEventBus bus) { MENUS.register(bus); }
}
