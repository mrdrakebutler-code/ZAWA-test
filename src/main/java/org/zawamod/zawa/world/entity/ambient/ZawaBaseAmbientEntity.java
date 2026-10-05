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
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;
import org.zawamod.zawa.world.entity.SpeciesVariantsEntity;

/** Common foundation for ZAWA's lightweight ambient animals. */
public abstract class ZawaBaseAmbientEntity extends Animal implements SpeciesVariantsEntity {
    public static final EntityDataAccessor<Integer> VARIANT =
            SynchedEntityData.defineId(ZawaBaseAmbientEntity.class, EntityDataSerializers.INT);

    protected ZawaBaseAmbientEntity(EntityType<? extends ZawaBaseAmbientEntity> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        goalSelector.addGoal(9, new RandomStrollGoal(this, 1.0D));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT, 0);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, net.minecraft.world.DifficultyInstance difficulty,
                                         MobSpawnType reason, @Nullable SpawnGroupData data) {
        int variant = getVariantByBiome(level);
        if (data instanceof SpeciesVariantData variantData) variant = variantData.variant;
        else data = new SpeciesVariantData(variant, 0.2F);
        setVariant(variant);
        return super.finalizeSpawn(level, difficulty, reason, data);
    }

    @Override public int getVariant() { return entityData.get(VARIANT); }
    @Override public void setVariant(int variant) { entityData.set(VARIANT, Math.max(0, variant)); }

    @Override
    public int getVariantByBiome(LevelAccessor level) {
        int variants = Math.max(1, getWildVariants());
        return getRandom().nextInt(variants);
    }

    public int getTotalVariants() { return getWildVariants() + getCaptiveVariants(); }
    public int getWildVariants() { return 1; }
    public int getCaptiveVariants() { return 0; }
    public int getSpeciesSize() { return 0; }

    @Override
    public boolean isFood(ItemStack stack) {
        return false;
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

    public static boolean checkLandSpawnRules(EntityType<? extends ZawaBaseAmbientEntity> type,
                                               ServerLevelAccessor level, MobSpawnType reason,
                                               BlockPos pos, RandomSource random) {
        return Animal.checkAnimalSpawnRules(type, level, reason, pos, random);
    }

    public static boolean checkLandSpawnRulesWithLeaves(EntityType<? extends ZawaBaseAmbientEntity> type,
                                                         ServerLevelAccessor level, MobSpawnType reason,
                                                         BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).isFaceSturdy(level, pos.below(), net.minecraft.core.Direction.UP)
                && level.getBlockState(pos).isAir();
    }

    public static boolean checkAquaticSpawnRules(EntityType<? extends ZawaBaseAmbientEntity> type,
                                                  ServerLevelAccessor level, MobSpawnType reason,
                                                  BlockPos pos, RandomSource random) {
        return level.getFluidState(pos).is(net.minecraft.tags.FluidTags.WATER)
                && level.getBlockState(pos.above()).isAir();
    }

    public static boolean checkFlyingSpawnRules(EntityType<? extends ZawaBaseAmbientEntity> type,
                                                 ServerLevelAccessor level, MobSpawnType reason,
                                                 BlockPos pos, RandomSource random) {
        return level.getBlockState(pos).isAir();
    }
}
