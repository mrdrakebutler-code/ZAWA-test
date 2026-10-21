package org.zawamod.zawa.client.model.orca;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import org.zawamod.zawa.world.entity.animal.Orca;

/**
 * Orca-specific animated model. Expects the original ZAWA layer hierarchy
 * (root/Body/Tail1/...). The original precise animation bytecode is still
 * being translated; current tail motion is an interim swimming animation.
 */
public final class OrcaModel extends EntityModel<Orca> {
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart tail1;
    private final ModelPart tail2;
    private final ModelPart tail3;
    private final ModelPart tail4;

    public OrcaModel(ModelPart root) {
        this.root = root;
        this.body = root.getChild("Body");
        this.tail1 = body.getChild("Tail1");
        this.tail2 = tail1.getChild("Tail2");
        this.tail3 = tail2.getChild("Tail3");
        this.tail4 = tail3.getChild("Tail4");
    }

    @Override
    public void setupAnim(Orca entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        root.getAllParts().forEach(ModelPart::resetPose);
        float phase = ageInTicks * 0.22F;
        float amplitude = 0.06F + Mth.clamp(limbSwingAmount, 0.0F, 1.0F) * 0.12F;
        tail1.xRot += Mth.sin(phase) * amplitude;
        tail2.xRot += Mth.sin(phase - 0.45F) * amplitude * 1.4F;
        tail3.xRot += Mth.sin(phase - 0.9F) * amplitude * 1.7F;
        tail4.xRot += Mth.sin(phase - 1.35F) * amplitude * 1.9F;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer consumer,
                               int packedLight, int packedOverlay, int color) {
        root.render(poseStack, consumer, packedLight, packedOverlay, color);
    }
}
