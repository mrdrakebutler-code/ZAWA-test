package org.zawamod.zawa.world.entity.animal;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

/** Stage 23 NeoForge reconstruction of the original ZAWA species. */
public class RingTailedLemur extends ZawaLandEntity {
    public RingTailedLemur(EntityType<? extends ZawaLandEntity> type, Level level) { super(type, level); }

    public static AttributeSupplier.Builder registerRingTailedLemurAttributes() {
        return createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 14)
                .add(Attributes.MOVEMENT_SPEED, 0.22)
                .add(Attributes.ATTACK_DAMAGE, 3);
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob other) {
        return ZawaEntities.RING_TAILED_LEMUR.get().create(level);
    }
}
