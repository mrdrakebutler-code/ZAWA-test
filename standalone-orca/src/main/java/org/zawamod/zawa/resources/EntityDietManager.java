package org.zawamod.zawa.resources;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Item;
import it.unimi.dsi.fastutil.objects.Object2ByteOpenHashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.zawamod.zawa.world.entity.stats.EntityDiet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/** Loads the original ZAWA data/zawa/diets definitions. */
public final class EntityDietManager extends SimpleJsonResourceReloadListener {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final EntityDietManager INSTANCE = new EntityDietManager();
    private final Map<ResourceLocation, EntityDiet> diets = new HashMap<>();

    private EntityDietManager() {
        super(new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create(), "diets");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> resources, ResourceManager manager, ProfilerFiller profiler) {
        diets.clear();
        Map<String, Set<String>> missing = new HashMap<>();
        for (Map.Entry<ResourceLocation, JsonElement> entry : resources.entrySet()) {
            try {
                JsonObject object = GsonHelper.convertToJsonObject(entry.getValue(), "top element");
                Object2ByteOpenHashMap<Item> values = new Object2ByteOpenHashMap<>();
                for (Map.Entry<String, JsonElement> food : object.entrySet()) {
                    String idString = food.getKey();
                    byte value = GsonHelper.convertToByte(food.getValue(), idString);
                    if (idString.startsWith("#")) {
                        ResourceLocation tagId = ResourceLocation.parse(idString.substring(1));
                        TagKey<Item> tag = TagKey.create(Registries.ITEM, tagId);
                        var holders = BuiltInRegistries.ITEM.getTag(tag);
                        if (holders.isPresent()) holders.get().forEach(holder -> values.put(holder.value(), value));
                        else missing.computeIfAbsent(tagId.getNamespace(), k -> new HashSet<>()).add(String.valueOf(tag));
                    } else {
                        ResourceLocation id = ResourceLocation.parse(idString);
                        Item item = BuiltInRegistries.ITEM.get(id);
                        if (item == null || item == net.minecraft.world.item.Items.AIR)
                            missing.computeIfAbsent(id.getNamespace(), k -> new HashSet<>()).add(id.toString());
                        else values.put(item, value);
                    }
                }
                diets.put(entry.getKey(), new EntityDiet(entry.getKey(), values));
            } catch (RuntimeException ex) {
                LOGGER.error("Failed to load ZAWA entity diet {}", entry.getKey(), ex);
            }
        }
        missing.forEach((namespace, values) -> LOGGER.info("ZAWA diet references missing in {}: {}", namespace, String.join(", ", values)));
        LOGGER.info("Loaded {} ZAWA diets", diets.size());
    }

    public EntityDiet getDiet(ResourceLocation id) { return diets.get(id); }
    public Map<ResourceLocation, EntityDiet> getAllDiets() { return Map.copyOf(diets); }
}
