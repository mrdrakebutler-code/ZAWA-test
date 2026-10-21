package org.zawamod.zawa.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import org.zawamod.zawa.Zawa;
import org.zawamod.zawa.client.model.ZawaAnimalModel;
import org.zawamod.zawa.client.model.ZawaModelLayers;
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
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(org.zawamod.zawa.world.entity.item.ZawaEnrichmentEntities.BOOMER_BALL.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx));
        event.registerEntityRenderer(org.zawamod.zawa.world.entity.item.ZawaEnrichmentEntities.SCENTED_BALL.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx));
        event.registerEntityRenderer(org.zawamod.zawa.world.entity.item.ZawaEnrichmentEntities.APPLE_ICE_TREAT.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx));
        event.registerEntityRenderer(org.zawamod.zawa.world.entity.item.ZawaEnrichmentEntities.BEEF_ICE_TREAT.get(), ctx -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(ctx));
        register(event, ZawaEntities.AFRICAN_LION, "african_lion", .5F); register(event, ZawaEntities.AFRICAN_WILD_DOG, "african_wild_dog", .4F); register(event, ZawaEntities.ASIAN_ELEPHANT, "asian_elephant", 1.0F); register(event, ZawaEntities.BALD_EAGLE, "bald_eagle", .35F); register(event, ZawaEntities.BLACK_FOOTED_FERRET, "black_footed_ferret", .3F); register(event, ZawaEntities.COMMON_CHIMPANZEE, "common_chimpanzee", .45F); register(event, ZawaEntities.COQUERELS_SIFAKA, "coquerels_sifaka", .35F); register(event, ZawaEntities.EMPEROR_PENGUIN, "emperor_penguin", .35F); register(event, ZawaEntities.FLAMINGO, "flamingo", .35F); register(event, ZawaEntities.SUMATRAN_ORANGUTAN, "sumatran_orangutan", .5F); register(event, ZawaEntities.TREE_FROG, "tree_frog", .2F); register(event, ZawaEntities.WESTERN_LOWLAND_GORILLA, "western_lowland_gorilla", .6F); register(event, ZawaEntities.GIANT_ANTEATER, "giant_anteater", .5F); register(event, ZawaEntities.GIANT_PANDA, "giant_panda", .55F); register(event, ZawaEntities.GIRAFFE, "giraffe", .6F); register(event, ZawaEntities.GREVYS_ZEBRA, "grevys_zebra", .5F); register(event, ZawaEntities.HORNBILL, "hornbill", .3F); register(event, ZawaEntities.INDIAN_GHARIAL, "indian_gharial", .5F); register(event, ZawaEntities.KAKAPO, "kakapo", .3F); register(event, ZawaEntities.KOALA, "koala", .35F); register(event, ZawaEntities.MACAW, "macaw", .3F); register(event, ZawaEntities.MANDRILL, "mandrill", .4F); register(event, ZawaEntities.ORCA, "orca", .8F); register(event, ZawaEntities.POLAR_BEAR, "polar_bear", .6F); register(event, ZawaEntities.RED_KANGAROO, "red_kangaroo", .5F); register(event, ZawaEntities.RED_PANDA, "red_panda", .35F); register(event, ZawaEntities.RING_TAILED_LEMUR, "ring_tailed_lemur", .35F); register(event, ZawaEntities.SLOTH, "sloth", .35F); register(event, ZawaEntities.SNOW_LEOPARD, "snow_leopard", .5F); register(event, ZawaEntities.SPIDER_MONKEY, "spider_monkey", .4F); register(event, ZawaEntities.AFRICAN_LAKE_CICHLID, "african_lake_cichlid", .2F); register(event, ZawaEntities.ANGELFISH, "angelfish", .25F); register(event, ZawaEntities.BETTA, "betta", .2F); register(event, ZawaEntities.CLOWNFISH, "clownfish", .25F); register(event, ZawaEntities.COD, "cod", .25F); register(event, ZawaEntities.CORYDORAS, "corydoras", .2F); register(event, ZawaEntities.GRAMMA, "gramma", .2F); register(event, ZawaEntities.PLECOSTOMUS, "plecostomus", .35F); register(event, ZawaEntities.SALMON, "salmon", .25F); register(event, ZawaEntities.BUTTERFLY, "butterfly", .15F); register(event, ZawaEntities.BROWN_RAT, "brown_rat", .25F); register(event, ZawaEntities.HONEY_BEE, "honey_bee", .15F); register(event, ZawaEntities.LEAFCUTTER_ANT, "leafcutter_ant", .15F); register(event, ZawaEntities.PRAYING_MANTIS, "praying_mantis", .15F); register(event, ZawaEntities.SCORPION, "scorpion", .2F); register(event, ZawaEntities.TARANTULA, "tarantula", .2F);
    }

    @SubscribeEvent public static void registerMenuScreens(RegisterMenuScreensEvent event) { event.register(ZawaMenuTypes.FEEDER_BLOCK.get(), ZawaMachineScreen::new); event.register(ZawaMenuTypes.INCUBATOR.get(), ZawaMachineScreen::new); event.register(ZawaMenuTypes.BUG_BOX.get(), ZawaMachineScreen::new); event.register(ZawaMenuTypes.HYDROPONICS_TABLE.get(), ZawaMachineScreen::new); event.register(ZawaMenuTypes.MUSSEL_BOX.get(), ZawaMachineScreen::new); }

    private static <T extends net.minecraft.world.entity.Mob> void register(EntityRenderersEvent.RegisterRenderers event, net.neoforged.neoforge.registries.DeferredHolder<net.minecraft.world.entity.EntityType<?>, net.minecraft.world.entity.EntityType<T>> holder, String species, float shadow) { event.registerEntityRenderer(holder.get(), ctx -> new ZawaAnimalRenderer<>(ctx, species, shadow)); }
}
