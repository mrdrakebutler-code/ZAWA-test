package org.zawamod.zawa.world.item;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.zawamod.zawa.world.entity.animal.ZawaBaseEntity;

import java.util.List;

/** Capture/release container used by ZAWA's animal handling loop. */
public class CaptureNetItem extends Item {
    public CaptureNetItem(Properties properties) { super(properties.stacksTo(1)); }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        if (!canCapture(target, player)) return InteractionResult.PASS;
        if (hasCaptured(stack)) {
            player.displayClientMessage(Component.translatable("chat.zawa.capture_net.full"), true);
            return InteractionResult.SUCCESS;
        }
        if (player.level().isClientSide) return InteractionResult.SUCCESS;

        CompoundTag captured = new CompoundTag();
        target.saveWithoutId(captured);
        ResourceLocation id = target.getType().builtInRegistryHolder().key().location();
        captured.putString("id", id.toString());
        captured.putBoolean("CapturedEntity", true);
        if (target instanceof TamableAnimal tame && tame.isTame()) {
            captured.putString("OwnerName", player.getGameProfile().name());
        }
        stack.getOrCreateTag().put("CapturedEntity", captured);
        target.discard();
        player.setItemInHand(hand, stack);
        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack stack = context.getItemInHand();
        Player player = context.getPlayer();
        Level level = context.getLevel();
        if (!hasCaptured(stack) || player == null) return InteractionResult.PASS;
        if (level.isClientSide) return InteractionResult.SUCCESS;

        CompoundTag captured = stack.getOrCreateTag().getCompound("CapturedEntity").copy();
        Entity entity = createCaptured(captured, level);
        if (entity == null) return InteractionResult.FAIL;

        BlockPos pos = context.getClickedPos().relative(context.getClickedFace());
        entity.moveTo(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, player.getYRot(), 0.0F);
        level.addFreshEntity(entity);
        stack.getOrCreateTag().remove("CapturedEntity");
        player.displayClientMessage(Component.translatable("chat.zawa.capture_net.release"), true);
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    private Entity createCaptured(CompoundTag tag, Level level) {
        try {
            return EntityTypeHelper.create(tag, level);
        } catch (Exception ignored) {
            return null;
        }
    }

    private boolean canCapture(LivingEntity target, Player player) {
        if (target == player || target.isDeadOrDying()) return false;
        if (target instanceof Player) return false;
        if (target instanceof ZawaBaseEntity zawa) {
            // Original net accepts only the smaller size categories for ordinary ZAWA animals.
            return zawa.getSpeciesSizeOrdinal() < 4;
        }
        return target instanceof TamableAnimal || target.getType().builtInRegistryHolder().key().location().getNamespace().equals("zawa");
    }

    private static boolean hasCaptured(ItemStack stack) {
        return stack.hasTag() && stack.getTag().contains("CapturedEntity", 10);
    }

    /** Isolated helper keeps entity construction out of the interaction methods. */
    private static final class EntityTypeHelper {
        private static Entity create(CompoundTag tag, Level level) {
            return net.minecraft.world.entity.EntityType.create(tag, level).orElse(null);
        }
    }
}
