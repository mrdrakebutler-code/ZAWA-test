package org.zawamod.zawa.world.entity.ambient;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.zawamod.zawa.world.entity.SpeciesVariantsEntity;

/** Shared swimming implementation for ZAWA's ambient fish. */
public abstract class ZawaAmbientFishEntity extends WaterAnimal implements SpeciesVariantsEntity {
    private static final EntityDataAccessor<Integer> VARIANT =
            SynchedEntityData.defineId(ZawaAmbientFishEntity.class, EntityDataSerializers.INT);

    protected ZawaAmbientFishEntity(EntityType<? extends ZawaAmbientFishEntity> type, Level level) {
        super(type, level);
        this.moveControl = new ZawaFishMoveControl(this);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT, 0);
    }

    @Override protected void registerGoals() {
        goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0D, 40));
        goalSelector.addGoal(7, new net.minecraft.world.entity.ai.goal.RandomLookAroundGoal(this));
    }

    @Override protected net.minecraft.world.entity.ai.navigation.PathNavigation createNavigation(Level level) {
        return new WaterBoundPathNavigation(this, level);
    }

    @Override public int getVariant() { return entityData.get(VARIANT); }
    @Override public void setVariant(int variant) { entityData.set(VARIANT, Math.max(0, variant)); }

    @Override
    public int getVariantByBiome(LevelAccessor level) {
        return getRandom().nextInt(Math.max(1, getWildVariants()));
    }

    public int getTotalVariants() { return getWildVariants() + getCaptiveVariants(); }
    public int getWildVariants() { return 1; }
    public int getCaptiveVariants() { return 0; }
    public int getSpeciesSize() { return 0; }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, net.minecraft.world.DifficultyInstance difficulty,
                                        MobSpawnType reason, @Nullable SpawnGroupData data) {
        int variant = getVariantByBiome(level);
        if (data instanceof SpeciesVariantData variantData) variant = variantData.variant;
        else data = new SpeciesVariantData(variant, 0.2F);
        setVariant(variant);
        return super.finalizeSpawn(level, difficulty, reason, data);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Variant", getVariant());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Variant")) setVariant(tag.getInt("Variant"));
    }

    protected boolean canRandomSwim() { return true; }

    /** Replaced by the species' custom bucket item when the item registry is migrated. */
    public ItemStack getBucketItemStack() { return new ItemStack(Items.WATER_BUCKET); }

    public void saveToBucketTag(CompoundTag tag) { saveWithoutId(tag); }
    public void loadFromBucketTag(CompoundTag tag) { readAdditionalSaveData(tag); }

    public static boolean checkAquaticSpawnRules(EntityType<? extends ZawaAmbientFishEntity> type,
                                                  ServerLevelAccessor level, MobSpawnType reason,
                                                  BlockPos pos, RandomSource random) {
        return level.getFluidState(pos).is(net.minecraft.tags.FluidTags.WATER);
    }

    protected static class ZawaFishMoveControl extends MoveControl {
        private final ZawaAmbientFishEntity fish;
        protected ZawaFishMoveControl(ZawaAmbientFishEntity fish) { super(fish); this.fish = fish; }
        @Override public void tick() {
            if (fish.isInWater() && operation == Operation.MOVE_TO) {
                Vec3 delta = new Vec3(wantedX - fish.getX(), wantedY - fish.getY(), wantedZ - fish.getZ());
                if (delta.lengthSqr() > 1.0E-6) {
                    fish.setYRot((float)(Math.atan2(delta.z, delta.x) * (180F / Math.PI)) - 90F);
                    fish.setXRot((float)(-(Math.atan2(delta.y, Math.sqrt(delta.x * delta.x + delta.z * delta.z)) * (180F / Math.PI))));
                    fish.setYya((float)(delta.y * 0.1D));
                    fish.setSpeed((float)(speedModifier * fish.getAttributeValue(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED)));
                    operation = Operation.WAIT;
                }
            } else super.tick();
        }
    }
}
