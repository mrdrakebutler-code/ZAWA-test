package org.zawamod.zawa.world.entity.animal;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.TryFindWaterGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
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
                .add(Attributes.FOLLOW_RANGE, 32.0D)
                .add(Attributes.ATTACK_DAMAGE, 20.0D);
    }

    @Override
    protected void registerGoals() {
        // Standalone breeding uses Minecraft's immediate baby spawning, not ZAWA pregnancy.
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0D, 10));
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

    /** Original orca eye position: half the height of the current entity dimensions. */
    @Override
    protected EntityDimensions getDefaultDimensions(Pose pose) {
        EntityDimensions dimensions = super.getDefaultDimensions(pose);
        return dimensions.withEyeHeight(dimensions.height() * 0.5F);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.is(Items.SALMON) && !isBaby() && canBreed()) {
            if (!level().isClientSide) {
                setInLove(player);
                if (!player.getAbilities().instabuild) stack.shrink(1);
            }
            return InteractionResult.sidedSuccess(level().isClientSide);
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.SALMON);
    }

    @Override
    public boolean canBreed() {
        return !isBaby() && !isPregnant() && super.isAlive() && getAge() == 0;
    }

    @Override
    public boolean canMate(Animal other) {
        return other instanceof Orca mate && mate != this &&
                isInLove() && mate.isInLove() && canBreed() && mate.canBreed();
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob other) {
        return org.zawamod.zawa.standalone.OrcaOnlyEntities.ORCA.get().create(level);
    }
}
