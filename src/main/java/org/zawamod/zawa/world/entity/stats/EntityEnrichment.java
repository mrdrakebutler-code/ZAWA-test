package org.zawamod.zawa.world.entity.stats;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Data-driven enrichment assignments recovered from ZAWA's entity_stats JSON.
 *
 * The original 1.20.1 implementation uses a custom enrichment registry.  The
 * resource representation is preserved here so that the species-stat layer can
 * load before that registry is migrated in a later stage.
 */
public final class EntityEnrichment {
    private final Map<String, Set<ResourceLocation>> values;

    public EntityEnrichment(Map<String, Set<ResourceLocation>> values) {
        Map<String, Set<ResourceLocation>> copy = new LinkedHashMap<>();
        values.forEach((key, value) -> copy.put(key, Collections.unmodifiableSet(new LinkedHashSet<>(value))));
        this.values = Collections.unmodifiableMap(copy);
    }

    public Set<ResourceLocation> get(String typeId) {
        Set<ResourceLocation> direct = values.get(typeId);
        if (direct != null) return direct;
        if (!typeId.contains(":")) {
            direct = values.get("zawa:" + typeId);
            if (direct != null) return direct;
        }
        return Collections.emptySet();
    }

    public Map<String, Set<ResourceLocation>> values() {
        return values;
    }

    /** Returns all enrichment entries that resolve to registered items. */
    public Set<Item> asItems() {
        Set<Item> result = new LinkedHashSet<>();
        for (Map.Entry<String, Set<ResourceLocation>> entry : values.entrySet()) {
            if (entry.getKey().endsWith(":entities") || entry.getKey().equals("entities")) {
                for (ResourceLocation id : entry.getValue()) {
                    Item item = BuiltInRegistries.ITEM.get(id);
                    if (item != null && item != net.minecraft.world.item.Items.AIR) {
                        result.add(item);
                    }
                }
            }
        }
        return result;
    }

    public static EntityEnrichment deserialize(JsonObject object, Map<String, Set<String>> missing) {
        Map<String, Set<ResourceLocation>> values = new LinkedHashMap<>();
        for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
            if (!entry.getValue().isJsonArray()) {
                continue;
            }
            Set<ResourceLocation> ids = new LinkedHashSet<>();
            JsonArray array = entry.getValue().getAsJsonArray();
            for (JsonElement element : array) {
                if (!element.isJsonPrimitive()) continue;
                ResourceLocation id = ResourceLocation.parse(element.getAsString());
                ids.add(id);
            }
            values.put(entry.getKey(), ids);
        }
        return new EntityEnrichment(values);
    }

    public static void write(FriendlyByteBuf buf, EntityEnrichment enrichment) {
        buf.writeVarInt(enrichment.values.size());
        for (Map.Entry<String, Set<ResourceLocation>> entry : enrichment.values.entrySet()) {
            buf.writeUtf(entry.getKey());
            buf.writeVarInt(entry.getValue().size());
            for (ResourceLocation id : entry.getValue()) buf.writeResourceLocation(id);
        }
    }

    public static EntityEnrichment read(FriendlyByteBuf buf) {
        int groups = buf.readVarInt();
        Map<String, Set<ResourceLocation>> values = new LinkedHashMap<>();
        for (int i = 0; i < groups; i++) {
            String key = buf.readUtf();
            int count = buf.readVarInt();
            Set<ResourceLocation> ids = new LinkedHashSet<>();
            for (int j = 0; j < count; j++) ids.add(buf.readResourceLocation());
            values.put(key, ids);
        }
        return new EntityEnrichment(values);
    }
}
