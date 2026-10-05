package org.zawamod.zawa.world.entity;

import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.LevelAccessor;

/** Shared API retained from the original ZAWA variant system. */
public interface SpeciesVariantsEntity {
    int getVariant();
    void setVariant(int variant);
    int getVariantByBiome(LevelAccessor level);

    /** Carries the selected variant through vanilla group spawning. */
    final class SpeciesVariantData implements SpawnGroupData {
        public final int variant;
        public final float babySpawnChance;

        public SpeciesVariantData(int variant, float babySpawnChance) {
            this.variant = variant;
            this.babySpawnChance = babySpawnChance;
        }
    }
}
