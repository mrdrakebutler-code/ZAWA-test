package org.zawamod.zawa.network.protocol;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.zawamod.zawa.Zawa;
import org.zawamod.zawa.world.entity.animal.ZawaBaseEntity;
import org.zawamod.zawa.world.item.ZawaItems;

/** Serverbound replacement for the original Forge UpdateAnimalNamePacket. */
public record UpdateAnimalNamePayload(int entityId, String name) implements CustomPacketPayload {
    public static final Type<UpdateAnimalNamePayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Zawa.MOD_ID, "update_animal_name"));
    public static final StreamCodec<RegistryFriendlyByteBuf, UpdateAnimalNamePayload> STREAM_CODEC = StreamCodec.of(UpdateAnimalNamePayload::write, UpdateAnimalNamePayload::read);

    public UpdateAnimalNamePayload(ZawaBaseEntity entity, String name) { this(entity.getId(), name); }

    private static void write(RegistryFriendlyByteBuf buf, UpdateAnimalNamePayload p) {
        buf.writeVarInt(p.entityId);
        buf.writeUtf(p.name, 256);
    }
    private static UpdateAnimalNamePayload read(RegistryFriendlyByteBuf buf) {
        return new UpdateAnimalNamePayload(buf.readVarInt(), buf.readUtf(256));
    }
    @Override public Type<? extends CustomPacketPayload> type() { return TYPE; }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            ItemStack main = player.getMainHandItem();
            ItemStack off = player.getOffhandItem();
            if (main.is(ZawaItems.DATA_BOOK.get()) || off.is(ZawaItems.DATA_BOOK.get())) {
                Entity entity = player.level().getEntity(entityId);
                if (entity instanceof ZawaBaseEntity animal && entity.distanceToSqr(player) <= 64.0D) {
                    String sanitized = name.trim();
                    if (!sanitized.isEmpty()) animal.setCustomName(Component.literal(sanitized));
                }
            }
        });
    }
}
