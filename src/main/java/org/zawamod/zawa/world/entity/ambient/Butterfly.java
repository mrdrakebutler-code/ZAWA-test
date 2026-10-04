package org.zawamod.zawa.world.entity.ambient;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import org.zawamod.zawa.world.entity.SpeciesVariantsEntity;

public class Butterfly extends ZawaAmbientFlyingEntity implements SpeciesVariantsEntity {
    public Butterfly(EntityType<? extends ZawaBaseAmbientEntity> type, Level level) { super(type, level); }
    public static AttributeSupplier.Builder registerButterflyAttributes() {
        return createLivingAttributes().add(Attributes.FLYING_SPEED, 0.70D).add(Attributes.MOVEMENT_SPEED, 0.225D).add(Attributes.MAX_HEALTH, 2D);
    }
    @Override public int getVariantByBiome(net.minecraft.world.level.LevelAccessor level) { return 0; }
}
