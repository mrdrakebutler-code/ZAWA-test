package org.zawamod.zawa.world.entity.ambient;

import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import org.zawamod.zawa.config.ZawaSpawnCategory;
import org.zawamod.zawa.world.entity.SpeciesVariantsEntity;
import org.zawamod.zawa.world.entity.VenomousEntity;

public class Tarantula extends ZawaAmbientLandEntity implements SpeciesVariantsEntity, VenomousEntity {
    public static final List<Tuple<String, ZawaSpawnCategory>> VARIANT_SPAWNS = List.of(
        new Tuple<>("mexican_red_knee", ZawaSpawnCategory.DRY_RAINFOREST), new Tuple<>("cobalt_blue", ZawaSpawnCategory.WET_RAINFOREST),
        new Tuple<>("goliath_birdeater", ZawaSpawnCategory.WET_RAINFOREST), new Tuple<>("chilean_rosehair", ZawaSpawnCategory.HOT_DESERT),
        new Tuple<>("brazilian_black", ZawaSpawnCategory.DRY_GRASSLAND), new Tuple<>("king_baboon", ZawaSpawnCategory.WET_SAVANNA),
        new Tuple<>("western_desert", ZawaSpawnCategory.HOT_DESERT), new Tuple<>("greenbottle_blue", ZawaSpawnCategory.HOT_DESERT),
        new Tuple<>("antilles_pinktoe", ZawaSpawnCategory.DRY_RAINFOREST));
    private int venomTimer;
    public Tarantula(EntityType<? extends ZawaBaseAmbientEntity> type, Level level) { super(type, level); }
    public static AttributeSupplier.Builder registerTarantulaAttributes() { return createLivingAttributes().add(Attributes.MOVEMENT_SPEED, 0.10D).add(Attributes.MAX_HEALTH, 4D).add(Attributes.ATTACK_DAMAGE, 4D); }
    @Override public int getVariantByBiome(net.minecraft.world.level.LevelAccessor level) { return 0; }
    @Override public boolean hasVenom() { return venomTimer <= 0; }
    @Override public void setMilked(int ticks) { venomTimer = Math.max(0, ticks); }
    @Override public boolean readyToBeMilked() { return hasVenom(); }
    @Override public void tick() { super.tick(); if (venomTimer > 0) venomTimer--; }
    @Override public void addAdditionalSaveData(CompoundTag tag) { super.addAdditionalSaveData(tag); tag.putInt("VenomTimer", venomTimer); }
    @Override public void readAdditionalSaveData(CompoundTag tag) { super.readAdditionalSaveData(tag); venomTimer = tag.getInt("VenomTimer"); }
}
