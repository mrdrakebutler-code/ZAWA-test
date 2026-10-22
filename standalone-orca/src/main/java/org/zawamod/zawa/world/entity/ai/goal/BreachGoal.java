package org.zawamod.zawa.world.entity.ai.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.JumpGoal;

/** Original ZAWA orca jump path clearance and momentum, adapted for 1.21.1. */
public class BreachGoal extends JumpGoal {
    private static final int[] STEPS_TO_CHECK = {0, 1, 4, 5, 6, 7};
    private final PathfinderMob entity;
    private final int interval;
    private boolean breached;

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
        breached = false;
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

    @Override
    public void tick() {
        boolean previouslyBreached = breached;
        if (!breached) {
            breached = entity.level().getFluidState(entity.blockPosition()).is(FluidTags.WATER);
        }
        if (breached && !previouslyBreached) {
            entity.playSound(SoundEvents.DOLPHIN_JUMP, 1.0F, 1.0F);
        }

        Vec3 velocity = entity.getDeltaMovement();
        if (velocity.y * velocity.y < 0.029999999329447746D && entity.getXRot() != 0.0F) {
            entity.setXRot(Mth.clamp(entity.getXRot(), 0.0F, 0.2F));
        } else {
            double speed = velocity.length();
            if (speed > 1.0E-7D) {
                double cosine = Mth.clamp(velocity.y / speed, -1.0D, 1.0D);
                double pitch = Math.signum(-velocity.y) * Math.acos(cosine) * (180.0D / Math.PI);
                entity.setXRot((float) pitch);
            }
        }
    }
}
