package org.zawamod.zawa.world.entity.stats;

import net.minecraft.util.RandomSource;

public enum EntityFertilityCategory {
    LOW(1, 2), MEDIUM(1, 3), HIGH(2, 4);
    public static final EntityFertilityCategory[] VALUES = values();
    private final int min;
    private final int max;
    EntityFertilityCategory(int min, int max) { this.min = min; this.max = max; }
    public int getRandomInRange(RandomSource random) {
        return Math.min(random.nextInt(max) + min, 4);
    }
    public int getAverage(EntityFertilityCategory other, int a, int b) {
        int value = Math.max(other.min, (a + b) / 2);
        return Math.min(other.max, value);
    }
}
