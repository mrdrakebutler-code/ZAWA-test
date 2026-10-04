package org.zawamod.zawa.world.entity.ambient;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import org.zawamod.zawa.world.entity.GroupEntity;
import org.zawamod.zawa.world.entity.SpeciesVariantsEntity;

public class Angelfish extends ZawaAmbientFishEntity, SpeciesVariantsEntity, GroupEntity<Angelfish> {

    private Angelfish groupLeader;
    private int groupSize;

    public Angelfish(EntityType<? extends ZawaAmbientFishEntity> type, Level level) { super(type, level); }

    public static AttributeSupplier.Builder registerAngelfishAttributes() {
        return createLivingAttributes().add(Attributes.MOVEMENT_SPEED, 0.30D).add(Attributes.MAX_HEALTH, 4D);
    }

    @Override public int getVariantByBiome(net.minecraft.world.level.LevelAccessor level) { return 0; }


    @Override public Angelfish getGroupLeader() { return groupLeader; }
    @Override public void setGroupLeader(Angelfish leader) { this.groupLeader = leader; }
    @Override public int getMaxGroupSize() { return 4; }
    @Override public int getGroupSize() { return groupSize; }
    @Override public void setGroupSize(int size) { this.groupSize = size; }

}
