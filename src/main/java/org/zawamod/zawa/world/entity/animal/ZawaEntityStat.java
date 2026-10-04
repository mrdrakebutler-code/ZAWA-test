package org.zawamod.zawa.world.entity.animal;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;

/**
 * Runtime stat tracker used by ZAWA animals for hunger, thirst and enrichment.
 *
 * <p>This is a direct behavioral migration of the 1.20.1 implementation. The
 * value itself remains synchronized through the owning entity's
 * {@link SynchedEntityData}; the local timers are deliberately not synced.</p>
 */
public final class ZawaEntityStat {
    private final ZawaBaseEntity entity;
    private final EntityDataAccessor<Integer> dataAccessor;
    private final int max;
    private final int depleteTimer;

    private int ticks;
    private int saturationTimer;

    public ZawaEntityStat(
            ZawaBaseEntity entity,
            EntityDataAccessor<Integer> dataAccessor,
            int max,
            int depleteTimer
    ) {
        this.entity = entity;
        this.dataAccessor = dataAccessor;
        this.max = max;
        this.depleteTimer = depleteTimer;
        this.ticks = depleteTimer;

        // The original initializes the synchronized stat to its maximum.
        entity.getEntityData().set(dataAccessor, max);
    }

    /**
     * Advances the stat by one game tick.
     *
     * <p>A recent increment temporarily suppresses depletion through the
     * saturation timer. Once the normal depletion timer expires, the value is
     * reduced by exactly one.</p>
     */
    public void tick() {
        if (--this.saturationTimer > 0 || this.isHurting()) {
            return;
        }

        if (--this.ticks <= 0) {
            this.ticks = this.depleteTimer;
            int value = this.entity.getEntityData().get(this.dataAccessor);
            this.entity.getEntityData().set(this.dataAccessor, value - 1);
        }
    }

    /**
     * Adds to the stat, clamped to its configured maximum, and refreshes its
     * temporary saturation period to half the depletion interval.
     */
    public void increment(int amount) {
        int value = this.entity.getEntityData().get(this.dataAccessor);
        this.entity.getEntityData().set(
                this.dataAccessor,
                Math.min(value + amount, this.max)
        );
        this.saturationTimer = this.depleteTimer / 2;
    }

    public int getValue() {
        return this.entity.getEntityData().get(this.dataAccessor);
    }

    public int getMax() {
        return this.max;
    }

    /** Returns true when the stat has reached zero or below. */
    public boolean isHurting() {
        return this.getValue() <= 0;
    }

    /** Serializes the value and local saturation timer using the original keys. */
    public CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("Value", this.getValue());
        tag.putInt("Saturation", this.saturationTimer);
        return tag;
    }

    /** Restores the value and local saturation timer from persistent data. */
    public void fromTag(CompoundTag tag) {
        this.entity.getEntityData().set(
                this.dataAccessor,
                tag.getInt("Value")
        );
        this.saturationTimer = tag.getInt("Saturation");
    }
}
