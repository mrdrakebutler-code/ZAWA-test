package org.zawamod.zawa.network;

import org.zawamod.zawa.network.protocol.UpdateAnimalNamePayload;

/** Client-facing helpers for ZAWA payloads. */
public final class ZawaNetworkSender {
    private ZawaNetworkSender() {}

    public static void updateAnimalName(int entityId, String name) {
        net.neoforged.neoforge.network.PacketDistributor.sendToServer(new UpdateAnimalNamePayload(entityId, name));
    }
}
