package org.zawamod.zawa.world.entity.ambient;

import java.util.List;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import org.zawamod.zawa.config.ZawaSpawnCategory;
import org.zawamod.zawa.world.entity.ClimbingEntity;
import org.zawamod.zawa.world.entity.SpeciesVariantsEntity;

public class PrayingMantis extends ZawaAmbientLandEntity implements SpeciesVariantsEntity, ClimbingEntity {
    public static final EntityDataAccessor<Boolean> CLIMBING = SynchedEntityData.defineId(PrayingMantis.class, EntityDataSerializers.BOOLEAN);
    public static final List<Tuple<String, ZawaSpawnCategory>> VARIANT_SPAWNS = List.of(
        new Tuple<>("chinese", ZawaSpawnCategory.TEMPERATE_FOREST),
        new Tuple<>("orchid", ZawaSpawnCategory.DEEP_RAINFOREST),
        new Tuple<>("european", ZawaSpawnCategory.TEMPERATE_FOREST),
        new Tuple<>("african", ZawaSpawnCategory.DRY_GRASSLAND),
        new Tuple<>("devils_flower", ZawaSpawnCategory.DRY_GRASSLAND),
        new Tuple<>("ghost", ZawaSpawnCategory.DRY_RAINFOREST));
    public PrayingMantis(EntityType<? extends ZawaBaseAmbientEntity> type, Level level) { super(type, level); }
    public static AttributeSupplier.Builder registerPrayingMantisAttributes() { return createLivingAttributes().add(Attributes.MOVEMENT_SPEED, 0.10D).add(Attributes.MAX_HEALTH, 2D).add(Attributes.ATTACK_DAMAGE, 1D); }
    @Override protected void defineSynchedData(SynchedEntityData.Builder builder) { super.defineSynchedData(builder); builder.define(CLIMBING, false); }
    @Override public boolean isClimbing() { return entityData.get(CLIMBING); }
    @Override public void setClimbing(boolean climbing) { entityData.set(CLIMBING, climbing); }
    @Override public int getVariantByBiome(net.minecraft.world.level.LevelAccessor level) { return 0; }
}
