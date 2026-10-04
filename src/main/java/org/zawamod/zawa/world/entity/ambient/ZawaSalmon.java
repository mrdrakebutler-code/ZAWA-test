package org.zawamod.zawa.world.entity.ambient;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import org.zawamod.zawa.world.entity.GroupEntity;
import org.zawamod.zawa.world.entity.SpeciesVariantsEntity;

/** Original 1.20.1 EntityType dimensions: 0.5F x 0.7F */
public class ZawaSalmon extends ZawaAmbientFishEntity, SpeciesVariantsEntity, GroupEntity<ZawaSalmon> {

    private ZawaSalmon groupLeader;
    private int groupSize;

    public ZawaSalmon(EntityType<? extends ZawaAmbientFishEntity> type, Level level) { super(type, level); }

    public static AttributeSupplier.Builder registerSalmonAttributes() {
        return createLivingAttributes().add(Attributes.MOVEMENT_SPEED, 0.30D).add(Attributes.MAX_HEALTH, 8D);
    }

    @Override public int getVariantByBiome(net.minecraft.world.level.LevelAccessor level) { return 0; }


    @Override public ZawaSalmon getGroupLeader() { return groupLeader; }
    @Override public void setGroupLeader(ZawaSalmon leader) { this.groupLeader = leader; }
    @Override public int getMaxGroupSize() { return 5; }
    @Override public int getGroupSize() { return groupSize; }
    @Override public void setGroupSize(int size) { this.groupSize = size; }

}
