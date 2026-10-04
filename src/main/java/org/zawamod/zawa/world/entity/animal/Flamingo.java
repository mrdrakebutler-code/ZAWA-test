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
public class Flamingo extends ZawaFlyingEntity {
    public Flamingo(EntityType<? extends ZawaFlyingEntity> type, Level level) { super(type, level); }

    public static AttributeSupplier.Builder registerFlamingoAttributes() {
        return createLivingAttributes()
                .add(Attributes.FLYING_SPEED, 0.6)
                .add(Attributes.MOVEMENT_SPEED, 0.225)
                .add(Attributes.MAX_HEALTH, 14)
                .add(Attributes.ATTACK_DAMAGE, 1)
                ;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }

    public static boolean checkFlamingoSpawnRules(EntityType<? extends Flamingo> type, ServerLevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random) { return checkFlyingSpawnRules(type, level, reason, pos, random); }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob other) {
        return ZawaEntities.FLAMINGO.get().create(level);
    }
}
