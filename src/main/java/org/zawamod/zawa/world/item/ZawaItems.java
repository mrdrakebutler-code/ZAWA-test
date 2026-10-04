package org.zawamod.zawa.world.item;

import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;
import org.zawamod.zawa.world.block.ZawaBlocks;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;
import org.zawamod.zawa.Zawa;
import org.zawamod.zawa.world.entity.animal.ZawaEntities;

public final class ZawaItems {
    private ZawaItems() {}
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Zawa.MOD_ID);

    public static final DeferredItem<SpawnEggItem> AFRICAN_LION_SPAWN_EGG = ITEMS.registerItem("african_lion_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ZawaEntities.AFRICAN_LION.get())));
    public static final DeferredItem<SpawnEggItem> AFRICAN_WILD_DOG_SPAWN_EGG = ITEMS.registerItem("african_wild_dog_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ZawaEntities.AFRICAN_WILD_DOG.get())));
    public static final DeferredItem<SpawnEggItem> ASIAN_ELEPHANT_SPAWN_EGG = ITEMS.registerItem("asian_elephant_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ZawaEntities.ASIAN_ELEPHANT.get())));
    public static final DeferredItem<SpawnEggItem> BALD_EAGLE_SPAWN_EGG = ITEMS.registerItem("bald_eagle_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ZawaEntities.BALD_EAGLE.get())));
    public static final DeferredItem<SpawnEggItem> BLACK_FOOTED_FERRET_SPAWN_EGG = ITEMS.registerItem("black_footed_ferret_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ZawaEntities.BLACK_FOOTED_FERRET.get())));
    public static final DeferredItem<SpawnEggItem> COMMON_CHIMPANZEE_SPAWN_EGG = ITEMS.registerItem("common_chimpanzee_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ZawaEntities.COMMON_CHIMPANZEE.get())));
    public static final DeferredItem<SpawnEggItem> COQUERELS_SIFAKA_SPAWN_EGG = ITEMS.registerItem("coquerels_sifaka_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ZawaEntities.COQUERELS_SIFAKA.get())));
    public static final DeferredItem<SpawnEggItem> EMPEROR_PENGUIN_SPAWN_EGG = ITEMS.registerItem("emperor_penguin_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ZawaEntities.EMPEROR_PENGUIN.get())));
    public static final DeferredItem<SpawnEggItem> FLAMINGO_SPAWN_EGG = ITEMS.registerItem("flamingo_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ZawaEntities.FLAMINGO.get())));
    public static final DeferredItem<SpawnEggItem> SUMATRAN_ORANGUTAN_SPAWN_EGG = ITEMS.registerItem("sumatran_orangutan_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ZawaEntities.SUMATRAN_ORANGUTAN.get())));
    public static final DeferredItem<SpawnEggItem> TREE_FROG_SPAWN_EGG = ITEMS.registerItem("tree_frog_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ZawaEntities.TREE_FROG.get())));
    public static final DeferredItem<SpawnEggItem> WESTERN_LOWLAND_GORILLA_SPAWN_EGG = ITEMS.registerItem("western_lowland_gorilla_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ZawaEntities.WESTERN_LOWLAND_GORILLA.get())));
    public static final DeferredItem<SpawnEggItem> AFRICAN_LAKE_CICHLID_SPAWN_EGG = ITEMS.registerItem("african_lake_cichlid_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ZawaEntities.AFRICAN_LAKE_CICHLID.get())));
    public static final DeferredItem<SpawnEggItem> ANGELFISH_SPAWN_EGG = ITEMS.registerItem("angelfish_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ZawaEntities.ANGELFISH.get())));
    public static final DeferredItem<SpawnEggItem> BETTA_SPAWN_EGG = ITEMS.registerItem("betta_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ZawaEntities.BETTA.get())));
    public static final DeferredItem<SpawnEggItem> CLOWNFISH_SPAWN_EGG = ITEMS.registerItem("clownfish_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ZawaEntities.CLOWNFISH.get())));
    public static final DeferredItem<SpawnEggItem> COD_SPAWN_EGG = ITEMS.registerItem("cod_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ZawaEntities.COD.get())));
    public static final DeferredItem<SpawnEggItem> CORYDORAS_SPAWN_EGG = ITEMS.registerItem("corydoras_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ZawaEntities.CORYDORAS.get())));
    public static final DeferredItem<SpawnEggItem> GRAMMA_SPAWN_EGG = ITEMS.registerItem("gramma_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ZawaEntities.GRAMMA.get())));
    public static final DeferredItem<SpawnEggItem> PLECOSTOMUS_SPAWN_EGG = ITEMS.registerItem("plecostomus_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ZawaEntities.PLECOSTOMUS.get())));
    public static final DeferredItem<SpawnEggItem> SALMON_SPAWN_EGG = ITEMS.registerItem("salmon_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ZawaEntities.SALMON.get())));
    public static final DeferredItem<SpawnEggItem> BUTTERFLY_SPAWN_EGG = ITEMS.registerItem("butterfly_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ZawaEntities.BUTTERFLY.get())));
    public static final DeferredItem<SpawnEggItem> BROWN_RAT_SPAWN_EGG = ITEMS.registerItem("brown_rat_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ZawaEntities.BROWN_RAT.get())));
    public static final DeferredItem<SpawnEggItem> HONEY_BEE_SPAWN_EGG = ITEMS.registerItem("honey_bee_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ZawaEntities.HONEY_BEE.get())));
    public static final DeferredItem<SpawnEggItem> LEAFCUTTER_ANT_SPAWN_EGG = ITEMS.registerItem("leafcutter_ant_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ZawaEntities.LEAFCUTTER_ANT.get())));
    public static final DeferredItem<SpawnEggItem> PRAYING_MANTIS_SPAWN_EGG = ITEMS.registerItem("praying_mantis_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ZawaEntities.PRAYING_MANTIS.get())));
    public static final DeferredItem<SpawnEggItem> SCORPION_SPAWN_EGG = ITEMS.registerItem("scorpion_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ZawaEntities.SCORPION.get())));
    public static final DeferredItem<SpawnEggItem> TARANTULA_SPAWN_EGG = ITEMS.registerItem("tarantula_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ZawaEntities.TARANTULA.get())));

    public static final DeferredItem<SpawnEggItem> GIANT_ANTEATER_SPAWN_EGG = ITEMS.registerItem("giant_anteater_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ZawaEntities.GIANT_ANTEATER.get())));
    public static final DeferredItem<SpawnEggItem> GIANT_PANDA_SPAWN_EGG = ITEMS.registerItem("giant_panda_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ZawaEntities.GIANT_PANDA.get())));
    public static final DeferredItem<SpawnEggItem> GIRAFFE_SPAWN_EGG = ITEMS.registerItem("giraffe_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ZawaEntities.GIRAFFE.get())));
    public static final DeferredItem<SpawnEggItem> GREVYS_ZEBRA_SPAWN_EGG = ITEMS.registerItem("grevys_zebra_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ZawaEntities.GREVYS_ZEBRA.get())));
    public static final DeferredItem<SpawnEggItem> HORNBILL_SPAWN_EGG = ITEMS.registerItem("hornbill_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ZawaEntities.HORNBILL.get())));
    public static final DeferredItem<SpawnEggItem> INDIAN_GHARIAL_SPAWN_EGG = ITEMS.registerItem("indian_gharial_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ZawaEntities.INDIAN_GHARIAL.get())));
    public static final DeferredItem<SpawnEggItem> KAKAPO_SPAWN_EGG = ITEMS.registerItem("kakapo_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ZawaEntities.KAKAPO.get())));
    public static final DeferredItem<SpawnEggItem> KOALA_SPAWN_EGG = ITEMS.registerItem("koala_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ZawaEntities.KOALA.get())));
    public static final DeferredItem<SpawnEggItem> MACAW_SPAWN_EGG = ITEMS.registerItem("macaw_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ZawaEntities.MACAW.get())));
    public static final DeferredItem<SpawnEggItem> MANDRILL_SPAWN_EGG = ITEMS.registerItem("mandrill_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ZawaEntities.MANDRILL.get())));
    public static final DeferredItem<SpawnEggItem> ORCA_SPAWN_EGG = ITEMS.registerItem("orca_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ZawaEntities.ORCA.get())));
    public static final DeferredItem<SpawnEggItem> POLAR_BEAR_SPAWN_EGG = ITEMS.registerItem("polar_bear_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ZawaEntities.POLAR_BEAR.get())));
    public static final DeferredItem<SpawnEggItem> RED_KANGAROO_SPAWN_EGG = ITEMS.registerItem("red_kangaroo_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ZawaEntities.RED_KANGAROO.get())));
    public static final DeferredItem<SpawnEggItem> RED_PANDA_SPAWN_EGG = ITEMS.registerItem("red_panda_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ZawaEntities.RED_PANDA.get())));
    public static final DeferredItem<SpawnEggItem> RING_TAILED_LEMUR_SPAWN_EGG = ITEMS.registerItem("ring_tailed_lemur_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ZawaEntities.RING_TAILED_LEMUR.get())));
    public static final DeferredItem<SpawnEggItem> SLOTH_SPAWN_EGG = ITEMS.registerItem("sloth_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ZawaEntities.SLOTH.get())));
    public static final DeferredItem<SpawnEggItem> SNOW_LEOPARD_SPAWN_EGG = ITEMS.registerItem("snow_leopard_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ZawaEntities.SNOW_LEOPARD.get())));
    public static final DeferredItem<SpawnEggItem> SPIDER_MONKEY_SPAWN_EGG = ITEMS.registerItem("spider_monkey_spawn_egg", p -> new SpawnEggItem(p.spawnEgg(ZawaEntities.SPIDER_MONKEY.get())));


    public static final DeferredItem<Item> DATA_BOOK = ITEMS.registerItem("data_book", p -> new DataBookItem(p));
    public static final DeferredItem<Item> MOTOR_BOAT = simple("motor_boat");
    public static final DeferredItem<Item> ZOO_CART = simple("zoo_cart");
    public static final DeferredItem<Item> CARNIVORE_KIBBLE = simple("carnivore_kibble");
    public static final DeferredItem<Item> HERBIVORE_KIBBLE = simple("herbivore_kibble");
    public static final DeferredItem<Item> INSECTIVORE_KIBBLE = simple("insectivore_kibble");
    public static final DeferredItem<Item> OMNIVORE_KIBBLE = simple("omnivore_kibble");
    public static final DeferredItem<Item> PISCIVORE_KIBBLE = simple("piscivore_kibble");
    public static final DeferredItem<Item> SHELLFISH_KIBBLE = simple("shellfish_kibble");
    public static final DeferredItem<Item> CAPTURE_CAGE = simple("capture_cage");
    public static final DeferredItem<Item> CAPTURE_NET = ITEMS.registerItem("capture_net", p -> new CaptureNetItem(p));
    public static final DeferredItem<Item> HANDLING_GLOVE = simple("handling_glove");
    public static final DeferredItem<Item> NET_LAUNCHER = simple("net_launcher");
    public static final DeferredItem<Item> RELEASE_FORM = simple("release_form");
    public static final DeferredItem<Item> SEINE_NET = simple("seine_net");
    public static final DeferredItem<Item> SLINGSHOT_NET = ITEMS.registerItem("slingshot_net", SlingshotNetItem::new);
    public static final DeferredItem<Item> TARGET_STICK = simple("target_stick");
    public static final DeferredItem<Item> TRANQUILIZER_GUN = simple("tranquilizer_gun");
    public static final DeferredItem<Item> TRANQUILIZER_DART = simple("tranquilizer_dart");
    public static final DeferredItem<Item> ZOOKEEPER_UNIFORM = simple("zookeeper_uniform");
    public static final DeferredItem<Item> BABY_FORMULA = simple("baby_formula");

    // Stage 35 enrichment objects.
    public static final DeferredItem<Item> BLACK_BAMBOO_FEEDER = blockItem("black_bamboo_feeder", ZawaBlocks.BLACK_BAMBOO_FEEDER);
    public static final DeferredItem<Item> YELLOW_BAMBOO_FEEDER = blockItem("yellow_bamboo_feeder", ZawaBlocks.YELLOW_BAMBOO_FEEDER);
    public static final DeferredItem<Item> BAMBOO_CHIMES = blockItem("bamboo_chimes", ZawaBlocks.BAMBOO_CHIMES);
    public static final DeferredItem<Item> BLACK_BAMBOO_CHIMES = blockItem("black_bamboo_chimes", ZawaBlocks.BLACK_BAMBOO_CHIMES);
    public static final DeferredItem<Item> YELLOW_BAMBOO_CHIMES = blockItem("yellow_bamboo_chimes", ZawaBlocks.YELLOW_BAMBOO_CHIMES);
    public static final DeferredItem<Item> SCRATCHING_POST = blockItem("scratching_post", ZawaBlocks.SCRATCHING_POST);
    public static final DeferredItem<Item> LEAF_BROWSE = blockItem("leaf_browse", ZawaBlocks.LEAF_BROWSE);
    public static final DeferredItem<Item> HAY_BROWSE = blockItem("hay_browse", ZawaBlocks.HAY_BROWSE);
    public static final DeferredItem<Item> SPRINKLER = blockItem("sprinkler", ZawaBlocks.SPRINKLER);
    public static final DeferredItem<Item> PINEAPPLE_PINATA = blockItem("pineapple_pinata", ZawaBlocks.PINEAPPLE_PINATA);
    public static final DeferredItem<Item> PUZZLE_FEEDER = blockItem("puzzle_feeder", ZawaBlocks.PUZZLE_FEEDER);
    public static final DeferredItem<Item> BALL_PIT = blockItem("ball_pit", ZawaBlocks.BALL_PIT);
    public static final DeferredItem<Item> TIRE_SWING = blockItem("tire_swing", ZawaBlocks.TIRE_SWING);
    public static final DeferredItem<Item> CLIMBING_VINE = blockItem("climbing_vine", ZawaBlocks.CLIMBING_VINE);
    public static final DeferredItem<Item> ROPE = blockItem("rope", ZawaBlocks.ROPE);
    public static final DeferredItem<Item> SNOW_PIT = blockItem("snow_pit", ZawaBlocks.SNOW_PIT);
    public static final DeferredItem<Item> SALT_LICK = blockItem("salt_lick", ZawaBlocks.SALT_LICK);
    public static final DeferredItem<Item> PERCHING_STAND = blockItem("perching_stand", ZawaBlocks.PERCHING_STAND);
    public static final DeferredItem<Item> HEAT_LAMP = blockItem("heat_lamp", ZawaBlocks.HEAT_LAMP);
    public static final DeferredItem<Item> HEAT_ROCK = blockItem("heat_rock", ZawaBlocks.HEAT_ROCK);

    public static final DeferredItem<Item> SCENTED_BALL = ITEMS.registerItem("scented_ball", p ->
            new PlaceableEnrichmentItem(p, org.zawamod.zawa.world.entity.item.ZawaEnrichmentEntities.SCENTED_BALL,
                    org.zawamod.zawa.world.entity.item.EnrichmentObjectEntity.Kind.SCENTED_BALL));
    public static final DeferredItem<Item> BOOMER_BALL = ITEMS.registerItem("boomer_ball", p ->
            new PlaceableEnrichmentItem(p, org.zawamod.zawa.world.entity.item.ZawaEnrichmentEntities.BOOMER_BALL,
                    org.zawamod.zawa.world.entity.item.EnrichmentObjectEntity.Kind.BOOMER_BALL));
    public static final DeferredItem<Item> APPLE_ICE_TREAT = ITEMS.registerItem("apple_ice_treat", p ->
            new PlaceableEnrichmentItem(p, org.zawamod.zawa.world.entity.item.ZawaEnrichmentEntities.APPLE_ICE_TREAT,
                    org.zawamod.zawa.world.entity.item.EnrichmentObjectEntity.Kind.APPLE_ICE_TREAT));
    public static final DeferredItem<Item> BEEF_ICE_TREAT = ITEMS.registerItem("beef_ice_treat", p ->
            new PlaceableEnrichmentItem(p, org.zawamod.zawa.world.entity.item.ZawaEnrichmentEntities.BEEF_ICE_TREAT,
                    org.zawamod.zawa.world.entity.item.EnrichmentObjectEntity.Kind.BEEF_ICE_TREAT));


    // Original animal products, foods, kibble ingredients, and habitat plants.
    public static final DeferredItem<Item> BALD_EAGLE_EGG = simple("bald_eagle_egg");
    public static final DeferredItem<Item> EMPEROR_PENGUIN_EGG = simple("emperor_penguin_egg");
    public static final DeferredItem<Item> FLAMINGO_EGG = simple("flamingo_egg");
    public static final DeferredItem<Item> HORNBILL_EGG = simple("hornbill_egg");
    public static final DeferredItem<Item> INDIAN_GHARIAL_EGG = simple("indian_gharial_egg");
    public static final DeferredItem<Item> KAKAPO_EGG = simple("kakapo_egg");
    public static final DeferredItem<Item> MACAW_EGG = simple("macaw_egg");
    public static final DeferredItem<Item> TREE_FROG_FROGSPAWN = simple("tree_frog_frogspawn");

    public static final DeferredItem<Item> AFRICAN_LAKE_CICHLID = simple("african_lake_cichlid");
    public static final DeferredItem<Item> AFRICAN_LAKE_CICHLID_BUCKET = simple("african_lake_cichlid_bucket");
    public static final DeferredItem<Item> ANGELFISH = simple("angelfish");
    public static final DeferredItem<Item> ANGELFISH_BUCKET = simple("angelfish_bucket");
    public static final DeferredItem<Item> BETTA = simple("betta");
    public static final DeferredItem<Item> BETTA_BUCKET = simple("betta_bucket");
    public static final DeferredItem<Item> CLOWNFISH = simple("clownfish");
    public static final DeferredItem<Item> CLOWNFISH_BUCKET = simple("clownfish_bucket");
    public static final DeferredItem<Item> COD_BUCKET = simple("cod_bucket");
    public static final DeferredItem<Item> CORYDORAS = simple("corydoras");
    public static final DeferredItem<Item> CORYDORAS_BUCKET = simple("corydoras_bucket");
    public static final DeferredItem<Item> GRAMMA = simple("gramma");
    public static final DeferredItem<Item> GRAMMA_BUCKET = simple("gramma_bucket");
    public static final DeferredItem<Item> PLECOSTOMUS = simple("plecostomus");
    public static final DeferredItem<Item> PLECOSTOMUS_BUCKET = simple("plecostomus_bucket");
    public static final DeferredItem<Item> SALMON_BUCKET = simple("salmon_bucket");

    public static final DeferredItem<Item> BROWN_RAT = simple("brown_rat");
    public static final DeferredItem<Item> COOKED_BROWN_RAT = simple("cooked_brown_rat");
    public static final DeferredItem<Item> CLAM = simple("clam");
    public static final DeferredItem<Item> COOKED_CLAM = simple("cooked_clam");
    public static final DeferredItem<Item> EARTHWORM = simple("earthworm");
    public static final DeferredItem<Item> HONEY_BEE = simple("honey_bee");
    public static final DeferredItem<Item> LEAFCUTTER_ANT = simple("leafcutter_ant");
    public static final DeferredItem<Item> MEALWORMS = simple("mealworms");
    public static final DeferredItem<Item> LARGE_MEAT = simple("large_meat");
    public static final DeferredItem<Item> COOKED_LARGE_MEAT = simple("cooked_large_meat");
    public static final DeferredItem<Item> MEDIUM_MEAT = simple("medium_meat");
    public static final DeferredItem<Item> COOKED_MEDIUM_MEAT = simple("cooked_medium_meat");
    public static final DeferredItem<Item> SMALL_MEAT = simple("small_meat");
    public static final DeferredItem<Item> COOKED_SMALL_MEAT = simple("cooked_small_meat");
    public static final DeferredItem<Item> MUSSELS = simple("mussels");
    public static final DeferredItem<Item> COOKED_MUSSELS = simple("cooked_mussels");
    public static final DeferredItem<Item> PRICKLY_PEAR = simple("prickly_pear");
    public static final DeferredItem<Item> SHED_SKIN = simple("shed_skin");
    public static final DeferredItem<Item> SHRIMP = simple("shrimp");
    public static final DeferredItem<Item> COOKED_SHRIMP = simple("cooked_shrimp");
    public static final DeferredItem<Item> TERMITES = simple("termites");
    public static final DeferredItem<Item> VENOM_BOTTLE = simple("venom_bottle");
    public static final DeferredItem<Item> BLACK_BAMBOO = simple("black_bamboo");
    public static final DeferredItem<Item> YELLOW_BAMBOO = simple("yellow_bamboo");
    public static final DeferredItem<Item> DUCKWEED = simple("duckweed");
    public static final DeferredItem<Item> EUCALYPTUS = simple("eucalyptus");
    public static final DeferredItem<Item> RED_ROOT_FLOATER = simple("red_root_floater");
    public static final DeferredItem<Item> WATER_HAWTHORNE = simple("water_hawthorne");
    public static final DeferredItem<Item> WATER_HYACINTH = simple("water_hyacinth");
    public static final DeferredItem<Item> WATER_LETTUCE = simple("water_lettuce");

    private static DeferredItem<Item> simple(String name) {
        return ITEMS.registerSimpleItem(name);
    }

    public static void register(net.neoforged.bus.api.IEventBus bus) { ITEMS.register(bus); }
    private static DeferredItem<Item> blockItem(String name, net.neoforged.neoforge.registries.DeferredHolder<net.minecraft.world.level.block.Block, ? extends net.minecraft.world.level.block.Block> block) {
        return ITEMS.registerItem(name, p -> new BlockItem(block.get(), p));
    }

}
