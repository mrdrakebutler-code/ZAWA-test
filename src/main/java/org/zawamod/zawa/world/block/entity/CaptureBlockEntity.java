package org.zawamod.zawa.world.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/** Persistent capture container scaffold. Full serialization/release behavior follows in the capture-system stage. */
public class CaptureBlockEntity extends BlockEntity {
    private CompoundTag captured;

    public CaptureBlockEntity(BlockPos pos, BlockState state) {
        super(ZawaBlockEntities.CAPTURE.get(), pos, state);
    }

    public boolean containsAnimal() { return captured != null; }

    public void addAnimal(LivingEntity entity) {
        if (entity == null) return;
        CompoundTag tag = new CompoundTag();
        entity.saveWithoutId(tag);
        captured = tag;
        setChanged();
    }

    public CompoundTag writeAnimal() { return captured == null ? new CompoundTag() : captured.copy(); }

    @Override
    protected void loadAdditional(CompoundTag tag, net.minecraft.core.HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        captured = tag.contains("Captured") ? tag.getCompound("Captured").copy() : null;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, net.minecraft.core.HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (captured != null) tag.put("Captured", captured.copy());
    }
}
