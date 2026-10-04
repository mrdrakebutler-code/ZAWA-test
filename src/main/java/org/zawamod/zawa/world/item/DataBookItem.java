package org.zawamod.zawa.world.item;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.zawamod.zawa.client.screen.DataBookScreen;

/** ZAWA encyclopedia/data-book interaction item. */
public class DataBookItem extends Item {
    public DataBookItem(Properties properties) { super(properties.stacksTo(1)); }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, net.minecraft.world.InteractionHand hand) {
        if (player.level().isClientSide) {
            openEntityScreen(target);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, net.minecraft.world.InteractionHand hand) {
        if (level.isClientSide) openEncyclopediaScreen(level);
        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide);
    }

    private void openEntityScreen(LivingEntity entity) {
        net.minecraft.client.Minecraft.getInstance().setScreen(new DataBookScreen(entity, entity.level()));
    }

    private void openEncyclopediaScreen(Level level) {
        net.minecraft.client.Minecraft.getInstance().setScreen(new DataBookScreen(null, level));
    }
}
