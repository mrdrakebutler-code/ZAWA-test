package org.zawamod.zawa.world.entity.ambient;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.level.Level;

public abstract class ZawaAmbientLandEntity extends ZawaBaseAmbientEntity {
    protected ZawaAmbientLandEntity(EntityType<? extends ZawaBaseAmbientEntity> type, Level level) { super(type, level); }
    @Override protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        super.registerGoals();
    }
}
