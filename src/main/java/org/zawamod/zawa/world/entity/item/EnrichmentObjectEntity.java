package org.zawamod.zawa.world.entity.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.zawamod.zawa.world.entity.animal.ZawaBaseEntity;
import org.zawamod.zawa.world.entity.animal.ZawaFeedingUtil;
import org.zawamod.zawa.world.item.ZawaItems;

import java.util.List;

/**
 * 1.21.1 reconstruction of the original Ball and IceTreat entities.
 * Balls bounce; ice treats slide. Nearby compatible animals can consume/use them.
 */
public class EnrichmentObjectEntity extends Entity implements net.minecraft.world.entity.projectile.ItemSupplier {
    private static final EntityDataAccessor<Integer> KIND =
            SynchedEntityData.defineId(EnrichmentObjectEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> DAMAGE =
            SynchedEntityData.defineId(EnrichmentObjectEntity.class, EntityDataSerializers.FLOAT);

    public enum Kind { BOOMER_BALL, SCENTED_BALL, APPLE_ICE_TREAT, BEEF_ICE_TREAT }

    public EnrichmentObjectEntity(EntityType<? extends EnrichmentObjectEntity> type, Level level) {
        super(type, level);
    }

    @Override protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(KIND, 0);
        builder.define(DAMAGE, 0F);
    }

    public Kind getKind() {
        Kind[] values = Kind.values();
        return values[Math.max(0, Math.min(values.length - 1, entityData.get(KIND)))];
    }
    public void setKind(Kind kind) { entityData.set(KIND, kind.ordinal()); }

    @Override public void tick() {
        super.tick();
        Vec3 velocity = getDeltaMovement();
        if (!isNoGravity()) velocity = velocity.add(0, -0.06D, 0);

        move(MoverType.SELF, velocity);
        boolean ball = getKind() == Kind.BOOMER_BALL || getKind() == Kind.SCENTED_BALL;
        if (horizontalCollision) {
            velocity = new Vec3(ball ? -velocity.x * .65D : velocity.x * .25D, velocity.y,
                                ball ? -velocity.z * .65D : velocity.z * .25D);
        }
        if (onGround()) {
            velocity = new Vec3(velocity.x * (ball ? .77D : .72D),
                                ball && Math.abs(velocity.y) > .08D ? Math.abs(velocity.y) * .55D : 0,
                                velocity.z * (ball ? .77D : .72D));
        } else velocity = velocity.scale(.98D);
        setDeltaMovement(velocity);

        if (!level().isClientSide && tickCount % 10 == 0) {
            List<ZawaBaseEntity> animals = level().getEntitiesOfClass(ZawaBaseEntity.class,
                    getBoundingBox().inflate(1.5D), a -> a.isAlive());
            for (ZawaBaseEntity animal : animals) {
                if (!animal.acceptsEnrichmentEntity(registryName())) continue;
                if (ball) ZawaFeedingUtil.provideEnrichment(animal, 4);
                else {
                    ZawaFeedingUtil.provideEnrichment(animal, 5);
                    ZawaFeedingUtil.feedTreat(animal, getKind() == Kind.APPLE_ICE_TREAT);
                }
                animal.markEnrichmentUsed(blockPosition());
                if (!ball) discard();
                else setDeltaMovement(getDeltaMovement().add(
                        (getX() - animal.getX()) * .08D, .18D, (getZ() - animal.getZ()) * .08D));
                break;
            }
        }
    }

    private String registryName() {
        return switch (getKind()) {
            case BOOMER_BALL -> "zawa:boomer_ball";
            case SCENTED_BALL -> "zawa:scented_ball";
            case APPLE_ICE_TREAT -> "zawa:apple_ice_treat";
            case BEEF_ICE_TREAT -> "zawa:beef_ice_treat";
        };
    }

    @Override public boolean hurt(DamageSource source, float amount) {
        if (level().isClientSide) return true;
        entityData.set(DAMAGE, entityData.get(DAMAGE) + amount * 10F);
        Entity attacker = source.getEntity();
        if (attacker != null) setDeltaMovement(getDeltaMovement().add(attacker.getLookAngle().scale(amount)));
        if (entityData.get(DAMAGE) > 40F) discard();
        return true;
    }

    @Override public InteractionResult interact(Player player, InteractionHand hand) {
        if (!level().isClientSide) {
            if (!player.getAbilities().instabuild && level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS))
                spawnAtLocation(asItem());
            discard();
        }
        return InteractionResult.sidedSuccess(level().isClientSide);
    }

    @Override public ItemStack getItem() { return new ItemStack(asItem()); }

    private Item asItem() {
        return switch (getKind()) {
            case BOOMER_BALL -> ZawaItems.BOOMER_BALL.get();
            case SCENTED_BALL -> ZawaItems.SCENTED_BALL.get();
            case APPLE_ICE_TREAT -> ZawaItems.APPLE_ICE_TREAT.get();
            case BEEF_ICE_TREAT -> ZawaItems.BEEF_ICE_TREAT.get();
        };
    }

    @Override protected void readAdditionalSaveData(CompoundTag tag) {
        if (tag.contains("Kind")) setKind(Kind.values()[Math.max(0, Math.min(Kind.values().length - 1, tag.getInt("Kind")))]);
        entityData.set(DAMAGE, tag.getFloat("Damage"));
    }
    @Override protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putInt("Kind", getKind().ordinal());
        tag.putFloat("Damage", entityData.get(DAMAGE));
    }
}
