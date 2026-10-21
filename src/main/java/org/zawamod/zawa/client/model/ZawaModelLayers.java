package org.zawamod.zawa.client.model;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import org.zawamod.zawa.Zawa;

/** Model layer identifiers for the reconstructed ZAWA animal model system. */
public final class ZawaModelLayers {
    public static final ModelLayerLocation ANIMAL = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(Zawa.MOD_ID, "animal"), "main");

    public static final ModelLayerLocation ORCA_ADULT_MALE = orcaLayer("orca_adult_male");
    public static final ModelLayerLocation ORCA_ADULT_FEMALE = orcaLayer("orca_adult_female");
    public static final ModelLayerLocation ORCA_CHILD = orcaLayer("orca_child");

    private static ModelLayerLocation orcaLayer(String name) {
        return new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Zawa.MOD_ID, name), "main");
    }

    private ZawaModelLayers() {}
}
