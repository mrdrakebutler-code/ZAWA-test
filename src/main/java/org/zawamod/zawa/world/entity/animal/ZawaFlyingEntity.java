package org.zawamod.zawa.world.entity.animal;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.FlyingMoveControl;
import net.minecraft.world.entity.FlyingAnimal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/** Shared flying-animal movement layer recovered from the original bytecode. */
public abstract class ZawaFlyingEntity extends ZawaBaseEntity implements FlyingAnimal {
    public float flap;
    public float flapSpeed;
    public float oFlapSpeed;
    public float oFlap;
    private float flapping;
    private float nextFlap;

    protected ZawaFlyingEntity(EntityType<? extends ZawaBaseEntity> type, Level level) {
        super(type, level);
        this.flapping = 1.0F;
        this.nextFlap = 1.0F;
        this.moveControl = new FlyingMoveControl(this, 10, false);
        this.setPathfindingMalus(net.minecraft.world.level.pathfinder.BlockPathTypes.WATER, -1.0F);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(8, new ZawaFlyingGoal(this, 1.0D, 0.6D, 0.5F));
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        FlyingPathNavigation navigation = new FlyingPathNavigation(this, level);
        navigation.setCanOpenDoors(false);
        navigation.setCanFloat(true);
        navigation.setCanPassDoors(true);
        return navigation;
    }

    @Override
    public boolean isFlying() {
        return !this.onGround() && !this.isInWater() && !this.isBaby() && !this.isPassenger();
    }

    @Override
    public void aiStep() {
        super.aiStep();
        calculateFlapping();
    }

    private void calculateFlapping() {
        this.oFlap = this.flap;
        this.oFlapSpeed = this.flapSpeed;
        this.flapSpeed += ((this.onGround() || this.isPassenger()) ? -1 : 4) * 0.3F;
        this.flapSpeed = Mth.clamp(this.flapSpeed, 0.0F, 1.0F);
        if (!this.onGround() && this.flapping < 1.0F) this.flapping = 1.0F;
        this.flapping *= 0.9F;
        Vec3 motion = this.getDeltaMovement();
        if (!this.onGround() && motion.y < 0.0D) {
            this.setDeltaMovement(motion.x, motion.y * 0.6D, motion.z);
        }
        this.flap += this.flapping * 2.0F;
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, net.minecraft.world.damagesource.DamageSource source) {
        return false;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, net.minecraft.world.level.block.state.BlockState state, BlockPos pos) {
        // Flying animals do not accumulate conventional fall damage.
    }

    @Override
    protected boolean onClimbable() {
        return this.fallDistance > this.nextFlap;
    }

    @Override
    protected void playStepSound(BlockPos pos, net.minecraft.world.level.block.state.BlockState state) {
        this.playSound(SoundEvents.CHICKEN_STEP, 0.15F, 1.0F);
        this.nextFlap = this.fallDistance + this.flapSpeed / 2.0F;
    }

    /** Original ZAWA flying stroll goal, including its tree-perching search. */
    public static class ZawaFlyingGoal extends WaterAvoidingRandomStrollGoal {
        protected final double flyingSpeedMod;

        public ZawaFlyingGoal(PathfinderMob mob, double speed, double flyingSpeedMod, float probability) {
            super(mob, speed, probability);
            this.flyingSpeedMod = flyingSpeedMod;
        }

        @Override
        public boolean canUse() {
            return !this.mob.isBaby() && super.canUse();
        }

        @Override
        protected Vec3 getPosition() {
            Vec3 result = null;
            if (this.mob.isInWater()) {
                result = net.minecraft.world.entity.ai.util.LandRandomPos.getPos(this.mob, 15, 15);
            }
            if (this.mob.getRandom().nextFloat() >= this.probability) {
                result = getTreePos();
            }
            return result != null ? result : super.getPosition();
        }

        private Vec3 getTreePos() {
            BlockPos origin = this.mob.blockPosition();
            BlockPos.MutableBlockPos below = new BlockPos.MutableBlockPos();
            BlockPos.MutableBlockPos above = new BlockPos.MutableBlockPos();
            int minX = Mth.floor(this.mob.getX() - 3.0D);
            int minY = Mth.floor(this.mob.getY() - 6.0D);
            int minZ = Mth.floor(this.mob.getZ() - 3.0D);
            int maxX = Mth.floor(this.mob.getX() + 3.0D);
            int maxY = Mth.floor(this.mob.getY() + 6.0D);
            int maxZ = Mth.floor(this.mob.getZ() + 3.0D);
            for (BlockPos pos : BlockPos.betweenClosed(minX, minY, minZ, maxX, maxY, maxZ)) {
                if (origin.equals(pos)) continue;
                var belowState = this.mob.level().getBlockState(below.setWithOffset(pos, net.minecraft.core.Direction.DOWN));
                boolean leaves = belowState.getBlock() instanceof net.minecraft.world.level.block.LeavesBlock
                        || belowState.is(BlockTags.LEAVES);
                if (leaves && this.mob.level().noCollision(pos) && this.mob.level().noCollision(above.setWithOffset(pos, net.minecraft.core.Direction.UP))) {
                    return Vec3.atBottomCenterOf(pos);
                }
            }
            return null;
        }
    }
}
