package org.zawamod.zawa.resources;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.zawamod.zawa.world.entity.stats.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/** Loads the original ZAWA data/zawa/entity_stats definitions. */
public final class EntityStatsManager extends SimpleJsonResourceReloadListener {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final EntityStatsManager INSTANCE = new EntityStatsManager();
    private Map<EntityType<?>, EntityStats> stats = new HashMap<>();

    private EntityStatsManager() { super(new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create(), "entity_stats"); }

    public static <T extends Enum<T>> T readEnum(JsonObject object, String name, Function<String,T> parser, T[] values) {
        JsonElement element = object.get(name);
        if (element == null) throw new JsonSyntaxException("Missing " + name);
        if (!element.isJsonPrimitive()) throw new JsonSyntaxException(name + " must be a string or integer, was " + GsonHelper.getType(element));
        return element.getAsJsonPrimitive().isNumber() ? values[element.getAsInt()] : parser.apply(element.getAsString().toUpperCase(Locale.ROOT));
    }

    private Item readItem(String value) {
        ResourceLocation id = ResourceLocation.parse(value);
        Item item = BuiltInRegistries.ITEM.get(id);
        if (item == null || item == net.minecraft.world.item.Items.AIR) throw new JsonSyntaxException(id + " is not a valid item");
        return item;
    }

    private List<String> readCaptiveVariants(JsonObject object) {
        JsonArray array = GsonHelper.getAsJsonArray(object, "captive_variants", new JsonArray());
        List<String> result = new ArrayList<>();
        for (JsonElement element : array) result.add(GsonHelper.convertToString(element, "captive variant"));
        return result;
    }

    private UniformInt readLitterSize(JsonObject object) {
        JsonElement element = object.get("litter_size");
        if (element == null) throw new JsonSyntaxException("Missing litter_size, expected to find a JsonObject or Int");
        if (element.isJsonPrimitive()) { int value = element.getAsInt(); return UniformInt.of(value, value); }
        if (element.isJsonObject()) {
            JsonObject range = element.getAsJsonObject();
            return UniformInt.of(GsonHelper.getAsInt(range, "min"), GsonHelper.getAsInt(range, "max"));
        }
        throw new JsonSyntaxException("Expected litter_size to be an object or integer, was " + GsonHelper.getType(element));
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> resources, ResourceManager manager, ProfilerFiller profiler) {
        Map<EntityType<?>, EntityStats> loaded = new HashMap<>();
        for (Map.Entry<ResourceLocation, JsonElement> entry : resources.entrySet()) {
            try {
                JsonObject object = GsonHelper.convertToJsonObject(entry.getValue(), "top element");
                if (object.isEmpty()) continue;
                EntityType<?> type = BuiltInRegistries.ENTITY_TYPE.get(entry.getKey());
                if (type == null) { LOGGER.error("Unknown ZAWA entity type {}", entry.getKey()); continue; }
                EntityTemperamentCategory temperament = readEnum(object, "temperament", EntityTemperamentCategory::valueOf, EntityTemperamentCategory.VALUES);
                EntitySizeCategory size = readEnum(object, "size", EntitySizeCategory::valueOf, EntitySizeCategory.VALUES);
                Item breedingItem = readItem(GsonHelper.getAsString(object, "breeding_item"));
                EntitySpeedCategory speed = readEnum(object, "speed", EntitySpeedCategory::valueOf, EntitySpeedCategory.VALUES);
                int variantCount = GsonHelper.getAsInt(object, "variant_count");
                List<String> captiveVariants = readCaptiveVariants(object);
                if (type.getCategory() != MobCategory.AMBIENT && type.getCategory() != MobCategory.WATER_AMBIENT) {
                    Item kibble = readItem(GsonHelper.getAsString(object, "kibble"));
                    EntityFertilityCategory fertility = readEnum(object, "fertility", EntityFertilityCategory::valueOf, EntityFertilityCategory.VALUES);
                    UniformInt litterSize = readLitterSize(object);
                    EntityDiet diet = EntityDiet.read(GsonHelper.getAsString(object, "diet"));
                    EntityEnrichment enrichment = EntityEnrichment.deserialize(GsonHelper.getAsJsonObject(object, "enrichment"), new HashMap<>());
                    loaded.put(type, new EntityStats(temperament, kibble, size, fertility, breedingItem, litterSize, diet, speed, enrichment, variantCount, captiveVariants));
                } else {
                    loaded.put(type, new EntityStats(temperament, null, size, null, breedingItem, null, null, speed, null, variantCount, captiveVariants));
                }
            } catch (Exception ex) { LOGGER.error("Failed to load ZAWA entity stats {}", entry.getKey(), ex); }
        }
        this.stats = loaded;
        LOGGER.info("Loaded {} ZAWA entity-stat definitions", loaded.size());
    }

    public EntityStats getStats(Entity entity) { return getStats(entity.getType()); }
    public EntityStats getStats(EntityType<?> type) { return stats.get(type); }
    public void syncStats(Map<EntityType<?>, EntityStats> synced) { stats = new HashMap<>(synced); }
    public Map<EntityType<?>, EntityStats> getAllStats() { return Map.copyOf(stats); }
}
