package org.zawamod.zawa.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.zawamod.zawa.client.model.ZawaModelLayers;
import org.zawamod.zawa.client.model.orca.OrcaModel;
import org.zawamod.zawa.world.entity.animal.Orca;
import org.zawamod.zawa.world.entity.animal.ZawaBaseEntity;

/**
 * Selects original sex- and age-specific geometry for each rendered orca.
 * All three reconstructed layer definitions are registered by ZawaClient.
 */
public final class OrcaRenderer extends MobRenderer<Orca, OrcaModel> {
    private final OrcaModel male;
    private final OrcaModel female;
    private final OrcaModel calf;

    public OrcaRenderer(EntityRendererProvider.Context context) {
        super(context, new OrcaModel(context.bakeLayer(ZawaModelLayers.ORCA_ADULT_MALE)), 0.8F);
        male = this.model;
        female = new OrcaModel(context.bakeLayer(ZawaModelLayers.ORCA_ADULT_FEMALE));
        calf = new OrcaModel(context.bakeLayer(ZawaModelLayers.ORCA_CHILD));
    }

    @Override
    public void render(Orca entity, float entityYaw, float partialTicks, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight) {
        this.model = entity.isBaby() ? calf :
                entity.getGender() == ZawaBaseEntity.Gender.FEMALE ? female : male;
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    protected void scale(Orca entity, PoseStack poseStack, float partialTick) {
        float size = entity.isBaby() ? 0.6F : 0.8F;
        poseStack.scale(size, size, size);
        poseStack.translate(0.0D, -0.3D, -0.3D);
    }

    @Override
    public ResourceLocation getTextureLocation(Orca entity) {
        if (entity.isBaby()) return ResourceLocation.fromNamespaceAndPath("zawa", "textures/entity/orca/orca_baby.png");
        int variant = Math.floorMod(entity.getVariant(), 4) + 1;
        String sex = entity.getGender() == ZawaBaseEntity.Gender.FEMALE ? "female" : "male";
        return ResourceLocation.fromNamespaceAndPath("zawa", "textures/entity/orca/orca_" + variant + "_" + sex + ".png");
    }
}
