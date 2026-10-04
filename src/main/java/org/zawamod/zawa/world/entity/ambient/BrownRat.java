package org.zawamod.zawa.world.entity.ambient;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class BrownRat extends ZawaAmbientLandEntity {
    public BrownRat(EntityType<? extends ZawaBaseAmbientEntity> type, Level level) { super(type, level); }
    public static AttributeSupplier.Builder registerBrownRatAttributes() {
        return createLivingAttributes().add(Attributes.MOVEMENT_SPEED, 0.30D).add(Attributes.MAX_HEALTH, 6D).add(Attributes.ATTACK_DAMAGE, 1D);
    }
}
