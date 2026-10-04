package org.zawamod.zawa.world.entity;

import java.util.List;

/**
 * Stage 5 recovery manifest. This deliberately records the concrete entity
 * classes found in the original binary before their behavior is translated.
 */
public final class ZawaEntityMigrationPlan {
    private ZawaEntityMigrationPlan() {}

    public record Entry(String id, String className, String superclass, List<String> interfaces) {}

    public static final List<Entry> ANIMALS = List.of(
        new Entry("african_lion", "AfricanLion", "ZawaLandEntity", List.of()),
        new Entry("african_wild_dog", "AfricanWildDog", "ZawaLandEntity", List.of()),
        new Entry("asian_elephant", "AsianElephant", "ZawaLandEntity", List.of()),
        new Entry("bald_eagle", "BaldEagle", "ZawaFlyingEntity", List.of("OviparousEntity")),
        new Entry("black_footed_ferret", "BlackFootedFerret", "ZawaLandEntity", List.of()),
        new Entry("common_chimpanzee", "CommonChimpanzee", "ZawaLandEntity", List.of("ClimbingEntity", "SittingEntity")),
        new Entry("coquerels_sifaka", "CoquerelsSifaka", "ZawaLandEntity", List.of("ClimbingEntity", "SittingEntity")),
        new Entry("emperor_penguin", "EmperorPenguin", "ZawaSemiAquaticEntity", List.of("OviparousEntity")),
        new Entry("flamingo", "Flamingo", "ZawaFlyingEntity", List.of("SpeciesVariantsEntity", "GroupEntity", "OviparousEntity")),
        new Entry("giant_anteater", "GiantAnteater", "ZawaLandEntity", List.of("ThreatenStandEntity")),
        new Entry("giant_panda", "GiantPanda", "ZawaLandEntity", List.of("SittingEntity", "EatsItemsEntity")),
        new Entry("giraffe", "Giraffe", "ZawaLandEntity", List.of("SpeciesVariantsEntity", "GroupEntity")),
        new Entry("grevys_zebra", "GrevysZebra", "ZawaLandEntity", List.of("GroupEntity")),
        new Entry("hornbill", "Hornbill", "ZawaFlyingEntity", List.of("SpeciesVariantsEntity", "OviparousEntity")),
        new Entry("indian_gharial", "IndianGharial", "ZawaSemiAquaticEntity", List.of("OviparousEntity")),
        new Entry("kakapo", "Kakapo", "ZawaLandEntity", List.of("OviparousEntity")),
        new Entry("koala", "Koala", "ZawaLandEntity", List.of("ClimbingEntity", "SittingEntity", "EatsItemsEntity")),
        new Entry("macaw", "Macaw", "ZawaFlyingEntity", List.of("SpeciesVariantsEntity", "OviparousEntity")),
        new Entry("mandrill", "Mandrill", "ZawaLandEntity", List.of("SittingEntity")),
        new Entry("orca", "Orca", "ZawaAquaticEntity", List.of()),
        new Entry("polar_bear", "ZawaPolarBear", "ZawaSemiAquaticEntity", List.of("ThreatenStandEntity")),
        new Entry("red_kangaroo", "RedKangaroo", "ZawaLandEntity", List.of("GroupEntity")),
        new Entry("red_panda", "RedPanda", "ZawaLandEntity", List.of("ClimbingEntity", "SittingEntity", "EatsItemsEntity")),
        new Entry("ring_tailed_lemur", "RingTailedLemur", "ZawaLandEntity", List.of("ClimbingEntity", "SittingEntity")),
        new Entry("sloth", "Sloth", "ZawaLandEntity", List.of("SpeciesVariantsEntity", "ClimbingEntity")),
        new Entry("snow_leopard", "SnowLeopard", "ZawaLandEntity", List.of()),
        new Entry("spider_monkey", "SpiderMonkey", "ZawaLandEntity", List.of("SpeciesVariantsEntity", "ClimbingEntity")),
        new Entry("sumatran_orangutan", "SumatranOrangutan", "ZawaLandEntity", List.of("ClimbingEntity", "SittingEntity")),
        new Entry("tree_frog", "TreeFrog", "ZawaLandEntity", List.of("SpeciesVariantsEntity", "OviparousEntity", "JumpingEntity", "ClimbingEntity")),
        new Entry("western_lowland_gorilla", "WesternLowlandGorilla", "ZawaLandEntity", List.of("ClimbingEntity", "SittingEntity"))
        new Entry("african_lake_cichlid", "AfricanLakeCichlid", "ZawaAmbientFishEntity", List.of("SpeciesVariantsEntity", "GroupEntity")),
        new Entry("angelfish", "Angelfish", "ZawaAmbientFishEntity", List.of("SpeciesVariantsEntity", "GroupEntity")),
        new Entry("betta", "Betta", "ZawaAmbientFishEntity", List.of("SpeciesVariantsEntity")),
        new Entry("butterfly", "Butterfly", "ZawaAmbientFlyingEntity", List.of("SpeciesVariantsEntity")),
        new Entry("brown_rat", "BrownRat", "ZawaAmbientLandEntity", List.of()),
        new Entry("clownfish", "Clownfish", "ZawaAmbientFishEntity", List.of("SpeciesVariantsEntity", "GroupEntity")),
        new Entry("cod", "ZawaCod", "ZawaAmbientFishEntity", List.of("GroupEntity")),
        new Entry("corydoras", "Corydoras", "ZawaAmbientFishEntity", List.of("SpeciesVariantsEntity", "GroupEntity")),
        new Entry("gramma", "Gramma", "ZawaAmbientFishEntity", List.of("SpeciesVariantsEntity", "GroupEntity")),
        new Entry("honey_bee", "HoneyBee", "ZawaAmbientFlyingEntity", List.of()),
        new Entry("leafcutter_ant", "LeafcutterAnt", "ZawaAmbientLandEntity", List.of("ClimbingEntity")),
        new Entry("plecostomus", "Plecostomus", "ZawaAmbientFishEntity", List.of("SpeciesVariantsEntity", "ClimbingEntity")),
        new Entry("praying_mantis", "PrayingMantis", "ZawaAmbientLandEntity", List.of("SpeciesVariantsEntity", "ClimbingEntity")),
        new Entry("salmon", "ZawaSalmon", "ZawaAmbientFishEntity", List.of("GroupEntity")),
        new Entry("scorpion", "Scorpion", "ZawaAmbientLandEntity", List.of("SpeciesVariantsEntity", "VenomousEntity")),
        new Entry("tarantula", "Tarantula", "ZawaAmbientLandEntity", List.of("SpeciesVariantsEntity", "VenomousEntity"))
    );
}
