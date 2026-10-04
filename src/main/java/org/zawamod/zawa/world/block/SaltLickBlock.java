package org.zawamod.zawa.world.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.zawamod.zawa.world.entity.animal.ZawaBaseEntity;
import org.zawamod.zawa.world.entity.animal.ZawaFeedingUtil;

/** Original ZAWA salt lick used a bite-count state consumed by animals. */
public class SaltLickBlock extends EnrichmentBlock {
    public static final IntegerProperty BITES = IntegerProperty.create("bites", 0, 4);
    public SaltLickBlock(Properties p) { super(p, "salt_lick"); registerDefaultState(defaultBlockState().setValue(BITES, 0)); }

    public void use(Level level, BlockPos pos, BlockState state, ZawaBaseEntity animal) {
        if (level.isClientSide || animal.isEnrichmentOnCooldown(pos)) return;
        ZawaFeedingUtil.provideEnrichment(animal, 4);
        animal.markEnrichmentUsed(pos);
        int bites=state.getValue(BITES);
        if (bites >= 4) level.destroyBlock(pos, false);
        else level.setBlock(pos, state.setValue(BITES, bites+1), 3);
    }
    @Override protected void createBlockStateDefinition(StateDefinition.Builder<net.minecraft.world.level.block.Block,BlockState> b) {
        b.add(BITES);
    }
}
