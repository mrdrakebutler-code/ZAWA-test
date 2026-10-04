package org.zawamod.zawa.world.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.zawamod.zawa.world.entity.item.EnrichmentObjectEntity;

import java.util.function.Supplier;

/** Recovered from the original BallItem/IceTreatItem placement flow. */
public class PlaceableEnrichmentItem extends Item {
    private final Supplier<? extends EntityType<? extends EnrichmentObjectEntity>> type;
    private final EnrichmentObjectEntity.Kind kind;

    public PlaceableEnrichmentItem(Properties properties,
                                   Supplier<? extends EntityType<? extends EnrichmentObjectEntity>> type,
                                   EnrichmentObjectEntity.Kind kind) {
        super(properties);
        this.type = type;
        this.kind = kind;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        BlockHitResult hit = getPlayerPOVHitResult(level, player, ClipContext.Fluid.ANY);
        if (hit.getType() != HitResult.Type.BLOCK) return InteractionResultHolder.pass(stack);

        EnrichmentObjectEntity entity = new EnrichmentObjectEntity(type.get(), level);
        entity.setKind(kind);
        entity.setPos(hit.getLocation().x, hit.getLocation().y + 0.05D, hit.getLocation().z);
        entity.setYRot(player.getYRot());
        if (!level.noCollision(entity, entity.getBoundingBox().deflate(0.1D))) return InteractionResultHolder.fail(stack);

        if (!level.isClientSide) {
            level.addFreshEntity(entity);
            if (!player.getAbilities().instabuild) stack.shrink(1);
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }
}
