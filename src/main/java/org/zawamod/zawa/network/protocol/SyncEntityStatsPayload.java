package org.zawamod.zawa.network.protocol;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.zawamod.zawa.Zawa;
import org.zawamod.zawa.resources.EntityStatsManager;
import org.zawamod.zawa.world.entity.stats.*;

import java.util.*;

/** Clientbound replacement for the original Forge SyncEntityStatsPacket. */
public record SyncEntityStatsPayload(Map<EntityType<?>, EntityStats> stats) implements CustomPacketPayload {
    public static final Type<SyncEntityStatsPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Zawa.MOD_ID, "sync_entity_stats"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SyncEntityStatsPayload> STREAM_CODEC =
            StreamCodec.of(SyncEntityStatsPayload::write, SyncEntityStatsPayload::read);

    private static void write(RegistryFriendlyByteBuf buf, SyncEntityStatsPayload payload) {
        buf.writeVarInt(payload.stats.size());
        for (var entry : payload.stats.entrySet()) {
            EntityStats s = entry.getValue();
            buf.writeResourceLocation(BuiltInRegistries.ENTITY_TYPE.getKey(entry.getKey()));
            buf.writeVarInt(s.getTemperament().ordinal());
            writeItem(buf, s.getKibble());
            buf.writeVarInt(s.getSizeCategory().ordinal());
            writeOptionalEnum(buf, s.getFertility());
            writeItem(buf, s.getBreedingItem());
            writeOptional(buf, s.getLitterSize(), (b, v) -> { b.writeVarInt(v.minInclusive()); b.writeVarInt(v.maxInclusive()); });
            writeOptional(buf, s.getDiet(), EntityDiet::write);
            writeOptional(buf, s.getSpeed(), (b, v) -> b.writeVarInt(v.ordinal()));
            writeOptional(buf, s.getEnrichment(), EntityEnrichment::write);
            buf.writeVarInt(s.getTotalVariants());
            List<String> captive = s.getCaptiveVariantsList();
            buf.writeVarInt(captive.size());
            for (String value : captive) buf.writeUtf(value);
        }
    }

    private static SyncEntityStatsPayload read(RegistryFriendlyByteBuf buf) {
        int count = buf.readVarInt();
        Map<EntityType<?>, EntityStats> result = new HashMap<>(count);
        for (int i = 0; i < count; i++) {
            ResourceLocation id = buf.readResourceLocation();
            EntityType<?> type = BuiltInRegistries.ENTITY_TYPE.get(id);
            EntityTemperamentCategory temperament = enumAt(EntityTemperamentCategory.values(), buf.readVarInt());
            Item kibble = readItem(buf);
            EntitySizeCategory size = enumAt(EntitySizeCategory.values(), buf.readVarInt());
            EntityFertilityCategory fertility = readOptional(buf, b -> enumAt(EntityFertilityCategory.values(), b.readVarInt()));
            Item breeding = readItem(buf);
            UniformInt litter = readOptional(buf, b -> UniformInt.of(b.readVarInt(), b.readVarInt()));
            EntityDiet diet = readOptional(buf, EntityDiet::read);
            EntitySpeedCategory speed = readOptional(buf, b -> enumAt(EntitySpeedCategory.values(), b.readVarInt()));
            EntityEnrichment enrichment = readOptional(buf, EntityEnrichment::read);
            int variants = buf.readVarInt();
            int captiveCount = buf.readVarInt();
            List<String> captive = new ArrayList<>(captiveCount);
            for (int j = 0; j < captiveCount; j++) captive.add(buf.readUtf());
            if (type != null) result.put(type, new EntityStats(temperament, kibble, size, fertility, breeding, litter, diet, speed, enrichment, variants, captive));
        }
        return new SyncEntityStatsPayload(result);
    }

    private static void writeItem(RegistryFriendlyByteBuf buf, Item item) {
        buf.writeResourceLocation(BuiltInRegistries.ITEM.getKey(item));
    }
    private static Item readItem(RegistryFriendlyByteBuf buf) {
        return BuiltInRegistries.ITEM.get(buf.readResourceLocation());
    }
    private static <T> void writeOptional(RegistryFriendlyByteBuf buf, T value, java.util.function.BiConsumer<RegistryFriendlyByteBuf, T> writer) {
        buf.writeBoolean(value != null);
        if (value != null) writer.accept(buf, value);
    }
    private static <T> T readOptional(RegistryFriendlyByteBuf buf, java.util.function.Function<RegistryFriendlyByteBuf, T> reader) {
        return buf.readBoolean() ? reader.apply(buf) : null;
    }
    private static <E extends Enum<E>> void writeOptionalEnum(RegistryFriendlyByteBuf buf, E value) {
        buf.writeBoolean(value != null);
        if (value != null) buf.writeVarInt(value.ordinal());
    }
    private static <E> E enumAt(E[] values, int ordinal) {
        if (ordinal < 0 || ordinal >= values.length) return values[0];
        return values[ordinal];
    }

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> EntityStatsManager.INSTANCE.syncStats(stats));
    }
}
