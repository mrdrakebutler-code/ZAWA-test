package org.zawamod.zawa;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import org.zawamod.zawa.resources.EntityDietManager;
import org.zawamod.zawa.resources.EntityStatsManager;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import org.zawamod.zawa.config.ZawaMainConfig;
import org.zawamod.zawa.world.entity.ZawaEntityRegistry;
import org.zawamod.zawa.world.item.ZawaItems;
import org.zawamod.zawa.world.inventory.ZawaMenuTypes;
import org.zawamod.zawa.world.block.ZawaBlocks;
import org.zawamod.zawa.world.block.entity.ZawaBlockEntities;
import org.zawamod.zawa.world.entity.ZawaSpawnPlacements;
import org.zawamod.zawa.world.entity.animal.ZawaEntities;

@Mod(Zawa.MOD_ID)
public final class NeoForgeZawaMod {
    public NeoForgeZawaMod(IEventBus modBus, ModContainer container) {
        container.registerConfig(ModConfig.Type.COMMON, ZawaMainConfig.CONFIG_SPEC);
        NeoForge.EVENT_BUS.addListener(NeoForgeZawaMod::addReloadListeners);

        ZawaEntityRegistry entities = new ZawaEntityRegistry(Zawa.MOD_ID);
        entities.register(modBus);
        ZawaItems.register(modBus);
        ZawaMenuTypes.register(modBus);
        ZawaBlocks.BLOCKS.register(modBus);
        ZawaBlockEntities.BLOCK_ENTITIES.register(modBus);
        modBus.addListener(entities::registerAttributes);
        ZawaEntities.register(modBus);
        org.zawamod.zawa.world.entity.item.ZawaEnrichmentEntities.register(modBus);
        ZawaSpawnPlacements.attach(modBus);
        modBus.addListener((EntityAttributeCreationEvent event) -> {
            event.put(ZawaEntities.AFRICAN_LION.get(), org.zawamod.zawa.world.entity.animal.AfricanLion.registerAfricanLionAttributes().build());
            event.put(ZawaEntities.AFRICAN_WILD_DOG.get(), org.zawamod.zawa.world.entity.animal.AfricanWildDog.registerAfricanWildDogAttributes().build());
            event.put(ZawaEntities.ASIAN_ELEPHANT.get(), org.zawamod.zawa.world.entity.animal.AsianElephant.registerAsianElephantAttributes().build());
            event.put(ZawaEntities.BALD_EAGLE.get(), org.zawamod.zawa.world.entity.animal.BaldEagle.registerBaldEagleAttributes().build());
            event.put(ZawaEntities.BLACK_FOOTED_FERRET.get(), org.zawamod.zawa.world.entity.animal.BlackFootedFerret.registerBlackFootedFerretAttributes().build());
            event.put(ZawaEntities.COMMON_CHIMPANZEE.get(), org.zawamod.zawa.world.entity.animal.CommonChimpanzee.registerCommonChimpanzeeAttributes().build());
            event.put(ZawaEntities.COQUERELS_SIFAKA.get(), org.zawamod.zawa.world.entity.animal.CoquerelsSifaka.registerCoquerelsSifakaAttributes().build());
            event.put(ZawaEntities.EMPEROR_PENGUIN.get(), org.zawamod.zawa.world.entity.animal.EmperorPenguin.registerEmperorPenguinAttributes().build());
            event.put(ZawaEntities.FLAMINGO.get(), org.zawamod.zawa.world.entity.animal.Flamingo.registerFlamingoAttributes().build());
            event.put(ZawaEntities.GIANT_ANTEATER.get(), org.zawamod.zawa.world.entity.animal.GiantAnteater.registerGiantAnteaterAttributes().build());
            event.put(ZawaEntities.GIANT_PANDA.get(), org.zawamod.zawa.world.entity.animal.GiantPanda.registerGiantPandaAttributes().build());
            event.put(ZawaEntities.GIRAFFE.get(), org.zawamod.zawa.world.entity.animal.Giraffe.registerGiraffeAttributes().build());
            event.put(ZawaEntities.GREVYS_ZEBRA.get(), org.zawamod.zawa.world.entity.animal.GrevysZebra.registerZebraAttributes().build());
            event.put(ZawaEntities.HORNBILL.get(), org.zawamod.zawa.world.entity.animal.Hornbill.registerHornbillAttributes().build());
            event.put(ZawaEntities.INDIAN_GHARIAL.get(), org.zawamod.zawa.world.entity.animal.IndianGharial.registerIndianGharialAttributes().build());
            event.put(ZawaEntities.KAKAPO.get(), org.zawamod.zawa.world.entity.animal.Kakapo.registerKakapoAttributes().build());
            event.put(ZawaEntities.KOALA.get(), org.zawamod.zawa.world.entity.animal.Koala.registerKoalaAttributes().build());
            event.put(ZawaEntities.MACAW.get(), org.zawamod.zawa.world.entity.animal.Macaw.registerMacawAttributes().build());
            event.put(ZawaEntities.MANDRILL.get(), org.zawamod.zawa.world.entity.animal.Mandrill.registerMandrillAttributes().build());
            event.put(ZawaEntities.ORCA.get(), org.zawamod.zawa.world.entity.animal.Orca.registerOrcaAttributes().build());
            event.put(ZawaEntities.POLAR_BEAR.get(), org.zawamod.zawa.world.entity.animal.ZawaPolarBear.registerPolarBearAttributes().build());
            event.put(ZawaEntities.RED_KANGAROO.get(), org.zawamod.zawa.world.entity.animal.RedKangaroo.registerRedKangarooAttributes().build());
            event.put(ZawaEntities.RED_PANDA.get(), org.zawamod.zawa.world.entity.animal.RedPanda.registerRedPandaAttributes().build());
            event.put(ZawaEntities.RING_TAILED_LEMUR.get(), org.zawamod.zawa.world.entity.animal.RingTailedLemur.registerRingTailedLemurAttributes().build());
            event.put(ZawaEntities.SLOTH.get(), org.zawamod.zawa.world.entity.animal.Sloth.registerSlothAttributes().build());
            event.put(ZawaEntities.SNOW_LEOPARD.get(), org.zawamod.zawa.world.entity.animal.SnowLeopard.registerSnowLeopardAttributes().build());
            event.put(ZawaEntities.SPIDER_MONKEY.get(), org.zawamod.zawa.world.entity.animal.SpiderMonkey.registerSpiderMonkeyAttributes().build());
            event.put(ZawaEntities.AFRICAN_LAKE_CICHLID.get(), org.zawamod.zawa.world.entity.ambient.AfricanLakeCichlid.registerAfricanLakeCichlidAttributes().build());
            event.put(ZawaEntities.ANGELFISH.get(), org.zawamod.zawa.world.entity.ambient.Angelfish.registerAngelfishAttributes().build());
            event.put(ZawaEntities.BETTA.get(), org.zawamod.zawa.world.entity.ambient.Betta.registerBettaAttributes().build());
            event.put(ZawaEntities.CLOWNFISH.get(), org.zawamod.zawa.world.entity.ambient.Clownfish.registerClownfishAttributes().build());
            event.put(ZawaEntities.COD.get(), org.zawamod.zawa.world.entity.ambient.ZawaCod.registerCodAttributes().build());
            event.put(ZawaEntities.CORYDORAS.get(), org.zawamod.zawa.world.entity.ambient.Corydoras.registerCorydorasAttributes().build());
            event.put(ZawaEntities.GRAMMA.get(), org.zawamod.zawa.world.entity.ambient.Gramma.registerGrammaAttributes().build());
            event.put(ZawaEntities.PLECOSTOMUS.get(), org.zawamod.zawa.world.entity.ambient.Plecostomus.registerPlecostomusAttributes().build());
            event.put(ZawaEntities.SALMON.get(), org.zawamod.zawa.world.entity.ambient.ZawaSalmon.registerSalmonAttributes().build());
            event.put(ZawaEntities.BUTTERFLY.get(), org.zawamod.zawa.world.entity.ambient.Butterfly.registerButterflyAttributes().build());
            event.put(ZawaEntities.BROWN_RAT.get(), org.zawamod.zawa.world.entity.ambient.BrownRat.registerBrownRatAttributes().build());
            event.put(ZawaEntities.HONEY_BEE.get(), org.zawamod.zawa.world.entity.ambient.HoneyBee.registerHoneyBeeAttributes().build());
            event.put(ZawaEntities.LEAFCUTTER_ANT.get(), org.zawamod.zawa.world.entity.ambient.LeafcutterAnt.registerLeafcutterAntAttributes().build());
            event.put(ZawaEntities.PRAYING_MANTIS.get(), org.zawamod.zawa.world.entity.ambient.PrayingMantis.registerPrayingMantisAttributes().build());
            event.put(ZawaEntities.SCORPION.get(), org.zawamod.zawa.world.entity.ambient.Scorpion.registerScorpionAttributes().build());
            event.put(ZawaEntities.TARANTULA.get(), org.zawamod.zawa.world.entity.ambient.Tarantula.registerTarantulaAttributes().build());
        });
    }

    private static void addReloadListeners(AddReloadListenerEvent event) {
        event.addListener(EntityDietManager.INSTANCE);
        event.addListener(EntityStatsManager.INSTANCE);
    }
}
