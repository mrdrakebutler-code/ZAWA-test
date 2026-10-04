package org.zawamod.zawa.world.entity.ambient;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import org.zawamod.zawa.world.entity.GroupEntity;
import org.zawamod.zawa.world.entity.SpeciesVariantsEntity;

/** Original 1.20.1 EntityType dimensions: 3.5F x 2.0F */
public class Plecostomus extends ZawaAmbientFishEntity, SpeciesVariantsEntity {

    public Plecostomus(EntityType<? extends ZawaAmbientFishEntity> type, Level level) { super(type, level); }

    public static AttributeSupplier.Builder registerPlecostomusAttributes() {
        return createLivingAttributes().add(Attributes.MOVEMENT_SPEED, 0.10D).add(Attributes.MAX_HEALTH, 8D);
    }

    @Override public int getVariantByBiome(net.minecraft.world.level.LevelAccessor level) { return 0; }


}
