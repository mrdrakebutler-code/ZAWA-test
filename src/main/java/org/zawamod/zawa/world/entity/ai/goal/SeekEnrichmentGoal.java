package org.zawamod.zawa.world.entity.ai.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.goal.Goal;
import org.zawamod.zawa.resources.EntityStatsManager;
import org.zawamod.zawa.world.entity.animal.ZawaBaseEntity;
import org.zawamod.zawa.world.entity.animal.ZawaFeedingUtil;
import org.zawamod.zawa.world.entity.stats.EntityEnrichment;

import java.util.EnumSet;
import java.util.Set;

/**
 * Finds block enrichment explicitly allowed by the species' entity_stats JSON.
 * The animal receives enrichment only after reaching the object.
 */
public final class SeekEnrichmentGoal extends Goal {
    private final ZawaBaseEntity animal;
    private final double speed;
    private final int radius;
    private BlockPos target;
    private int cooldown;

    public SeekEnrichmentGoal(ZawaBaseEntity animal, double speed, int radius) {
        this.animal = animal; this.speed = speed; this.radius = radius;
        setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override public boolean canUse() {
        if (cooldown-- > 0 || animal.getEnrichment().getValue() >= animal.getEnrichment().getMax()) return false;
        cooldown = 100 + animal.getRandom().nextInt(100);
        target = findEnrichment();
        return target != null;
    }

    @Override public boolean canContinueToUse() {
        return target != null && animal.getEnrichment().getValue() < animal.getEnrichment().getMax()
                && animal.blockPosition().distSqr(target) <= radius * radius * 2L;
    }

    @Override public void start() { move(); }
    @Override public void tick() {
        if (target == null) return;
        if (animal.blockPosition().distSqr(target) <= 5.0D) {
            ZawaFeedingUtil.provideEnrichment(animal, 4);
            animal.markEnrichmentUsed(target);
            stop();
        } else if (animal.getNavigation().isDone()) move();
    }

    private void move() { animal.getNavigation().moveTo(target.getX() + .5, target.getY(), target.getZ() + .5, speed); }
    @Override public void stop() { target = null; animal.getNavigation().stop(); }

    private BlockPos findEnrichment() {
        EntityEnrichment enrichment = EntityStatsManager.INSTANCE.getStats(animal).getEnrichment();
        if (enrichment == null) return null;
        Set<ResourceLocation> allowed = enrichment.get("zawa:blocks");
        if (allowed.isEmpty()) allowed = enrichment.get("blocks");
        if (allowed.isEmpty()) return null; // entity-style enrichment is handled by item interaction/treat logic

        BlockPos origin = animal.blockPosition();
        BlockPos best = null; double bestDist = Double.MAX_VALUE;
        for (BlockPos pos : BlockPos.betweenClosed(origin.offset(-radius, -4, -radius), origin.offset(radius, 4, radius))) {
            ResourceLocation id = BuiltInRegistries.BLOCK.getKey(animal.level().getBlockState(pos).getBlock());
            if (!allowed.contains(id) || animal.isEnrichmentOnCooldown(pos)) continue;
            double d = origin.distSqr(pos);
            if (d < bestDist) { bestDist = d; best = pos.immutable(); }
        }
        return best;
    }
}
