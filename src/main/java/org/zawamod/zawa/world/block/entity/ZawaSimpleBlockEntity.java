package org.zawamod.zawa.world.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/** Lightweight persistent state base used by the first NeoForge block-entity port. */
public abstract class ZawaSimpleBlockEntity extends BlockEntity {
    protected int progress;
    protected int totalProgress = 200;
    protected boolean active;

    protected ZawaSimpleBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public void serverTick() {
        if (level == null || level.isClientSide) return;
        if (active) {
            if (++progress >= totalProgress) {
                progress = 0;
                active = false;
            }
            setChanged();
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, net.minecraft.core.HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        progress = tag.getInt("Progress");
        totalProgress = Math.max(1, tag.getInt("TotalProgress"));
        active = tag.getBoolean("Active");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, net.minecraft.core.HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("Progress", progress);
        tag.putInt("TotalProgress", totalProgress);
        tag.putBoolean("Active", active);
    }
}
