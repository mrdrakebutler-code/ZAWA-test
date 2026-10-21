package org.zawamod.zawa.world.entity.animal;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.level.Level;
import org.zawamod.zawa.world.entity.ai.goal.BreachGoal;
import org.zawamod.zawa.world.entity.ai.goal.ZawaMeleeAttackGoal;

/**
 * Orca values and goals recovered from the original ZAWA 1.20.1
 * IntelligentAnimals3-alpha5 Orca.class bytecode.
 * Experimental standalone extraction branch; not yet a standalone mod.
 */
public class Orca extends ZawaAquaticEntity {
    public Orca(EntityType<? extends ZawaAquaticEntity> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder registerOrcaAttributes() {
        return createLivingAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.MAX_HEALTH, 120.0D)
                .add(Attributes.ATTACK_DAMAGE, 20.0D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(4, new BreachGoal(this, 5));
        this.goalSelector.addGoal(5, new ZawaMeleeAttackGoal(this, 4.0D, 1.33D, true));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
    }

    /** Only vocalize an attack when the inherited melee hit actually connects. */
    @Override
    public boolean doHurtTarget(Entity target) {
        boolean hit = super.doHurtTarget(target);
        if (hit && !level().isClientSide) {
            playSound(OrcaSounds.ATTACKING.get(), 1.0F, 1.0F);
        }
        return hit;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return OrcaSounds.AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(net.minecraft.world.damagesource.DamageSource source) {
        return OrcaSounds.HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return OrcaSounds.HURT.get();
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob other) {
        return ZawaEntities.ORCA.get().create(level);
    }
}
