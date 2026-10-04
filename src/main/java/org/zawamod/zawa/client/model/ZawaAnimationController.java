package org.zawamod.zawa.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.FlyingAnimal;
import net.minecraft.world.entity.LivingEntity;
import org.zawamod.zawa.world.entity.ClimbingEntity;

/**
 * Shared animation state reconstruction for ZAWA animals.
 *
 * This keeps animation math out of the model's geometry definition and provides
 * common land, aquatic, flying, climbing and sitting poses.
 */
public final class ZawaAnimationController {
    private ZawaAnimationController() {}

    public static void reset(ModelPart root, ModelPart... parts) {
        root.resetPose();
        for (ModelPart part : parts) part.resetPose();
    }

    public static void animate(
            LivingEntity entity,
            String species,
            ModelPart root,
            ModelPart body,
            ModelPart neck,
            ModelPart head,
            ModelPart snout,
            ModelPart legFL,
            ModelPart legFR,
            ModelPart legBL,
            ModelPart legBR,
            ModelPart tail,
            ModelPart wingL,
            ModelPart wingR,
            ModelPart finL,
            ModelPart finR,
            float limbSwing,
            float limbSwingAmount,
            float ageInTicks,
            float netHeadYaw,
            float headPitch) {

        reset(root, body, neck, head, snout, legFL, legFR, legBL, legBR, tail, wingL, wingR, finL, finR);

        final float headYaw = netHeadYaw * Mth.DEG_TO_RAD;
        final float pitch = headPitch * Mth.DEG_TO_RAD;
        head.yRot = headYaw * 0.55F;
        head.xRot = pitch * 0.55F;
        neck.yRot = headYaw * 0.25F;
        neck.xRot = pitch * 0.35F;

        boolean aquatic = entity.isInWaterOrBubble();
        boolean flying = entity instanceof FlyingAnimal && !entity.onGround();
        boolean sitting = entity.isInSittingPose();
        boolean climbing = entity instanceof ClimbingEntity c && c.isClimbing();
        boolean baby = entity.isBaby();

        if (aquatic) {
            animateSwimming(species, body, head, tail, finL, finR, ageInTicks, limbSwingAmount);
        } else if (flying) {
            animateFlying(species, body, head, wingL, wingR, legFL, legFR, legBL, legBR, ageInTicks);
        } else {
            animateGround(species, legFL, legFR, legBL, legBR, tail, limbSwing, limbSwingAmount, climbing);
        }

        if (sitting) animateSitting(species, body, neck, head, legFL, legFR, legBL, legBR, tail);
        if (climbing) animateClimbing(species, body, legFL, legFR, legBL, legBR, tail, ageInTicks);

        animateSpeciesDetails(species, body, head, snout, tail, wingL, wingR, ageInTicks, limbSwingAmount);

        // ZAWA's young animals use visibly smaller proportions rather than a
        // renderer-wide scale. This keeps the shadow and hitbox independent.
        if (baby) {
            root.xScale = 0.65F;
            root.yScale = 0.65F;
            root.zScale = 0.65F;
            root.y = 8.0F;
        } else {
            root.xScale = root.yScale = root.zScale = 1.0F;
            root.y = 0.0F;
        }
    }

    private static void animateGround(String species, ModelPart fl, ModelPart fr, ModelPart bl, ModelPart br,
                                      ModelPart tail, float swing, float amount, boolean climbing) {
        float speed = switch (species) {
            case "giraffe", "grevys_zebra", "red_kangaroo" -> 0.55F;
            case "sloth", "koala" -> 0.35F;
            case "giant_panda", "polar_bear", "asian_elephant" -> 0.65F;
            default -> 0.75F;
        };
        float scale = Math.min(amount, 1.0F) * (climbing ? 0.35F : 0.8F);
        float walk = Mth.cos(swing * speed) * scale;
        fl.xRot = walk;
        br.xRot = walk;
        fr.xRot = -walk;
        bl.xRot = -walk;
        tail.yRot = Mth.cos(swing * speed * 0.8F + 0.8F) * scale * 0.18F;
        tail.xRot += Mth.sin(swing * speed * 0.4F) * scale * 0.08F;
    }

    private static void animateSwimming(String species, ModelPart body, ModelPart head, ModelPart tail,
                                        ModelPart finL, ModelPart finR, float age, float amount) {
        body.xRot = -0.08F;
        float wave = Mth.cos(age * 0.22F) * (0.10F + amount * 0.12F);
        float tailWave = Mth.cos(age * 0.28F) * (0.25F + amount * 0.25F);
        head.xRot -= 0.05F;
        tail.yRot = tailWave;
        tail.xRot = wave;
        finL.zRot = Mth.cos(age * 0.32F) * 0.18F;
        finR.zRot = -finL.zRot;

        if ("orca".equals(species) || "indian_gharial".equals(species)) {
            body.xRot = -0.18F;
            tail.yRot *= 1.25F;
        }
    }

    private static void animateFlying(String species, ModelPart body, ModelPart head, ModelPart wingL, ModelPart wingR,
                                      ModelPart fl, ModelPart fr, ModelPart bl, ModelPart br, float age) {
        float flapSpeed = species.equals("butterfly") ? 1.35F : 0.65F;
        float flap = Mth.cos(age * flapSpeed) * (species.equals("butterfly") ? 0.85F : 0.45F);
        wingL.zRot = flap;
        wingR.zRot = -flap;
        body.xRot = -0.10F;
        fl.xRot = fr.xRot = bl.xRot = br.xRot = 0.35F;
    }

    private static void animateSitting(String species, ModelPart body, ModelPart neck, ModelPart head,
                                       ModelPart fl, ModelPart fr, ModelPart bl, ModelPart br, ModelPart tail) {
        body.xRot = species.equals("kangaroo") ? -0.15F : 0.18F;
        bl.xRot = br.xRot = -1.05F;
        fl.xRot = fr.xRot = -0.35F;
        tail.xRot += 0.18F;
        if (species.equals("giant_panda") || species.equals("red_panda")) {
            head.xRot -= 0.08F;
            neck.xRot += 0.08F;
        }
    }

    private static void animateClimbing(String species, ModelPart body, ModelPart fl, ModelPart fr,
                                        ModelPart bl, ModelPart br, ModelPart tail, float age) {
        float climb = Mth.cos(age * 0.25F) * 0.45F;
        fl.xRot = climb;
        br.xRot = climb;
        fr.xRot = -climb;
        bl.xRot = -climb;
        body.xRot = -0.22F;
        tail.xRot += 0.25F;
    }

    private static void animateSpeciesDetails(String species, ModelPart body, ModelPart head, ModelPart snout,
                                               ModelPart tail, ModelPart wingL, ModelPart wingR, float age, float amount) {
        switch (species) {
            case "flamingo" -> head.xRot += Mth.sin(age * 0.08F) * 0.04F;
            case "giraffe" -> head.yRot += Mth.sin(age * 0.045F) * 0.035F;
            case "sloth" -> body.xRot += Mth.sin(age * 0.05F) * 0.03F;
            case "red_kangaroo" -> body.xRot += Math.min(amount, 1.0F) * 0.10F;
            case "orca" -> tail.yRot += Mth.sin(age * 0.12F) * 0.04F;
            case "butterfly" -> {
                wingL.yRot = Mth.sin(age * 0.9F) * 0.15F;
                wingR.yRot = -wingL.yRot;
            }
            case "praying_mantis" -> {
                snout.xRot += 0.08F;
                wingL.zRot += 0.12F;
                wingR.zRot -= 0.12F;
            }
            default -> { }
        }
    }
}
