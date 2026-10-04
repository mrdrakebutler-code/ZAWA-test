package org.zawamod.zawa.world.entity.ambient;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import org.zawamod.zawa.world.entity.GroupEntity;
import org.zawamod.zawa.world.entity.SpeciesVariantsEntity;

public class AfricanLakeCichlid extends ZawaAmbientFishEntity implements SpeciesVariantsEntity, GroupEntity<AfricanLakeCichlid> {
    private AfricanLakeCichlid groupLeader;
    private int groupSize;
    public AfricanLakeCichlid(EntityType<? extends ZawaAmbientFishEntity> type, Level level) { super(type, level); }
    public static AttributeSupplier.Builder registerAfricanLakeCichlidAttributes() { return createLivingAttributes().add(Attributes.MOVEMENT_SPEED, 0.20D).add(Attributes.MAX_HEALTH, 4D); }
    @Override public int getVariantByBiome(net.minecraft.world.level.LevelAccessor level) { return 0; }
    @Override public AfricanLakeCichlid getGroupLeader() { return groupLeader; }
    @Override public void setGroupLeader(AfricanLakeCichlid leader) { this.groupLeader = leader; }
    @Override public int getMaxGroupSize() { return 4; }
    @Override public int getGroupSize() { return groupSize; }
    @Override public void setGroupSize(int size) { this.groupSize = size; }
}
