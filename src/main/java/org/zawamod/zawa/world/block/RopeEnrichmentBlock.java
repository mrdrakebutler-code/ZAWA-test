package org.zawamod.zawa.world.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

/** Restores the original rope's ATTACHED state and support-aware placement. */
public class RopeEnrichmentBlock extends EnrichmentBlock {
    public static final BooleanProperty ATTACHED=BooleanProperty.create("attached");
    public RopeEnrichmentBlock(Properties p){super(p,"rope");registerDefaultState(defaultBlockState().setValue(ATTACHED,false));}
    @Override public BlockState getStateForPlacement(BlockPlaceContext c){
        BlockState above=c.getLevel().getBlockState(c.getClickedPos().above());
        return defaultBlockState().setValue(ATTACHED, above.getBlock() instanceof LeavesBlock || !above.isAir() || above.is(this));
    }
    @Override protected void createBlockStateDefinition(StateDefinition.Builder<net.minecraft.world.level.block.Block,BlockState> b){b.add(ATTACHED);}
}
