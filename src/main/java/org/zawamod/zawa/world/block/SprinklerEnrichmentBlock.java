package org.zawamod.zawa.world.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.zawamod.zawa.world.entity.animal.ZawaBaseEntity;
import org.zawamod.zawa.world.entity.animal.ZawaFeedingUtil;

/** Restores the original sprinkler ON/SNOWY state model and active-area enrichment. */
public class SprinklerEnrichmentBlock extends EnrichmentBlock {
    public static final BooleanProperty ON=BooleanProperty.create("on");
    public static final BooleanProperty SNOWY=BooleanProperty.create("snowy");
    public SprinklerEnrichmentBlock(Properties p){super(p,"sprinkler");registerDefaultState(defaultBlockState().setValue(ON,false).setValue(SNOWY,false));}
    @Override protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random){
        if(!state.getValue(ON)) return;
        for(ZawaBaseEntity a: level.getEntitiesOfClass(ZawaBaseEntity.class,new net.minecraft.world.phys.AABB(pos).inflate(4))){
            if(a.acceptsEnrichmentBlock("zawa:sprinkler") && !a.isEnrichmentOnCooldown(pos)){
                ZawaFeedingUtil.provideEnrichment(a,2); a.markEnrichmentUsed(pos);
            }
        }
        level.scheduleTick(pos,this,20);
    }
    @Override protected void createBlockStateDefinition(StateDefinition.Builder<net.minecraft.world.level.block.Block,BlockState> b){b.add(ON,SNOWY);}
}
