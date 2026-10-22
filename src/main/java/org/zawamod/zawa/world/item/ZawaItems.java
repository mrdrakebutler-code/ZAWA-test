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


    public static final DeferredItem<SpawnEggItem> ORCA_SPAWN_EGG = ITEMS.registerItem("orca_spawn_egg", p -> new SpawnEggItem(ZawaEntities.ORCA.get(), 0x171C23, 0xF2F3F0, p));


    public static final DeferredItem<Item> DATA_BOOK = ITEMS.registerItem("data_book", p -> new DataBookItem(p));
    public static final DeferredItem<Item> MOTOR_BOAT = simple("motor_boat");
    public static final DeferredItem<Item> ZOO_CART = simple("zoo_cart");
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
