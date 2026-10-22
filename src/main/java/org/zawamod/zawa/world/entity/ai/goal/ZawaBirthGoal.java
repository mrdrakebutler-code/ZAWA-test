package org.zawamod.zawa.world.entity.ai.goal;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.Goal;
import org.zawamod.zawa.world.entity.animal.ZawaBaseEntity;

/**
 * Server-side gestation and retry handling. A pregnancy with a zero timer
 * must remain eligible for the birth goal after a world reload.
 */
public class ZawaBirthGoal extends Goal {
    private final ZawaBaseEntity mother;
    private int retryCooldown;

    public ZawaBirthGoal(ZawaBaseEntity mother) {
        this.mother = mother;
    }

    @Override
    public boolean canUse() {
        return mother.isPregnant() && mother.level() instanceof ServerLevel;
    }

    @Override
    public boolean canContinueToUse() {
        return canUse();
    }

    @Override
    public void tick() {
        if (!(mother.level() instanceof ServerLevel server) || !mother.isPregnant()) {
            return;
        }
        if (retryCooldown > 0) {
            retryCooldown--;
            return;
        }
        int remaining = mother.getGestationTimer();
        if (remaining <= 1) {
            mother.spawnChildrenFromPregnancy(server);
            // Failed spawns retain the saved calves; do not retry every tick.
            if (mother.isPregnant()) retryCooldown = 20;
        } else {
            mother.setGestationTimer(remaining - 1);
        }
    }
}
