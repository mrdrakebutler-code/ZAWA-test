package org.zawamod.zawa.world.entity.animal;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.level.Level;

/** Shared ground-animal navigation/goal layer recovered from the 1.20.1 build. */
public abstract class ZawaLandEntity extends ZawaBaseEntity {
    protected FloatGoal floatGoal;
    protected WaterAvoidingRandomStrollGoal waterAvoidingRandomStrollGoal;

    protected ZawaLandEntity(EntityType<? extends ZawaBaseEntity> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.floatGoal = new FloatGoal(this);
        this.goalSelector.addGoal(0, this.floatGoal);
        this.waterAvoidingRandomStrollGoal = new WaterAvoidingRandomStrollGoal(this, 1.0D);
        this.goalSelector.addGoal(8, this.waterAvoidingRandomStrollGoal);
    }
}
