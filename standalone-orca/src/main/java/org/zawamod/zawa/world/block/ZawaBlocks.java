package org.zawamod.zawa.world.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.zawamod.zawa.Zawa;
import org.zawamod.zawa.world.block.entity.ZawaBlockEntities;

/** Orca-only feeder block registry retaining the original IDs. */
public final class ZawaBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Zawa.MOD_ID);
    public static final DeferredBlock<Block> BAMBOO_FEEDER =
            BLOCKS.register("bamboo_feeder", () -> new ZawaMachineBlock(
                    BlockBehaviour.Properties.of().strength(2.0f).noOcclusion(), ZawaBlockEntities.GROUND_FEEDER));
    public static final DeferredBlock<Block> BAMBOO_WALL_FORAGE =
            BLOCKS.register("bamboo_wall_forage", () -> new ZawaMachineBlock(
                    BlockBehaviour.Properties.of().strength(2.0f).noOcclusion(), ZawaBlockEntities.WALL_FEEDER));
    private ZawaBlocks() {}
}
