package org.zawamod.zawa.world.entity.ambient;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;

/** Shared swimming implementation for ZAWA's ambient fish. */
public abstract class ZawaAmbientFishEntity extends WaterAnimal {
    protected ZawaAmbientFishEntity(EntityType<? extends ZawaAmbientFishEntity> type, Level level) {
        super(type, level);
        this.moveControl = new ZawaFishMoveControl(this);
    }

    @Override protected void registerGoals() {
        goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0D, 40));
        goalSelector.addGoal(7, new net.minecraft.world.entity.ai.goal.RandomLookAroundGoal(this));
    }

    @Override protected net.minecraft.world.entity.ai.navigation.PathNavigation createNavigation(Level level) {
        return new WaterBoundPathNavigation(this, level);
    }

    protected boolean canRandomSwim() { return true; }

    /** Replaced by the species' custom bucket item when the item registry is migrated. */
    public ItemStack getBucketItemStack() { return new ItemStack(Items.WATER_BUCKET); }

    public void saveToBucketTag(CompoundTag tag) { saveWithoutId(tag); }

    public void loadFromBucketTag(CompoundTag tag) { readAdditionalSaveData(tag); }

    public static boolean checkAquaticSpawnRules(EntityType<? extends ZawaBaseAmbientEntity> type,
                                                  ServerLevelAccessor level, MobSpawnType reason,
                                                  BlockPos pos, RandomSource random) {
        return ZawaBaseAmbientEntity.checkAquaticSpawnRules(type, level, reason, pos, random);
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
