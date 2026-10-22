package org.zawamod.zawa.world.entity.stats;

import net.minecraft.util.StringRepresentable;
import java.util.Locale;

public enum EntitySpeedCategory implements StringRepresentable {
    SLOW, MEDIUM, FAST;
    public static final EntitySpeedCategory[] VALUES = values();
    @Override public String getSerializedName() { return name().toLowerCase(Locale.ROOT); }
}
