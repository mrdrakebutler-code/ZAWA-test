package org.zawamod.zawa.world.entity.ambient;

import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import org.zawamod.zawa.config.ZawaSpawnCategory;
import org.zawamod.zawa.world.entity.SpeciesVariantsEntity;
import org.zawamod.zawa.world.entity.VenomousEntity;

public class Scorpion extends ZawaAmbientLandEntity implements SpeciesVariantsEntity, VenomousEntity {
    public static final List<Tuple<String, ZawaSpawnCategory>> VARIANT_SPAWNS = List.of(
        new Tuple<>("emperor", ZawaSpawnCategory.DEEP_RAINFOREST), new Tuple<>("giant_hairy", ZawaSpawnCategory.HOT_DESERT),
        new Tuple<>("red_claw", ZawaSpawnCategory.DEEP_RAINFOREST), new Tuple<>("arizona_bark", ZawaSpawnCategory.DRY_GRASSLAND),
        new Tuple<>("asian_forest", ZawaSpawnCategory.DEEP_RAINFOREST), new Tuple<>("common_yellow", ZawaSpawnCategory.HOT_DESERT),
        new Tuple<>("northern", ZawaSpawnCategory.DRY_GRASSLAND));
    private int venomTimer;
    public Scorpion(EntityType<? extends ZawaBaseAmbientEntity> type, Level level) { super(type, level); }
    public static AttributeSupplier.Builder registerScorpionAttributes() { return createLivingAttributes().add(Attributes.MOVEMENT_SPEED, 0.10D).add(Attributes.MAX_HEALTH, 2D).add(Attributes.ATTACK_DAMAGE, 4D); }
    @Override public int getVariantByBiome(net.minecraft.world.level.LevelAccessor level) { return 0; }
    @Override public boolean hasVenom() { return venomTimer <= 0; }
    @Override public void setMilked(int ticks) { venomTimer = Math.max(0, ticks); }
    @Override public boolean readyToBeMilked() { return hasVenom(); }
    @Override public void tick() { super.tick(); if (venomTimer > 0) venomTimer--; }
    @Override public void addAdditionalSaveData(CompoundTag tag) { super.addAdditionalSaveData(tag); tag.putInt("VenomTimer", venomTimer); }
    @Override public void readAdditionalSaveData(CompoundTag tag) { super.readAdditionalSaveData(tag); venomTimer = tag.getInt("VenomTimer"); }
}
