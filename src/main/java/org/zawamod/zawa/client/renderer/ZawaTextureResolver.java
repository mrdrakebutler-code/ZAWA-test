package org.zawamod.zawa.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.zawamod.zawa.Zawa;
import org.zawamod.zawa.world.entity.animal.ZawaBaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Resolves ZAWA's numbered, gendered, and baby texture variants at render time.
 * The original resource pack contains several naming conventions, so resolution
 * deliberately tries the most specific texture first and falls back safely.
 */
public final class ZawaTextureResolver {
    private ZawaTextureResolver() {}

    public static ResourceLocation resolveForSpecies(LivingEntity entity, String species) {
        if (!(entity instanceof ZawaBaseEntity zawa)) {
            return find(species, species + "_1.png", species + ".png");
        }
        int variant = Math.max(0, zawa.getVariant()) + 1;
        boolean female = zawa.getGender() == ZawaBaseEntity.Gender.FEMALE;
        String sex = female ? "female" : "male";
        List<String> candidates = new ArrayList<>();
        if (entity.isBaby()) {
            candidates.add(species + "_baby.png");
            candidates.add(species + "_baby_" + variant + ".png");
        }
        candidates.add(species + "_" + variant + "_" + sex + ".png");
        candidates.add(species + "_" + variant + ".png");
        candidates.add(species + "_" + sex + ".png");
        candidates.add(species + "_1_" + sex + ".png");
        candidates.add(species + "_1.png");
        candidates.add(species + ".png");
        return find(species, candidates.toArray(String[]::new));
    }

    private static ResourceLocation find(String species, String... files) {
        Minecraft minecraft = Minecraft.getInstance();
        ResourceLocation fallback = null;
        for (String file : files) {
            ResourceLocation location = ResourceLocation.fromNamespaceAndPath(
                    Zawa.MOD_ID, "textures/entity/" + species + "/" + file);
            if (fallback == null) fallback = location;
            if (minecraft.getResourceManager().getResource(location).isPresent()) return location;
        }
        return fallback;
    }
}
