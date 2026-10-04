package org.zawamod.zawa.world.entity.animal;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.util.RandomSource;

/** Recovered ZAWA species implementation for the NeoForge 1.21.1 port. */
public class AsianElephant extends ZawaLandEntity {
    public AsianElephant(EntityType<? extends ZawaLandEntity> type, Level level) { super(type, level); }

    public static AttributeSupplier.Builder registerAsianElephantAttributes() {
        return createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 100)
                .add(Attributes.MOVEMENT_SPEED, 0.225)
                .add(Attributes.ATTACK_DAMAGE, 14)
                ;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }

    @Override public float getMaleRatio() { return 0.17F; }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob other) {
        return ZawaEntities.ASIAN_ELEPHANT.get().create(level);
    }
}
