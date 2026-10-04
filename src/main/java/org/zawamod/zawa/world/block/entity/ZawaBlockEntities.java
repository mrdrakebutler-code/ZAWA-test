package org.zawamod.zawa.world.block.entity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.zawamod.zawa.Zawa;
import org.zawamod.zawa.world.block.ZawaBlocks;

public final class ZawaBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(net.minecraft.core.registries.Registries.BLOCK_ENTITY_TYPE, Zawa.MOD_ID);

    public static final var INCUBATOR = BLOCK_ENTITIES.register("incubator",
            () -> BlockEntityType.Builder.of(IncubatorBlockEntity::new, ZawaBlocks.INCUBATOR.get()).build(null));
    public static final var BUG_BOX = BLOCK_ENTITIES.register("bug_box",
            () -> BlockEntityType.Builder.of(BugBoxBlockEntity::new, ZawaBlocks.BUG_BOX.get()).build(null));
    public static final var HYDROPONICS_TABLE = BLOCK_ENTITIES.register("hydroponics_table",
            () -> BlockEntityType.Builder.of(HydroponicsTableBlockEntity::new, ZawaBlocks.HYDROPONICS_TABLE.get()).build(null));
    public static final var MUSSEL_BOX = BLOCK_ENTITIES.register("mussel_box",
            () -> BlockEntityType.Builder.of(MusselBoxBlockEntity::new, ZawaBlocks.MUSSEL_BOX.get()).build(null));
    public static final var GROUND_FEEDER = BLOCK_ENTITIES.register("ground_feeder",
            () -> BlockEntityType.Builder.of(GroundFeederBlockEntity::new, ZawaBlocks.BAMBOO_FEEDER.get()).build(null));
    public static final var WALL_FEEDER = BLOCK_ENTITIES.register("wall_feeder",
            () -> BlockEntityType.Builder.of(WallFeederBlockEntity::new, ZawaBlocks.BAMBOO_WALL_FORAGE.get()).build(null));
    public static final var CAPTURE = BLOCK_ENTITIES.register("capture",
            () -> BlockEntityType.Builder.of(CaptureBlockEntity::new, ZawaBlocks.CAPTURE_CAGE.get(), ZawaBlocks.SEINE_NET.get()).build(null));

    private ZawaBlockEntities() {}
}
