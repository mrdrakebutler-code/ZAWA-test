package org.zawamod.zawa.world.item;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.zawamod.zawa.Zawa;

/**
 * Minimal original-ID item registry for the extracted orca.
 * Keeps only the items directly required by its feeding, training and
 * reproduction systems; does not register other ZAWA animals.
 */
public final class ZawaItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Zawa.MOD_ID);

    public static final DeferredItem<Item> BABY_FORMULA =
            ITEMS.registerItem("baby_formula", Item::new);
    public static final DeferredItem<Item> TARGET_STICK =
            ITEMS.registerItem("target_stick", Item::new);
    public static final DeferredItem<Item> LARGE_MEAT =
            ITEMS.registerItem("large_meat", Item::new);
    public static final DeferredItem<Item> PISCIVORE_KIBBLE =
            ITEMS.registerItem("piscivore_kibble", Item::new);

    private ZawaItems() {}
}
