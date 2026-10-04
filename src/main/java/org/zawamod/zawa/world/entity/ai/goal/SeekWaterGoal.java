package org.zawamod.zawa.world.entity.ai.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.ai.goal.Goal;
import org.zawamod.zawa.world.entity.animal.ZawaBaseEntity;
import org.zawamod.zawa.world.entity.animal.ZawaFeedingUtil;

import java.util.EnumSet;

/** Lets thirsty animals seek nearby accessible water and drink from it. */
public final class SeekWaterGoal extends Goal {
    private final ZawaBaseEntity animal;
    private final double speed;
    private final int radius;
    private BlockPos target;
    private int cooldown;

    public SeekWaterGoal(ZawaBaseEntity animal, double speed, int radius) {
        this.animal = animal; this.speed = speed; this.radius = radius;
        setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override public boolean canUse() {
        if (cooldown-- > 0 || animal.getThirst().getValue() >= animal.getThirst().getMax()) return false;
        cooldown = 60 + animal.getRandom().nextInt(60);
        target = findWater();
        return target != null;
    }

    @Override public boolean canContinueToUse() {
        return target != null && animal.getThirst().getValue() < animal.getThirst().getMax()
                && animal.blockPosition().distSqr(target) <= radius * radius * 2L;
    }

    @Override public void start() { move(); }
    @Override public void tick() {
        if (target == null) return;
        if (animal.blockPosition().distSqr(target) <= 4.0D || animal.isInWater()) {
            ZawaFeedingUtil.provideWater(animal, Math.max(2, animal.getThirst().getMax() / 3));
            stop();
        } else if (animal.getNavigation().isDone()) move();
    }

    private void move() { animal.getNavigation().moveTo(target.getX() + .5, target.getY(), target.getZ() + .5, speed); }
    @Override public void stop() { target = null; animal.getNavigation().stop(); }

    private BlockPos findWater() {
        BlockPos origin = animal.blockPosition();
        BlockPos best = null; double bestDist = Double.MAX_VALUE;
        for (BlockPos pos : BlockPos.betweenClosed(origin.offset(-radius, -3, -radius), origin.offset(radius, 2, radius))) {
            if (!animal.level().getFluidState(pos).is(FluidTags.WATER)) continue;
            BlockPos stand = pos.above();
            if (!animal.level().getBlockState(stand).getCollisionShape(animal.level(), stand).isEmpty()) continue;
            double d = origin.distSqr(pos);
            if (d < bestDist) { bestDist = d; best = pos.immutable(); }
        }
        return best;
    }
}
