package org.zawamod.zawa.world.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

/** Restores the original heat rock's WATERLOGGED state. */
public class HeatRockBlock extends EnrichmentBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED=BlockStateProperties.WATERLOGGED;
    public HeatRockBlock(Properties p){ super(p,"heat_rock"); registerDefaultState(defaultBlockState().setValue(WATERLOGGED,false)); }
    @Override public BlockState getStateForPlacement(BlockPlaceContext c){
        return defaultBlockState().setValue(WATERLOGGED,c.getLevel().getFluidState(c.getClickedPos()).getType()==Fluids.WATER);
    }
    @Override protected BlockState updateShape(BlockState state, net.minecraft.world.level.LevelReader level,
            net.minecraft.world.level.ScheduledTickAccess ticks, BlockPos pos, Direction direction,
            BlockPos neighborPos, BlockState neighborState, net.minecraft.util.RandomSource random){
        if(state.getValue(WATERLOGGED)) ticks.scheduleTick(pos,Fluids.WATER,Fluids.WATER.getTickDelay(level));
        return state;
    }
    @Override public FluidState getFluidState(BlockState s){ return s.getValue(WATERLOGGED)?Fluids.WATER.getSource(false):super.getFluidState(s); }
    @Override protected void createBlockStateDefinition(StateDefinition.Builder<net.minecraft.world.level.block.Block,BlockState> b){b.add(WATERLOGGED);}
}
