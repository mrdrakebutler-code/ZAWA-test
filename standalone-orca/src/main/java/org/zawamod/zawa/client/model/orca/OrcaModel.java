package org.zawamod.zawa.client.model.orca;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import org.zawamod.zawa.world.entity.animal.Orca;
import org.zawamod.zawa.world.entity.animal.ZawaBaseEntity;

/**
 * ZAWA 1.20.1 orca swimming animation reconstructed from the original
 * OrcaModel$AdultMale, $AdultFemale, $Child and ZawaBaseModel bytecode.
 * Geometry is supplied by the original sex/age-specific layer definitions.
 */
public final class OrcaModel extends EntityModel<Orca> {
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart chest;
    private final ModelPart head;
    private final ModelPart tail1;
    private final ModelPart tail2;
    private final ModelPart tail3;
    private final ModelPart tail4;
    private final ModelPart leftPectoral;
    private final ModelPart rightPectoral;

    public OrcaModel(ModelPart root) {
        this.root = root;
        body = root.getChild("Body");
        tail1 = body.getChild("Tail1");
        tail2 = tail1.getChild("Tail2");
        tail3 = tail2.getChild("Tail3");
        tail4 = tail3.getChild("Tail4");
        chest = body.getChild("Chest");
        head = chest.getChild("Head");
        leftPectoral = chest.getChild("PectoralFinLeft1");
        rightPectoral = chest.getChild("PectoralFinRight1");
    }

    @Override
    public void setupAnim(Orca entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        root.getAllParts().forEach(ModelPart::resetPose);

        // The original base-model movement pass runs while the animal swims.
        if (entity.isInWater()) {
            float wave = Mth.cos(limbSwing * 0.3F + Mth.PI);
            float strength = limbSwingAmount * 0.5F;
            body.xRot = wave * 0.5F * strength;
            tail1.xRot = Mth.cos(1.0F + limbSwing * 0.3F + Mth.PI) * -0.9F * strength - 0.1F;
            tail2.xRot = wave * -0.5F * strength;
            chest.xRot = Mth.cos(6.0F + limbSwing * 0.3F + Mth.PI) * -0.5F * strength + 0.15F;
            tail3.xRot = wave * -1.0F * strength - 0.2F;
            tail4.xRot = Mth.cos(5.0F + limbSwing * 0.3F + Mth.PI) * -2.0F * strength;
            rightPectoral.yRot = wave * 0.05F * strength + 0.1F;
            leftPectoral.yRot = wave * 0.05F * strength - 0.1F;

            if (entity.isPassenger() && entity.getVehicle() instanceof net.minecraft.world.entity.player.Player) {
                root.getAllParts().forEach(ModelPart::resetPose);
                float idlePhase = entity.tickCount * 0.2F + Mth.PI;
                float idleStrength = 0.3F * 0.5F;
                body.xRot = Mth.cos(idlePhase) * 0.1F * idleStrength;
                tail1.xRot = Mth.cos(1.0F + idlePhase) * -0.2F * idleStrength - 0.1F;
                tail2.xRot = Mth.cos(idlePhase) * -0.1F * idleStrength;
                chest.xRot = Mth.cos(1.0F + idlePhase) * -0.1F * idleStrength + 0.15F;
                tail3.xRot = Mth.cos(idlePhase) * -0.6F * idleStrength - 0.2F;
                tail4.xRot = Mth.cos(5.0F + idlePhase) * -0.4F * idleStrength;
            }
        }

        // Subclass look rotations override the base body's swim pitch.
        body.yRot = netHeadYaw * Mth.DEG_TO_RAD * 0.25F;
        body.xRot = headPitch * Mth.DEG_TO_RAD;
        head.yRot = netHeadYaw * Mth.DEG_TO_RAD * 0.25F;
        float headOffset = !entity.isBaby() && entity.getGender() == ZawaBaseEntity.Gender.MALE
                ? 0.1F : 0.03F;
        head.xRot = headPitch * Mth.DEG_TO_RAD * 0.25F - headOffset;
        if (entity.getDeltaMovement().lengthSqr() > 1.0E-7D) {
            body.xRot += Mth.cos(limbSwing * 0.3F + Mth.PI) * 0.5F * limbSwingAmount * 0.5F;
        }
        // Tail5 is not directly animated in the recovered original formulas.
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer consumer,
                               int packedLight, int packedOverlay, int color) {
        root.render(poseStack, consumer, packedLight, packedOverlay, color);
    }
}
