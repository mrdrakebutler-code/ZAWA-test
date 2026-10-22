package org.zawamod.zawa.world.block.entity;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.zawamod.zawa.Zawa;
import org.zawamod.zawa.world.block.ZawaBlocks;

/** Orca-only ground and wall feeder block entities. */
public final class ZawaBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Zawa.MOD_ID);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GroundFeederBlockEntity>> GROUND_FEEDER =
            BLOCK_ENTITIES.register("ground_feeder", () -> BlockEntityType.Builder.of(
                    GroundFeederBlockEntity::new, ZawaBlocks.BAMBOO_FEEDER.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<WallFeederBlockEntity>> WALL_FEEDER =
            BLOCK_ENTITIES.register("wall_feeder", () -> BlockEntityType.Builder.of(
                    WallFeederBlockEntity::new, ZawaBlocks.BAMBOO_WALL_FORAGE.get()).build(null));
    private ZawaBlockEntities() {}
}
