package org.zawamod.zawa.world.entity.stats;

import net.minecraft.util.StringRepresentable;
import java.util.Locale;

public enum EntityTemperamentCategory implements StringRepresentable {
    TIMID, NEUTRAL, DEFENSIVE, TERRITORIAL, AGGRESSIVE;
    public static final EntityTemperamentCategory[] VALUES = values();
    @Override public String getSerializedName() { return name().toLowerCase(Locale.ROOT); }
}
