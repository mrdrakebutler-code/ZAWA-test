package org.zawamod.zawa.world.entity.ai.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.zawamod.zawa.world.block.entity.GroundFeederBlockEntity;
import org.zawamod.zawa.world.block.entity.WallFeederBlockEntity;
import org.zawamod.zawa.world.entity.animal.ZawaBaseEntity;
import org.zawamod.zawa.world.entity.animal.ZawaFeedingUtil;

import java.util.EnumSet;

/**
 * Stage 34 enclosure-care goal. Hungry animals search a bounded area for a
 * ZAWA feeder containing food from their data-driven diet.
 */
public final class SeekFeederGoal extends Goal {
    private final ZawaBaseEntity animal;
    private final double speed;
    private final int radius;
    private BlockPos target;
    private int cooldown;

    public SeekFeederGoal(ZawaBaseEntity animal, double speed, int radius) {
        this.animal = animal;
        this.speed = speed;
        this.radius = radius;
        setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override public boolean canUse() {
        if (cooldown-- > 0 || animal.getHunger().getValue() >= animal.getHunger().getMax()) return false;
        cooldown = 40 + animal.getRandom().nextInt(40);
        target = findFeeder();
        return target != null;
    }

    @Override public boolean canContinueToUse() {
        return target != null && animal.getHunger().getValue() < animal.getHunger().getMax()
                && animal.blockPosition().distSqr(target) <= radius * radius * 2L;
    }

    @Override public void start() { move(); }
    @Override public void tick() {
        if (target == null) return;
        if (animal.blockPosition().distSqr(target) <= 4.0D) {
            BlockEntity be = animal.level().getBlockEntity(target);
            ItemStack food = feederStack(be);
            if (!food.isEmpty() && ZawaFeedingUtil.feed(animal, food)) {
                if (be != null) be.setChanged();
            }
            stop();
        } else if (animal.getNavigation().isDone()) move();
    }

    private void move() {
        if (target != null) animal.getNavigation().moveTo(target.getX() + .5, target.getY(), target.getZ() + .5, speed);
    }

    @Override public void stop() {
        target = null;
        animal.getNavigation().stop();
    }

    private BlockPos findFeeder() {
        BlockPos origin = animal.blockPosition();
        BlockPos best = null;
        double bestDist = Double.MAX_VALUE;
        for (BlockPos pos : BlockPos.betweenClosed(origin.offset(-radius, -3, -radius), origin.offset(radius, 3, radius))) {
            BlockEntity be = animal.level().getBlockEntity(pos);
            ItemStack stack = feederStack(be);
            if (!stack.isEmpty() && animal.isDietFood(stack)) {
                double d = origin.distSqr(pos);
                if (d < bestDist) { bestDist = d; best = pos.immutable(); }
            }
        }
        return best;
    }

    private static ItemStack feederStack(BlockEntity be) {
        if (be instanceof GroundFeederBlockEntity feeder) return feeder.getItem(0);
        if (be instanceof WallFeederBlockEntity feeder) return feeder.getItem(0);
        return ItemStack.EMPTY;
    }
}
