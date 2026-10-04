package org.zawamod.zawa.world.item;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;

/** Lightweight reconstructed slingshot-net interaction; projectile integration follows in a later projectile stage. */
public class SlingshotNetItem extends Item {
    public SlingshotNetItem(Properties properties) { super(properties.stacksTo(1)); }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        if (player == null) return InteractionResult.PASS;
        if (!context.getLevel().isClientSide) {
            // The original launches a custom projectile. Keep the item interaction authoritative
            // while the projectile entity is migrated in the dedicated projectile pass.
            // Projectile entity migration is intentionally deferred; keep the interaction server-authoritative.
        }
        return InteractionResult.sidedSuccess(context.getLevel().isClientSide);
    }
}
