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
    private int breachTicks;
    private boolean leftWater;

    public BreachGoal(PathfinderMob entity, int interval) {
        this.entity = entity;
        this.interval = Math.max(1, interval);
    }

    @Override
    public boolean canUse() {
        if (!entity.isInWater() || entity.getRandom().nextInt(interval) != 0) return false;
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
        breachTicks = 0;
        leftWater = false;
    }

    @Override
    public boolean canContinueToUse() {
        // The original pitch-based condition could stay true indefinitely after
        // a landing, leaving the orca locked in its jump pose underwater.
        if (breachTicks >= 45 || entity.onGround()) return false;
        return !leftWater || !entity.isInWater();
    }

    @Override
    public boolean isInterruptable() { return false; }

    @Override
    public void stop() {
        entity.setXRot(0.0F);
        breached = false;
        leftWater = false;
        breachTicks = 0;
    }

    @Override
    public void tick() {
        breachTicks++;
        if (!entity.isInWater()) leftWater = true;
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
