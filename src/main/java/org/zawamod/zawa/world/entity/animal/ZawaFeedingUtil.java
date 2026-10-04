package org.zawamod.zawa.world.entity.animal;

import net.minecraft.world.item.ItemStack;

/** Shared Stage 33 hooks for feeders and enrichment blocks. */
public final class ZawaFeedingUtil {
    private ZawaFeedingUtil() {}

    public static boolean feed(ZawaBaseEntity animal, ItemStack stack) {
        if (stack.isEmpty() || !animal.consumeDietItem(stack)) return false;
        stack.shrink(1);
        return true;
    }

    public static void provideWater(ZawaBaseEntity animal, int amount) {
        animal.drink(amount);
    }

    public static void provideEnrichment(ZawaBaseEntity animal, int amount) {
        animal.enrich(amount);
    }
    public static void feedTreat(ZawaBaseEntity animal, boolean plantTreat) {
        int hunger = Math.max(1, animal.getHunger().getMax() / 8);
        animal.getHunger().add(hunger);
        animal.heal(plantTreat ? 1.0F : 2.0F);
    }

}
