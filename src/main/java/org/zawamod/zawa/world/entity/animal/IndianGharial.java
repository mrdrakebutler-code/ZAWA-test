package org.zawamod.zawa.world.entity.animal;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

/** Stage 23 NeoForge reconstruction of the original ZAWA species. */
public class IndianGharial extends ZawaSemiAquaticEntity {
    public IndianGharial(EntityType<? extends ZawaSemiAquaticEntity> type, Level level) { super(type, level); }

    public static AttributeSupplier.Builder registerIndianGharialAttributes() {
        return createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 24)
                .add(Attributes.MOVEMENT_SPEED, 0.12)
                .add(Attributes.ATTACK_DAMAGE, 5);
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob other) {
        return ZawaEntities.INDIAN_GHARIAL.get().create(level);
    }
}
