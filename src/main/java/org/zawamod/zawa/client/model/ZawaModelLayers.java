package org.zawamod.zawa.client.model;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import org.zawamod.zawa.Zawa;

/** Model layer identifiers for the reconstructed ZAWA animal model system. */
public final class ZawaModelLayers {
    public static final ModelLayerLocation ANIMAL = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(Zawa.MOD_ID, "animal"), "main");

    private ZawaModelLayers() {}
}
