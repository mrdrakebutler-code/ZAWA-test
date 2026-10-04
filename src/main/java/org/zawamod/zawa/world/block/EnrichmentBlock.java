package org.zawamod.zawa.world.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.zawamod.zawa.world.entity.animal.ZawaBaseEntity;
import org.zawamod.zawa.world.entity.animal.ZawaFeedingUtil;

/**
 * Common Stage 35 behavior for the original static enrichment blocks.
 * Compatibility remains data-driven: SeekEnrichmentGoal only approaches block
 * IDs explicitly listed by that species' entity_stats.
 */
public class EnrichmentBlock extends Block {
    private final String enrichmentId;

    public EnrichmentBlock(Properties properties, String enrichmentId) {
        super(properties);
        this.enrichmentId = enrichmentId;
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, net.minecraft.world.entity.Entity entity) {
        super.stepOn(level, pos, state, entity);
        if (!level.isClientSide && entity instanceof ZawaBaseEntity animal && !animal.isEnrichmentOnCooldown(pos)) {
            ZawaFeedingUtil.provideEnrichment(animal, 1);
            animal.markEnrichmentUsed(pos);
        }
    }

    public String enrichmentId() { return enrichmentId; }
}
