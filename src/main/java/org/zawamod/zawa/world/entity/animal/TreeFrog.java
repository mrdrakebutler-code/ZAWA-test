package org.zawamod.zawa.world.entity.animal;

import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

/** Recovered Stage 15 species shell; core species attributes match the 1.20.1 bytecode. */
public class TreeFrog extends ZawaLandEntity {
    public TreeFrog(EntityType<? extends ZawaBaseEntity> type, Level level) { super(type, level); }
    public static AttributeSupplier.Builder registerTreeFrogAttributes() {
        return createLivingAttributes().add(Attributes.MOVEMENT_SPEED, 0.22499999403953552D).add(Attributes.MAX_HEALTH, 4.0D).add(Attributes.ATTACK_DAMAGE, 1.0D);
    }
    @Override public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob other) { return ZawaEntities.TREE_FROG.get().create(level); }
}
