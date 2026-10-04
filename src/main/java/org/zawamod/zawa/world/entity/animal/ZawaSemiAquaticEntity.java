package org.zawamod.zawa.world.entity.animal;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.BreathAirGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.phys.Vec3;

/** Shared amphibious/semi-aquatic movement layer recovered from bytecode. */
public abstract class ZawaSemiAquaticEntity extends ZawaBaseEntity {
    protected final WaterBoundPathNavigation waterNavigation;
    protected final LookControl waterLookControl;
    protected final MoveControl waterMoveControl;
    protected final GroundPathNavigation groundNavigation;
    protected final LookControl groundLookControl;
    protected final MoveControl groundMoveControl;
    private final RandomSwimmingGoal randomSwimmingGoal;
    private final FloatGoal babySwimGoal;

    protected ZawaSemiAquaticEntity(EntityType<? extends ZawaSemiAquaticEntity> type, Level level) {
        super(type, level);
        this.randomSwimmingGoal = new RandomSwimmingGoal(this, 1.0D, 10);
        this.babySwimGoal = new FloatGoal(this);
        this.waterNavigation = new WaterBoundPathNavigation(this, level);
        this.waterLookControl = new SmoothSwimmingLookControl(this, 10);
        this.waterMoveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.2F, 0.1F, true);
        this.groundNavigation = new GroundPathNavigation(this, level);
        this.groundLookControl = new LookControl(this);
        this.groundMoveControl = new MoveControl(this);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new BreathAirGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
    }

    public void reassessSwimGoal(boolean swimming) {
        if (level() == null || level().isClientSide) return;
        if (isBaby() && !canBabySwim()) {
            if (swimming) goalSelector.addGoal(1, babySwimGoal);
            else goalSelector.removeGoal(babySwimGoal);
        } else if (swimming) {
            goalSelector.addGoal(8, randomSwimmingGoal);
        } else {
            goalSelector.removeGoal(randomSwimmingGoal);
        }
    }

    public abstract float swimSpeedMultiplier();
    public abstract boolean canBabySwim();

    @Override
    public void travel(Vec3 travelVector) {
        if (isEffectiveAi() && isInWater()) {
            this.moveRelative(getSpeed() * swimSpeedMultiplier(), travelVector);
            this.move(net.minecraft.world.entity.MoverType.SELF, getDeltaMovement());
            this.setDeltaMovement(getDeltaMovement().scale(0.9D));
            if (getTarget() == null) this.setDeltaMovement(getDeltaMovement().add(0.0D, -0.005D, 0.0D));
        } else {
            super.travel(travelVector);
        }
    }

    @Override
    public boolean isPushedByFluid() { return true; }

    public int getWaterBreathingTime() { return 4800; }
    protected int decreaseAirSupply(int currentAir) { return getWaterBreathingTime(); }

    @Override
    public MobType getMobType() { return MobType.WATER; }

    @Override
    public boolean checkSpawnObstruction(LevelReader level) { return level.noCollision(this); }

    @Override
    public void tick() {
        super.tick();
        if (isInWaterOrBubble()) setAirSupply(getWaterBreathingTime());
    }
}
