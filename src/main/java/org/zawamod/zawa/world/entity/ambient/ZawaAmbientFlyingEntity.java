package org.zawamod.zawa.world.entity.ambient;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;

/** Shared ambient flying-animal foundation recovered for the NeoForge port. */
public abstract class ZawaAmbientFlyingEntity extends ZawaBaseAmbientEntity {
    protected ZawaAmbientFlyingEntity(EntityType<? extends ZawaBaseAmbientEntity> type, Level level) {
        super(type, level);
        this.moveControl = new FlyingMoveControl(this, 20, true);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new FlyingPathNavigation(this, level);
    }

    public boolean isFlying() {
        return !onGround();
    }
}
