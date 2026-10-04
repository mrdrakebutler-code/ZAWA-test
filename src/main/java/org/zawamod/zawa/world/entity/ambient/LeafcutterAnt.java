package org.zawamod.zawa.world.entity.ambient;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import org.zawamod.zawa.world.entity.ClimbingEntity;

public class LeafcutterAnt extends ZawaAmbientLandEntity implements ClimbingEntity {
    public static final EntityDataAccessor<Boolean> CLIMBING = SynchedEntityData.defineId(LeafcutterAnt.class, EntityDataSerializers.BOOLEAN);
    public LeafcutterAnt(EntityType<? extends ZawaBaseAmbientEntity> type, Level level) { super(type, level); }
    public static AttributeSupplier.Builder registerLeafcutterAntAttributes() {
        return createLivingAttributes().add(Attributes.MOVEMENT_SPEED, 0.225D).add(Attributes.MAX_HEALTH, 2D).add(Attributes.ATTACK_DAMAGE, 1D);
    }
    @Override protected void defineSynchedData(SynchedEntityData.Builder builder) { super.defineSynchedData(builder); builder.define(CLIMBING, false); }
    @Override public boolean isClimbing() { return entityData.get(CLIMBING); }
    @Override public void setClimbing(boolean climbing) { entityData.set(CLIMBING, climbing); }
}
