package org.zawamod.zawa.network;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.zawamod.zawa.Zawa;
import org.zawamod.zawa.network.protocol.SyncEntityStatsPayload;
import org.zawamod.zawa.resources.EntityStatsManager;

/** Server lifecycle hooks for the ZAWA networking layer. */
@EventBusSubscriber(modid = Zawa.MOD_ID)
public final class ZawaNetworkEvents {
    private ZawaNetworkEvents() {}

    @SubscribeEvent
    public static void playerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof net.minecraft.server.level.ServerPlayer player) {
            PacketDistributor.sendToPlayer(player, new SyncEntityStatsPayload(EntityStatsManager.INSTANCE.getAllStats()));
        }
    }
}
