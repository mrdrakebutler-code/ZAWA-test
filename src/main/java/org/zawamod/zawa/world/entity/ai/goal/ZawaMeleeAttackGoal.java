package org.zawamod.zawa.world.entity.ai.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

/**
 * ZAWA melee goal with an adjustable reach bonus.
 *
 * Minecraft 1.21.1 moved the attack hook to canPerformAttack(LivingEntity),
 * so keep ZAWA's larger-animal reach behaviour there rather than overriding
 * the removed 1.20-era checkAndPerformAttack/getAttackReachSqr hooks.
 */
public class ZawaMeleeAttackGoal extends MeleeAttackGoal {
    private final double attackReach;

    public ZawaMeleeAttackGoal(PathfinderMob mob, double attackReach, double speed, boolean follow) {
        super(mob, speed, follow);
        this.attackReach = attackReach;
    }

    @Override
    protected boolean canPerformAttack(LivingEntity target) {
        double vanillaReach = this.mob.getMeleeAttackRangeSqr(target);
        double extraReach = Math.max(0.0D, this.attackReach);
        double reach = vanillaReach + extraReach * extraReach;
        return this.mob.getPerceivedTargetDistanceSquareForMeleeAttack(target) <= reach
                && this.isTimeToAttack()
                && this.mob.getSensing().hasLineOfSight(target);
    }
}
