package org.zawamod.zawa.world.entity.animal;

import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

/** Recovered Stage 15 species shell; core species attributes match the 1.20.1 bytecode. */
public class SumatranOrangutan extends ZawaLandEntity {
    public SumatranOrangutan(EntityType<? extends ZawaLandEntity> type, Level level) { super(type, level); }
    public static AttributeSupplier.Builder registerSumatranOrangutanAttributes() {
        return createLivingAttributes().add(Attributes.MOVEMENT_SPEED, 0.22499999403953552D).add(Attributes.MAX_HEALTH, 26.0D).add(Attributes.ATTACK_DAMAGE, 6.0D);
    }
    @Override public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob other) { return ZawaEntities.SUMATRAN_ORANGUTAN.get().create(level); }
}
