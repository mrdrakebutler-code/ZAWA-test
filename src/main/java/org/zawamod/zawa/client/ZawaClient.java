package org.zawamod.zawa.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import org.zawamod.zawa.Zawa;
import org.zawamod.zawa.client.model.ZawaAnimalModel;
import org.zawamod.zawa.client.model.ZawaModelLayers;
import org.zawamod.zawa.client.model.orca.OrcaAdultMaleLayer;
import org.zawamod.zawa.client.model.orca.OrcaAdultFemaleLayer;
import org.zawamod.zawa.client.model.orca.OrcaChildLayer;
import org.zawamod.zawa.client.renderer.OrcaRenderer;
import org.zawamod.zawa.client.renderer.ZawaAnimalRenderer;
import org.zawamod.zawa.world.entity.animal.ZawaEntities;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import org.zawamod.zawa.world.inventory.ZawaMenuTypes;
import org.zawamod.zawa.client.screen.ZawaMachineScreen;

@EventBusSubscriber(modid = Zawa.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public final class ZawaClient {
    private ZawaClient() {}

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ZawaModelLayers.ANIMAL, ZawaAnimalModel::createBodyLayer);
        event.registerLayerDefinition(ZawaModelLayers.ORCA_ADULT_MALE, OrcaAdultMaleLayer::createBodyLayer);
        event.registerLayerDefinition(ZawaModelLayers.ORCA_ADULT_FEMALE, OrcaAdultFemaleLayer::createBodyLayer);
        event.registerLayerDefinition(ZawaModelLayers.ORCA_CHILD, OrcaChildLayer::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ZawaEntities.ORCA.get(), OrcaRenderer::new);
    }


    private static <T extends net.minecraft.world.entity.Mob> void register(EntityRenderersEvent.RegisterRenderers event, net.neoforged.neoforge.registries.DeferredHolder<net.minecraft.world.entity.EntityType<?>, net.minecraft.world.entity.EntityType<T>> holder, String species, float shadow) { event.registerEntityRenderer(holder.get(), ctx -> new ZawaAnimalRenderer<>(ctx, species, shadow)); }
}
