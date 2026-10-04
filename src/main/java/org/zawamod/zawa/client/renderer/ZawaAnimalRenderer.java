package org.zawamod.zawa.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.zawamod.zawa.client.model.ZawaAnimalModel;
import org.zawamod.zawa.client.model.ZawaModelLayers;

/** Species-aware renderer using the reconstructed ZAWA animal model. */
public class ZawaAnimalRenderer<T extends LivingEntity> extends MobRenderer<T, ZawaAnimalModel<T>> {
    private final String species;
    private final float shadow;

    public ZawaAnimalRenderer(EntityRendererProvider.Context context, String species, float shadow) {
        super(context, new ZawaAnimalModel<>(context.bakeLayer(ZawaModelLayers.ANIMAL), species), shadow);
        this.species = species;
        this.shadow = shadow;
    }


    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return ZawaTextureResolver.resolveForSpecies(entity, species);
    }
}
