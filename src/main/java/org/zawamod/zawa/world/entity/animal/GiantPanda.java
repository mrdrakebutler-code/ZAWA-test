package org.zawamod.zawa.world.entity.animal;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

/** Stage 23 NeoForge reconstruction of the original ZAWA species. */
public class GiantPanda extends ZawaLandEntity {
    public GiantPanda(EntityType<? extends ZawaLandEntity> type, Level level) { super(type, level); }

    public static AttributeSupplier.Builder registerGiantPandaAttributes() {
        return createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 24)
                .add(Attributes.MOVEMENT_SPEED, 0.15)
                .add(Attributes.ATTACK_DAMAGE, 4);
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob other) {
        return ZawaEntities.GIANT_PANDA.get().create(level);
    }
}
