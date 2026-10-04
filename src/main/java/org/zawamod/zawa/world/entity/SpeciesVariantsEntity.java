package org.zawamod.zawa.world.entity;

/** Marker/API retained from the original ZAWA variant system. */
public interface SpeciesVariantsEntity {
    int getVariant();
    void setVariant(int variant);
    int getVariantByBiome(net.minecraft.world.level.LevelAccessor level);
}
