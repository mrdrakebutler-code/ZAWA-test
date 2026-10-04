package org.zawamod.zawa.world.entity.ai.goal;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class ZawaMeleeAttackGoal extends MeleeAttackGoal {
    private final double attackReach;
    public ZawaMeleeAttackGoal(PathfinderMob mob, double attackReach, double speed, boolean follow) {
        super(mob, speed, follow); this.attackReach = attackReach;
    }
    @Override protected void checkAndPerformAttack(LivingEntity target, double distance) {
        double reach = getAttackReachSqr(target);
        if (distance <= reach && canPerformAttack()) {
            resetAttackCooldown(); mob.swing(net.minecraft.world.InteractionHand.MAIN_HAND); mob.doHurtTarget(target);
        } else if (distance <= reach + attackReach && canPerformAttack()) {
            resetAttackCooldown(); mob.swing(net.minecraft.world.InteractionHand.MAIN_HAND); mob.doHurtTarget(target);
        }
    }
    @Override protected double getAttackReachSqr(LivingEntity target) {
        float w = mob.getBbWidth();
        return w * w * 2.0 + target.getBbWidth() + attackReach;
    }
}
