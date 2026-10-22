package org.zawamod.zawa.standalone;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import org.zawamod.zawa.Zawa;
import org.zawamod.zawa.client.model.ZawaModelLayers;
import org.zawamod.zawa.client.model.orca.OrcaAdultMaleLayer;
import org.zawamod.zawa.client.model.orca.OrcaAdultFemaleLayer;
import org.zawamod.zawa.client.model.orca.OrcaChildLayer;
import org.zawamod.zawa.client.renderer.OrcaRenderer;

/**
 * Client registrations for the standalone orca only.
 * Uses the original male, female and calf geometry and existing renderer.
 * The isolated source set must exclude the full ZawaClient subscriber.
 */
@EventBusSubscriber(modid = Zawa.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public final class OrcaOnlyClient {
    private OrcaOnlyClient() {}

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ZawaModelLayers.ORCA_ADULT_MALE, OrcaAdultMaleLayer::createBodyLayer);
        event.registerLayerDefinition(ZawaModelLayers.ORCA_ADULT_FEMALE, OrcaAdultFemaleLayer::createBodyLayer);
        event.registerLayerDefinition(ZawaModelLayers.ORCA_CHILD, OrcaChildLayer::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(OrcaOnlyEntities.ORCA.get(), OrcaRenderer::new);
    }
}
