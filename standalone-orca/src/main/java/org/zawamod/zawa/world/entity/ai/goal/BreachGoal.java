package org.zawamod.zawa.world.entity.ai.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.JumpGoal;

/** Original ZAWA orca jump path clearance and momentum, adapted for 1.21.1. */
public class BreachGoal extends JumpGoal {
    private static final int[] STEPS_TO_CHECK = {0, 1, 4, 5, 6, 7};
    private final PathfinderMob entity;
    private final int interval;

    public BreachGoal(PathfinderMob entity, int interval) {
        this.entity = entity;
        this.interval = Math.max(1, interval);
    }

    @Override
    public boolean canUse() {
        if (entity.getRandom().nextInt(interval) != 0) return false;
        Direction direction = entity.getMotionDirection();
        int dx = direction.getStepX(), dz = direction.getStepZ();
        BlockPos origin = entity.blockPosition();
        for (int distance : STEPS_TO_CHECK) {
            BlockPos pos = origin.offset(dx * distance, 0, dz * distance);
            if (!entity.level().getFluidState(pos).is(FluidTags.WATER)
                    || entity.level().getBlockState(pos).blocksMotion()
                    || !entity.level().getBlockState(pos.above()).isAir()
                    || !entity.level().getBlockState(pos.above(2)).isAir()) return false;
        }
        return true;
    }

    @Override
    public void start() {
        Direction direction = entity.getMotionDirection();
        entity.setDeltaMovement(entity.getDeltaMovement().add(
                direction.getStepX() * 0.6D, 0.7D, direction.getStepZ() * 0.6D));
        entity.getNavigation().stop();
    }

    @Override
    public boolean canContinueToUse() {
        double dy = entity.getDeltaMovement().y;
        return (dy * dy >= 0.03D || entity.getXRot() == 0.0F
                || Math.abs(entity.getXRot()) >= 10.0F || entity.isInWater())
                && !entity.onGround();
    }

    @Override
    public boolean isInterruptable() { return false; }

    @Override
    public void stop() { entity.setXRot(0.0F); }
}
