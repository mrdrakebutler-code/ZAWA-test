package org.zawamod.zawa.world.entity.animal;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import org.zawamod.zawa.config.ZawaMainConfig;
import org.zawamod.zawa.world.entity.SpeciesVariantsEntity;
import org.zawamod.zawa.world.entity.stats.EntityFertilityCategory;
import org.zawamod.zawa.world.entity.ai.goal.ZawaBirthGoal;
import org.zawamod.zawa.world.entity.ai.goal.ZawaBreedGoal;
import org.zawamod.zawa.world.entity.ai.goal.ZawaFollowParentGoal;
import org.zawamod.zawa.world.entity.ai.goal.SeekFeederGoal;
import org.zawamod.zawa.world.entity.ai.goal.SeekWaterGoal;
import org.zawamod.zawa.world.entity.ai.goal.SeekEnrichmentGoal;
import java.util.HashMap;
import java.util.Map;
import org.zawamod.zawa.resources.EntityStatsManager;
import org.zawamod.zawa.world.item.ZawaItems;

import java.util.function.Predicate;

/**
 * Recovered NeoForge core of the original ZAWA 1.20.1 base animal.
 *
 * The species-specific classes and ZAWA data managers are intentionally kept
 * separate; this class preserves the common synchronized state and lifecycle
 * contract that all concrete animals inherit.
 */
public abstract class ZawaBaseEntity extends TamableAnimal {
    public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(ZawaBaseEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Boolean> GENDER = SynchedEntityData.defineId(ZawaBaseEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> FERTILITY = SynchedEntityData.defineId(ZawaBaseEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> FAVORITE_FOOD = SynchedEntityData.defineId(ZawaBaseEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Boolean> PREGNANT = SynchedEntityData.defineId(ZawaBaseEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> GESTATION_TIMER = SynchedEntityData.defineId(ZawaBaseEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> HUNGER = SynchedEntityData.defineId(ZawaBaseEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> THIRST = SynchedEntityData.defineId(ZawaBaseEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> ENRICHMENT = SynchedEntityData.defineId(ZawaBaseEntity.class, EntityDataSerializers.INT);

    protected static final Predicate<Entity> AVOID_PLAYERS = e -> !e.isSpectator() && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(e);

    private final ZawaEntityStat hunger;
    private final ZawaEntityStat thirst;
    private final ZawaEntityStat enrichment;
    private int starveTimer;
    private int dehydrateTimer;
    private final ListTag children = new ListTag();
    private final Map<Long, Integer> enrichmentCooldowns = new HashMap<>();

    protected ZawaBaseEntity(EntityType<? extends ZawaBaseEntity> type, Level level) {
        super(type, level);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.FENCE, -1.0F);
        int size = getSpeciesSizeOrdinal();
        int multiplier = size * 4;
        this.hunger = new ZawaEntityStat(this, HUNGER, multiplier, ZawaMainConfig.hungerDepletion.get() / Math.max(1, size));
        this.thirst = new ZawaEntityStat(this, THIRST, multiplier, ZawaMainConfig.thirstDepletion.get() / Math.max(1, size));
        this.enrichment = new ZawaEntityStat(this, ENRICHMENT, 20, ZawaMainConfig.enrichmentDepletion.get());
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new ZawaBirthGoal(this));
        goalSelector.addGoal(2, new ZawaBreedGoal(this, 1.0D));
        goalSelector.addGoal(4, new ZawaFollowParentGoal(this, 1.25D));
        goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 10.0F));
        goalSelector.addGoal(5, new SeekFeederGoal(this, 1.1D, 12));
        goalSelector.addGoal(5, new SeekWaterGoal(this, 1.1D, 12));
        goalSelector.addGoal(5, new SeekEnrichmentGoal(this, 1.0D, 12));
        goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT, 0);
        builder.define(GENDER, false);
        builder.define(FERTILITY, 4);
        builder.define(FAVORITE_FOOD, 0);
        builder.define(HUNGER, 0);
        builder.define(THIRST, 0);
        builder.define(ENRICHMENT, 0);
        builder.define(PREGNANT, false);
        builder.define(GESTATION_TIMER, 0);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (VARIANT.equals(key) || GENDER.equals(key)) refreshDimensions();
        super.onSyncedDataUpdated(key);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, net.minecraft.world.DifficultyInstance difficulty,
                                         MobSpawnType reason, SpawnGroupData groupData, CompoundTag tag) {
        setFertility(getSpeciesFertility().getRandomInRange(getRandom()));
        setGender(Gender.fromBool(getRandom().nextFloat() <= getMaleRatio()));
        setFavoriteFood(setupFavoriteFood());

        int variant = getWildVariants() > 0 ? getRandom().nextInt(getWildVariants()) : 0;
        if (this instanceof SpeciesVariantsEntity variants && reason != MobSpawnType.SPAWN_EGG) {
            variant = variants.getVariantByBiome(level);
            if (groupData instanceof SpeciesVariantsEntity.SpeciesVariantData data) variant = data.variant;
            else groupData = new SpeciesVariantsEntity.SpeciesVariantData(variant, 0.2F);
        }
        if (tag != null && tag.contains("Variant")) variant = tag.getInt("Variant");
        setVariant(variant);
        if (groupData == null) groupData = new AgeableMob.AgeableMobGroupData(0.2F);
        return super.finalizeSpawn(level, difficulty, reason, groupData, tag);
    }

    public float getMaleRatio() { return 0.5F; }

    public final int getTotalVariants() { return EntityStatsManager.INSTANCE.getStats(this).getTotalVariants(); }
    public final int getWildVariants() { return getTotalVariants() - getCaptiveVariants(); }
    public final int getCaptiveVariants() { return EntityStatsManager.INSTANCE.getStats(this).getCaptiveVariants(); }

    public int getVariant() { return entityData.get(VARIANT); }
    public void setVariant(int value) { entityData.set(VARIANT, value); }

    public Gender getGender() { return Gender.fromBool(entityData.get(GENDER)); }
    public void setGender(Gender gender) { entityData.set(GENDER, gender.toBool()); }

    public int getFertility() {
        int value = entityData.get(FERTILITY);
        if (value == 0) setFertility(getSpeciesFertility().getRandomInRange(getRandom()));
        return entityData.get(FERTILITY);
    }
    public void setFertility(int value) { entityData.set(FERTILITY, value); }

    public Item getFavoriteFood() {
        Item item = Item.byId(entityData.get(FAVORITE_FOOD));
        if (item == Items.AIR) setFavoriteFood(setupFavoriteFood());
        return Item.byId(entityData.get(FAVORITE_FOOD));
    }
    public void setFavoriteFood(Item item) { entityData.set(FAVORITE_FOOD, Item.getId(item)); }

    public Item setupFavoriteFood() {
        var items = EntityStatsManager.INSTANCE.getStats(this).getDiet().getItemValues().keySet();
        if (items.isEmpty()) return Items.AIR;
        return items.stream().skip(getRandom().nextInt(items.size())).findFirst().orElse(Items.AIR);
    }

    public EntityFertilityCategory getSpeciesFertility() { return EntityStatsManager.INSTANCE.getStats(this).getFertility(); }
    public int getSpeciesSizeOrdinal() { return EntityStatsManager.INSTANCE.getStats(this).getSizeCategory().ordinal(); }

    public boolean isPregnant() { return entityData.get(PREGNANT); }
    public void setPregnant(boolean value) { entityData.set(PREGNANT, value); }
    public int getGestationTimer() { return entityData.get(GESTATION_TIMER); }
    public void setGestationTimer(int value) { entityData.set(GESTATION_TIMER, value); }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            hunger.tick();
            thirst.tick();
            enrichment.tick();
            if (!enrichmentCooldowns.isEmpty() && tickCount % 20 == 0) {
                enrichmentCooldowns.replaceAll((key, value) -> value - 20);
                enrichmentCooldowns.values().removeIf(value -> value <= 0);
            }
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Variant", getVariant());
        tag.putBoolean("Gender", getGender().toBool());
        tag.putInt("Fertility", getFertility());
        tag.putString("FavoriteFood", net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(getFavoriteFood()).toString());
        tag.put("Hunger", hunger.toTag());
        tag.put("Thirst", thirst.toTag());
        tag.put("Enrichment", enrichment.toTag());
        if (getGender() == Gender.FEMALE) {
            tag.putBoolean("Pregnant", isPregnant());
            tag.putInt("Gestation", getGestationTimer());
            if (isPregnant()) tag.put("Children", children.copy());
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Variant")) setVariant(tag.getInt("Variant"));
        if (tag.contains("Gender")) setGender(Gender.fromBool(tag.getBoolean("Gender")));
        if (tag.contains("Fertility")) setFertility(tag.getInt("Fertility"));
        if (tag.contains("FavoriteFood")) {
            Item item = net.minecraft.core.registries.BuiltInRegistries.ITEM.get(ResourceLocation.parse(tag.getString("FavoriteFood")));
            setFavoriteFood(item);
        }
        if (tag.contains("Hunger")) hunger.fromTag(tag.getCompound("Hunger"));
        if (tag.contains("Thirst")) thirst.fromTag(tag.getCompound("Thirst"));
        if (tag.contains("Enrichment")) enrichment.fromTag(tag.getCompound("Enrichment"));
        if (getGender() == Gender.FEMALE) {
            setPregnant(tag.getBoolean("Pregnant"));
            setGestationTimer(tag.getInt("Gestation"));
            if (tag.contains("Children", 9)) {
                children.clear();
                children.addAll(tag.getList("Children", 10));
            }
        }
    }

    @Override
    public void breed(net.minecraft.server.level.ServerLevel level, Animal other) {
        if (!(other instanceof ZawaBaseEntity partner) || !canMate(partner)) return;
        ZawaBaseEntity mother = getGender() == Gender.FEMALE ? this : partner;
        ZawaBaseEntity father = getGender() == Gender.MALE ? this : partner;
        if (mother.getGender() != Gender.FEMALE || !mother.canBreed() || !father.canBreed()) return;
        mother.setPregnant(true);
        mother.setGestationTimer(ZawaMainConfig.gestationTime.get());
        mother.children.clear();
        int litter = EntityStatsManager.INSTANCE.getStats(mother).getLitterSize().sample(mother.getRandom());
        litter = Math.max(1, litter);
        for (int i = 0; i < litter; i++) {
            ZawaBaseEntity child = (ZawaBaseEntity) mother.getBreedOffspring(level, father);
            if (child == null) continue;
            CompoundTag childTag = new CompoundTag();
            child.saveWithoutId(childTag);
            mother.children.add(childTag);
            child.discard();
        }
        mother.setInLove(false);
        father.setInLove(false);
    }

    public void createChild(net.minecraft.server.level.ServerLevel level, ZawaBaseEntity partner) {
        breed(level, partner);
    }

    public void spawnChildrenFromPregnancy(net.minecraft.server.level.ServerLevel level) {
        if (!isPregnant()) return;
        for (int i = 0; i < children.size(); i++) {
            CompoundTag childTag = children.getCompound(i);
            Entity entity = EntityType.create(childTag, level);
            if (entity instanceof ZawaBaseEntity child) {
                child.moveTo(getX(), getY(), getZ(), getYRot(), 0);
                child.setAge(-24000);
                level.addFreshEntity(child);
            }
        }
        children.clear();
        setPregnant(false);
        setGestationTimer(0);
    }

    @Override
    public boolean canMate(Animal other) {
        if (!isTame() || other.getType() != getType() || !(other instanceof ZawaBaseEntity mate)) return false;
        if (mate.getGender() == getGender() || mate.isPregnant() || isPregnant()) return false;
        return mate.isTame() && super.canMate(other);
    }

    @Override
    public boolean canBreed() {
        boolean lowHealth = getHealth() < getMaxHealth();
        boolean lowHunger = hunger.getValue() < hunger.getMax();
        boolean lowThirst = thirst.getValue() < thirst.getMax();
        boolean lowEnrichment = enrichment.getValue() < enrichment.getMax();
        return !isPregnant() && !lowHealth && !lowHunger && !lowThirst && !lowEnrichment && super.canBreed();
    }

    @Override
    public void setInLove(boolean value) {
        super.setInLove(value);
        if (value) setFertility(getSpeciesFertility().getRandomInRange(getRandom()));
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.getItem() == EntityStatsManager.INSTANCE.getStats(this).getBreedingItem();
    }

    public boolean isDietFood(ItemStack stack) {
        return EntityStatsManager.INSTANCE.getStats(this).getDiet().getItemValues().containsKey(stack.getItem());
    }

    public boolean isKibble(ItemStack stack) {
        return stack.getItem() == EntityStatsManager.INSTANCE.getStats(this).getKibble();
    }

    /**
     * Stage 33 interaction pass: connects the resource-driven diet data to the
     * runtime hunger/enrichment systems.  The server remains authoritative;
     * the client only reports a successful interaction for hand animation.
     */
    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (isDietFood(stack) && hunger.getValue() < hunger.getMax()) {
            if (!level().isClientSide) {
                int foodValue = Byte.toUnsignedInt(EntityStatsManager.INSTANCE.getStats(this)
                        .getDiet().getItemValues().getByte(stack.getItem()));
                int favoriteBonus = stack.is(getFavoriteFood()) ? Math.max(1, foodValue / 2) : 0;
                hunger.increment(Math.max(1, foodValue + favoriteBonus));
                heal(Math.max(1.0F, foodValue * 0.25F));
                if (!player.getAbilities().instabuild) stack.shrink(1);
            }
            return InteractionResult.sidedSuccess(level().isClientSide);
        }

        if (isKibble(stack)) {
            if (!level().isClientSide) {
                hunger.increment(Math.max(2, hunger.getMax() / 2));
                if (!isTame()) tame(player);
                heal(2.0F);
                if (!player.getAbilities().instabuild) stack.shrink(1);
            }
            return InteractionResult.sidedSuccess(level().isClientSide);
        }

        if (stack.is(ZawaItems.BABY_FORMULA.get()) && isBaby()) {
            if (!level().isClientSide) {
                hunger.increment(Math.max(2, hunger.getMax() / 2));
                heal(2.0F);
                if (!player.getAbilities().instabuild) stack.shrink(1);
            }
            return InteractionResult.sidedSuccess(level().isClientSide);
        }

        if (stack.is(ZawaItems.TARGET_STICK.get()) && enrichment.getValue() < enrichment.getMax()) {
            if (!level().isClientSide) enrichment.increment(2);
            return InteractionResult.sidedSuccess(level().isClientSide);
        }

        return super.mobInteract(player, hand);
    }

    /** Called by feeder/enrichment blocks and future AI goals. */
    public boolean consumeDietItem(ItemStack stack) {
        if (!isDietFood(stack) || hunger.getValue() >= hunger.getMax()) return false;
        int value = Byte.toUnsignedInt(EntityStatsManager.INSTANCE.getStats(this).getDiet().getItemValues().getByte(stack.getItem()));
        hunger.increment(Math.max(1, value));
        return true;
    }

    public void drink(int amount) { thirst.increment(Math.max(1, amount)); }
    public void enrich(int amount) { enrichment.increment(Math.max(1, amount)); }

    public ZawaEntityStat getHunger() { return hunger; }
    public ZawaEntityStat getThirst() { return thirst; }
    public ZawaEntityStat getEnrichment() { return enrichment; }

    public boolean acceptsEnrichmentBlock(String id) {
        var enrichmentData = org.zawamod.zawa.resources.EntityStatsManager.INSTANCE.getStats(this).getEnrichment();
        if (enrichmentData == null) return false;
        var allowed = enrichmentData.get("zawa:blocks");
        if (allowed.isEmpty()) allowed = enrichmentData.get("blocks");
        return allowed.stream().anyMatch(key -> key.toString().equals(id));
    }

    public boolean acceptsEnrichmentEntity(String id) {
        var enrichmentData = org.zawamod.zawa.resources.EntityStatsManager.INSTANCE.getStats(this).getEnrichment();
        if (enrichmentData == null) return false;
        return enrichmentData.get("zawa:entities").stream().anyMatch(key -> key.toString().equals(id));
    }

    public boolean isEnrichmentOnCooldown(BlockPos pos) {
        return enrichmentCooldowns.getOrDefault(pos.asLong(), 0) > 0;
    }

    public void markEnrichmentUsed(BlockPos pos) {
        enrichmentCooldowns.put(pos.asLong(), 20 * 30);
    }

    @Override
    public int getAmbientSoundInterval() { return 80; }

    public static boolean checkLandSpawnRules(EntityType<? extends ZawaBaseEntity> type, ServerLevelAccessor level,
                                                MobSpawnType reason, BlockPos pos, RandomSource random) {
        return Mob.checkMobSpawnRules(type, level, reason, pos, random)
                && level.getRawBrightness(pos, 0) > 8;
    }

    public static boolean checkLandSpawnRulesWithLeaves(EntityType<? extends ZawaBaseEntity> type, ServerLevelAccessor level,
                                                         MobSpawnType reason, BlockPos pos, RandomSource random) {
        BlockState below = level.getBlockState(pos.below());
        return checkLandSpawnRules(type, level, reason, pos, random)
                || below.is(BlockTags.LEAVES);
    }

    public static boolean checkSemiAquaticSpawnRules(EntityType<? extends ZawaBaseEntity> type, ServerLevelAccessor level,
                                                       MobSpawnType reason, BlockPos pos, RandomSource random) {
        return checkLandSpawnRulesWithLeaves(type, level, reason, pos, random)
                || (level.getFluidState(pos).is(FluidTags.WATER) && pos.getY() > 45);
    }

    public static boolean checkAquaticSpawnRules(EntityType<? extends ZawaBaseEntity> type, ServerLevelAccessor level,
                                                  MobSpawnType reason, BlockPos pos, RandomSource random) {
        return pos.getY() > 45 && pos.getY() < level.getSeaLevel()
                && level.getFluidState(pos).is(FluidTags.WATER);
    }

    public static boolean checkFlyingSpawnRules(EntityType<? extends ZawaBaseEntity> type, ServerLevelAccessor level,
                                                 MobSpawnType reason, BlockPos pos, RandomSource random) {
        BlockState below = level.getBlockState(pos.below());
        return checkLandSpawnRulesWithLeaves(type, level, reason, pos, random)
                || below.is(BlockTags.LEAVES) || below.is(Blocks.SNOW_BLOCK);
    }

    public enum Gender {
        FEMALE, MALE;
        public static Gender fromBool(boolean male) { return male ? MALE : FEMALE; }
        public boolean toBool() { return this == MALE; }
    }
}
