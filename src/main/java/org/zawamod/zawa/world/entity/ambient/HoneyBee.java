package org.zawamod.zawa.world.entity.ambient;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class HoneyBee extends ZawaAmbientFlyingEntity {
    public HoneyBee(EntityType<? extends ZawaBaseAmbientEntity> type, Level level) { super(type, level); }
    public static AttributeSupplier.Builder registerHoneyBeeAttributes() {
        return createLivingAttributes().add(Attributes.FLYING_SPEED, 1.20D).add(Attributes.MOVEMENT_SPEED, 0.30D).add(Attributes.MAX_HEALTH, 2D).add(Attributes.ATTACK_DAMAGE, 2D);
    }
}
