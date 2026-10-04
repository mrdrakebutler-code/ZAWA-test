package org.zawamod.zawa.world.entity.animal;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.TryFindWaterGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

/** Shared fully-aquatic movement and breathing layer recovered from bytecode. */
public abstract class ZawaAquaticEntity extends ZawaBaseEntity {
    protected ZawaAquaticEntity(EntityType<? extends ZawaAquaticEntity> type, Level level) {
        super(type, level);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.2F, 0.1F, true);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0D, 10));
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return !isTame() && !hasCustomName() && tickCount > 2400;
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new WaterBoundPathNavigation(this, level);
    }

    @Override
    public void tick() {
        super.tick();
        if (!isInWaterOrBubble() && !onGround() && !isNoGravity()) {
            Vec3 motion = getDeltaMovement();
            setDeltaMovement(motion.add(
                    (getRandom().nextFloat() * 2.0F - 1.0F) * 0.2D,
                    0.5D,
                    (getRandom().nextFloat() * 2.0F - 1.0F) * 0.2D));
            setYRot(getRandom().nextFloat() * 360.0F);
            setNoGravity(false);
            hasImpulse = true;
        }
    }

    @Override
    public void baseTick() {
        int air = getAirSupply();
        super.baseTick();
        handleAirSupply(air);
    }

    protected void handleAirSupply(int currentAir) {
        if (isAlive() && !isInWaterOrBubble()) {
            setAirSupply(currentAir - 1);
            if (getAirSupply() == -20) {
                setAirSupply(0);
                hurt(damageSources().drown(), 2.0F);
            }
        } else {
            setAirSupply(300);
        }
    }

    @Override
    public void travel(Vec3 travelVector) {
        if (isEffectiveAi() && isInWater()) {
            this.moveRelative(getSpeed(), travelVector);
            this.move(net.minecraft.world.entity.MoverType.SELF, getDeltaMovement());
            this.setDeltaMovement(getDeltaMovement().scale(0.9D));
            if (getTarget() == null) this.setDeltaMovement(getDeltaMovement().add(0.0D, -0.005D, 0.0D));
        } else {
            super.travel(travelVector);
        }
    }

    @Override
    public boolean isPushedByFluid() { return true; }

    @Override
    public MobType getMobType() { return MobType.WATER; }

    @Override
    public boolean checkSpawnObstruction(LevelReader level) { return level.noCollision(this); }

    @Override
    public boolean shouldDropExperience() { return false; }

    @Override
    public boolean isInvulnerableTo(net.minecraft.world.damagesource.DamageSource source) {
        return super.isInvulnerableTo(source);
    }
}
