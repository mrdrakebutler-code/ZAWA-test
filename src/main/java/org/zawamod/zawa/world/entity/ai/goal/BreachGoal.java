package org.zawamod.zawa.world.entity.ai.goal;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;

/** Occasional surface breach; never repeatedly boosts an animal into the sky. */
public class BreachGoal extends Goal {
    private final PathfinderMob entity;
    private final int interval;
    private int cooldown = 0;

    public BreachGoal(PathfinderMob entity, int interval) {
        this.entity = entity;
        this.interval = interval;
    }

    @Override
    public boolean canUse() {
        if (cooldown > 0) {
            cooldown--;
            return false;
        }
        // Only breach near the surface, with open air immediately above.
        return entity.isInWater() && !entity.level().getFluidState(entity.blockPosition().above()).is(net.minecraft.tags.FluidTags.WATER)
                && entity.getRandom().nextInt(Math.max(1, interval)) == 0;
    }

    @Override
    public void start() {
        cooldown = 200;
        entity.setDeltaMovement(entity.getDeltaMovement().add(0.0D, 0.35D, 0.0D));
    }
}
