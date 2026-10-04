package org.zawamod.zawa.network;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.zawamod.zawa.Zawa;
import org.zawamod.zawa.network.protocol.SyncEntityStatsPayload;
import org.zawamod.zawa.network.protocol.UpdateAnimalNamePayload;

/** NeoForge custom-payload network layer replacing the original Forge SimpleChannel. */
@EventBusSubscriber(modid = Zawa.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public final class ZawaNetworkManager {
    public static final String PROTOCOL_VERSION = "1";
    private ZawaNetworkManager() {}

    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(PROTOCOL_VERSION);
        registrar.playToClient(SyncEntityStatsPayload.TYPE, SyncEntityStatsPayload.STREAM_CODEC, SyncEntityStatsPayload::handle);
        registrar.playToServer(UpdateAnimalNamePayload.TYPE, UpdateAnimalNamePayload.STREAM_CODEC, UpdateAnimalNamePayload::handle);
    }
}
