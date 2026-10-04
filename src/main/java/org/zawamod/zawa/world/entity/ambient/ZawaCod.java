package org.zawamod.zawa.world.entity.ambient;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import org.zawamod.zawa.world.entity.GroupEntity;
import org.zawamod.zawa.world.entity.SpeciesVariantsEntity;

/** Original 1.20.1 EntityType dimensions: 1.0F x 0.5F */
public class ZawaCod extends ZawaAmbientFishEntity implements SpeciesVariantsEntity, GroupEntity<ZawaCod> {
    private ZawaCod groupLeader;
    private int groupSize;
    public ZawaCod(EntityType<? extends ZawaAmbientFishEntity> type, Level level) { super(type, level); }
    public static AttributeSupplier.Builder registerCodAttributes() { return createLivingAttributes().add(Attributes.MOVEMENT_SPEED, 0.30D).add(Attributes.MAX_HEALTH, 8D); }
    @Override public int getVariantByBiome(net.minecraft.world.level.LevelAccessor level) { return 0; }
    @Override public ZawaCod getGroupLeader() { return groupLeader; }
    @Override public void setGroupLeader(ZawaCod leader) { this.groupLeader = leader; }
    @Override public int getMaxGroupSize() { return 6; }
    @Override public int getGroupSize() { return groupSize; }
    @Override public void setGroupSize(int size) { this.groupSize = size; }
}
