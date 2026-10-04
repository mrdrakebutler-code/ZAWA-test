package org.zawamod.zawa.world.entity.animal;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

/** Stage 23 NeoForge reconstruction of the original ZAWA species. */
public class Macaw extends ZawaFlyingEntity {
    public Macaw(EntityType<? extends ZawaFlyingEntity> type, Level level) { super(type, level); }

    public static AttributeSupplier.Builder registerMacawAttributes() {
        return createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 10)
                .add(Attributes.MOVEMENT_SPEED, 0.2)
                .add(Attributes.ATTACK_DAMAGE, 2);
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob other) {
        return ZawaEntities.MACAW.get().create(level);
    }
}
