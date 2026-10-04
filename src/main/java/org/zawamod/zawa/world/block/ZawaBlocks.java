package org.zawamod.zawa.world.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.zawamod.zawa.Zawa;
import org.zawamod.zawa.world.block.entity.ZawaBlockEntities;

public final class ZawaBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Zawa.MOD_ID);

    private static BlockBehaviour.Properties machineProps() {
        return BlockBehaviour.Properties.of().strength(2.0f).noOcclusion();
    }

    public static final DeferredBlock<Block> INCUBATOR = BLOCKS.register("incubator", () -> new ZawaMachineBlock(machineProps(), ZawaBlockEntities.INCUBATOR));
    public static final DeferredBlock<Block> BUG_BOX = BLOCKS.register("bug_box", () -> new ZawaMachineBlock(machineProps(), ZawaBlockEntities.BUG_BOX));
    public static final DeferredBlock<Block> HYDROPONICS_TABLE = BLOCKS.register("hydroponics_table", () -> new ZawaMachineBlock(machineProps(), ZawaBlockEntities.HYDROPONICS_TABLE));
    public static final DeferredBlock<Block> MUSSEL_BOX = BLOCKS.register("mussel_box", () -> new ZawaMachineBlock(machineProps(), ZawaBlockEntities.MUSSEL_BOX));
    public static final DeferredBlock<Block> BAMBOO_FEEDER = BLOCKS.register("bamboo_feeder", () -> new ZawaMachineBlock(machineProps(), ZawaBlockEntities.GROUND_FEEDER));
    public static final DeferredBlock<Block> BAMBOO_WALL_FORAGE = BLOCKS.register("bamboo_wall_forage", () -> new ZawaMachineBlock(machineProps(), ZawaBlockEntities.WALL_FEEDER));
    public static final DeferredBlock<Block> CAPTURE_CAGE = BLOCKS.register("capture_cage", () -> new ZawaMachineBlock(machineProps(), ZawaBlockEntities.CAPTURE));
    public static final DeferredBlock<Block> SEINE_NET = BLOCKS.register("seine_net", () -> new ZawaMachineBlock(machineProps(), ZawaBlockEntities.CAPTURE));


    // Stage 35: original block-based enrichment roster referenced by entity_stats.
    public static final DeferredBlock<Block> BLACK_BAMBOO_FEEDER = enrichment("black_bamboo_feeder");
    public static final DeferredBlock<Block> YELLOW_BAMBOO_FEEDER = enrichment("yellow_bamboo_feeder");
    public static final DeferredBlock<Block> BAMBOO_CHIMES = enrichment("bamboo_chimes");
    public static final DeferredBlock<Block> BLACK_BAMBOO_CHIMES = enrichment("black_bamboo_chimes");
    public static final DeferredBlock<Block> YELLOW_BAMBOO_CHIMES = enrichment("yellow_bamboo_chimes");
    public static final DeferredBlock<Block> SCRATCHING_POST = enrichment("scratching_post");
    public static final DeferredBlock<Block> LEAF_BROWSE = enrichment("leaf_browse");
    public static final DeferredBlock<Block> HAY_BROWSE = enrichment("hay_browse");
    public static final DeferredBlock<Block> SPRINKLER = BLOCKS.register("sprinkler", () -> new SprinklerEnrichmentBlock(BlockBehaviour.Properties.of().strength(1.5f).noOcclusion()));
    public static final DeferredBlock<Block> PINEAPPLE_PINATA = enrichment("pineapple_pinata");
    public static final DeferredBlock<Block> PUZZLE_FEEDER = enrichment("puzzle_feeder");
    public static final DeferredBlock<Block> BALL_PIT = enrichment("ball_pit");
    public static final DeferredBlock<Block> TIRE_SWING = enrichment("tire_swing");
    public static final DeferredBlock<Block> CLIMBING_VINE = enrichment("climbing_vine");
    public static final DeferredBlock<Block> ROPE = BLOCKS.register("rope", () -> new RopeEnrichmentBlock(BlockBehaviour.Properties.of().strength(1.5f).noOcclusion()));
    public static final DeferredBlock<Block> SNOW_PIT = enrichment("snow_pit");
    public static final DeferredBlock<Block> SALT_LICK = BLOCKS.register("salt_lick", () -> new SaltLickBlock(BlockBehaviour.Properties.of().strength(1.5f).noOcclusion()));
    public static final DeferredBlock<Block> PERCHING_STAND = enrichment("perching_stand");
    public static final DeferredBlock<Block> HEAT_LAMP = enrichment("heat_lamp");
    public static final DeferredBlock<Block> HEAT_ROCK = BLOCKS.register("heat_rock", () -> new HeatRockBlock(BlockBehaviour.Properties.of().strength(1.5f).noOcclusion()));

    private static DeferredBlock<Block> enrichment(String name) {
        return BLOCKS.register(name, () -> new EnrichmentBlock(
                BlockBehaviour.Properties.of().strength(1.5f).noOcclusion(), name));
    }

    private ZawaBlocks() {}
}
