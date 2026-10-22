package org.zawamod.zawa.world.entity.stats;

import com.google.gson.JsonSyntaxException;
import it.unimi.dsi.fastutil.objects.Object2ByteMap;
import it.unimi.dsi.fastutil.objects.Object2ByteOpenHashMap;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.core.registries.BuiltInRegistries;
import org.zawamod.zawa.resources.EntityDietManager;

public class EntityDiet {
    private final ResourceLocation id;
    private final Object2ByteMap<Item> itemValues;
    public EntityDiet(ResourceLocation id, Object2ByteMap<Item> itemValues) { this.id = id; this.itemValues = itemValues; }
    public ResourceLocation getId() { return id; }
    public Object2ByteMap<Item> getItemValues() { return itemValues; }
    public static EntityDiet read(String value) {
        ResourceLocation id = ResourceLocation.parse(value);
        EntityDiet diet = EntityDietManager.INSTANCE.getDiet(id);
        if (diet == null) throw new JsonSyntaxException(String.valueOf(id) + " is not a valid entity diet");
        return diet;
    }
    public static void write(FriendlyByteBuf buf, EntityDiet diet) {
        buf.writeResourceLocation(diet.id);
        buf.writeVarInt(diet.itemValues.size());
        for (Object2ByteMap.Entry<Item> entry : diet.itemValues.object2ByteEntrySet()) {
            buf.writeVarInt(BuiltInRegistries.ITEM.getId(entry.getKey()));
            buf.writeByte(entry.getByteValue());
        }
    }
    public static EntityDiet read(FriendlyByteBuf buf) {
        ResourceLocation id = buf.readResourceLocation();
        int size = buf.readVarInt();
        Object2ByteOpenHashMap<Item> values = new Object2ByteOpenHashMap<>(size);
        for (int i = 0; i < size; i++) {
            values.put(BuiltInRegistries.ITEM.byId(buf.readVarInt()), buf.readByte());
        }
        return new EntityDiet(id, values);
    }
}
