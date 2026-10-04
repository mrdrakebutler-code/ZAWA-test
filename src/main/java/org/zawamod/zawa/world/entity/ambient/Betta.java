package org.zawamod.zawa.world.entity.ambient;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import org.zawamod.zawa.world.entity.GroupEntity;
import org.zawamod.zawa.world.entity.SpeciesVariantsEntity;

public class Betta extends ZawaAmbientFishEntity, SpeciesVariantsEntity {

    public Betta(EntityType<? extends ZawaAmbientFishEntity> type, Level level) { super(type, level); }

    public static AttributeSupplier.Builder registerBettaAttributes() {
        return createLivingAttributes().add(Attributes.MOVEMENT_SPEED, 0.10D).add(Attributes.MAX_HEALTH, 2D);
    }

    @Override public int getVariantByBiome(net.minecraft.world.level.LevelAccessor level) { return 0; }


}
