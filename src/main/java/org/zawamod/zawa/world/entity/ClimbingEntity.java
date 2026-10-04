package org.zawamod.zawa.world.entity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
public interface ClimbingEntity {
    boolean isClimbing();
    void setClimbing(boolean climbing);
    default boolean isClimbableBlock(Level level, BlockPos pos) { return level.getBlockState(pos).isSuffocating(level, pos); }
}
