package org.zawamod.zawa.world.entity.stats;

import net.minecraft.util.StringRepresentable;
import java.util.Locale;

public enum EntitySizeCategory implements StringRepresentable {
    AMBIENT, TINY, SMALL, MEDIUM, LARGE, GIANT;
    public static final EntitySizeCategory[] VALUES = values();
    @Override public String getSerializedName() { return name().toLowerCase(Locale.ROOT); }
}
