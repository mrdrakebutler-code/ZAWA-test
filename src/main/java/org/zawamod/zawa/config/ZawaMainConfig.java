package org.zawamod.zawa.config;

import net.neoforged.neoforge.common.ModConfigSpec;

/** NeoForge migration of the original ZAWA main configuration values used by animal logic. */
public final class ZawaMainConfig {
    public static final ModConfigSpec CONFIG_SPEC;

    public static final ModConfigSpec.BooleanValue spawnZookeepers;
    public static final ModConfigSpec.BooleanValue nameBabies;
    public static final ModConfigSpec.IntValue babyGrowth;
    public static final ModConfigSpec.IntValue breedingCooldown;
    public static final ModConfigSpec.IntValue gestationTime;
    public static final ModConfigSpec.IntValue venomCooldown;
    public static final ModConfigSpec.BooleanValue hunger;
    public static final ModConfigSpec.IntValue hungerDepletion;
    public static final ModConfigSpec.BooleanValue canStarve;
    public static final ModConfigSpec.BooleanValue thirst;
    public static final ModConfigSpec.IntValue thirstDepletion;
    public static final ModConfigSpec.BooleanValue canDehydrate;
    public static final ModConfigSpec.BooleanValue enrichment;
    public static final ModConfigSpec.IntValue enrichmentDepletion;

    static {
        ModConfigSpec.Builder b = new ModConfigSpec.Builder();
        b.push("Options");
        spawnZookeepers = b.comment("Enable or disable NPC Zookeeper spawns.").define("spawn_zookeepers", true);
        nameBabies = b.comment("Enable automatic naming for baby animals.").define("name_babies", false);
        b.pop();

        b.push("Timers");
        babyGrowth = b.defineInRange("baby_growth", 16000, 1, Integer.MAX_VALUE);
        breedingCooldown = b.defineInRange("breeding_cooldown", 24000, 1, Integer.MAX_VALUE);
        gestationTime = b.defineInRange("gestation_time", 8000, 1, Integer.MAX_VALUE);
        venomCooldown = b.defineInRange("venom_cooldown", 24000, 1, Integer.MAX_VALUE);
        b.pop();

        b.push("Entity Stats");
        b.push("Hunger");
        hunger = b.define("enabled", true);
        canStarve = b.define("can_cause_death", true);
        hungerDepletion = b.defineInRange("depletion_rate", 24000, 1, Integer.MAX_VALUE);
        b.pop();
        b.push("Thirst");
        thirst = b.define("enabled", true);
        canDehydrate = b.define("can_cause_death", true);
        thirstDepletion = b.defineInRange("depletion_rate", 16000, 1, Integer.MAX_VALUE);
        b.pop();
        b.push("Enrichment");
        enrichment = b.define("enabled", true);
        enrichmentDepletion = b.defineInRange("depletion_rate", 4800, 1, Integer.MAX_VALUE);
        b.pop();
        b.pop();

        CONFIG_SPEC = b.build();
    }

    private ZawaMainConfig() {}
}
