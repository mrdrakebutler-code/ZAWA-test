package org.zawamod.zawa.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

/**
 * Reconstructed ZAWA animal model scaffold.  It uses the original resource
 * textures and species-specific proportions while keeping one stable baked
 * layer until the original hand-authored layer definitions are fully ported.
 */
public class ZawaAnimalModel<T extends Entity> extends EntityModel<T> {
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart neck;
    private final ModelPart head;
    private final ModelPart snout;
    private final ModelPart legFL;
    private final ModelPart legFR;
    private final ModelPart legBL;
    private final ModelPart legBR;
    private final ModelPart tail;
    private final ModelPart wingL;
    private final ModelPart wingR;
    private final ModelPart finL;
    private final ModelPart finR;
    private final String species;
    private final Shape shape;

    public ZawaAnimalModel(ModelPart root, String species) {
        this.root = root;
        this.body = root.getChild("body");
        this.neck = root.getChild("neck");
        this.head = root.getChild("head");
        this.snout = root.getChild("snout");
        this.legFL = root.getChild("leg_fl");
        this.legFR = root.getChild("leg_fr");
        this.legBL = root.getChild("leg_bl");
        this.legBR = root.getChild("leg_br");
        this.tail = root.getChild("tail");
        this.wingL = root.getChild("wing_l");
        this.wingR = root.getChild("wing_r");
        this.finL = root.getChild("fin_l");
        this.finR = root.getChild("fin_r");
        this.species = species;
        this.shape = Shape.forSpecies(species);
        applyShape();
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        CubeListBuilder empty = CubeListBuilder.create();
        root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-4, -3, -6, 8, 6, 12), PartPose.offset(0, 10, 0));
        root.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(0, 18)
                .addBox(-2.5F, -4, -2.5F, 5, 8, 5), PartPose.offset(0, 5, -5));
        root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(20, 18)
                .addBox(-3, -3, -3, 6, 6, 6), PartPose.offset(0, 3, -8));
        root.addOrReplaceChild("snout", CubeListBuilder.create().texOffs(44, 18)
                .addBox(-2, -2, -3, 4, 4, 4), PartPose.offset(0, 4, -11));
        root.addOrReplaceChild("leg_fl", CubeListBuilder.create().texOffs(0, 34)
                .addBox(-1.5F, 0, -1.5F, 3, 8, 3), PartPose.offset(-3, 11, -4));
        root.addOrReplaceChild("leg_fr", CubeListBuilder.create().texOffs(12, 34)
                .addBox(-1.5F, 0, -1.5F, 3, 8, 3), PartPose.offset(3, 11, -4));
        root.addOrReplaceChild("leg_bl", CubeListBuilder.create().texOffs(24, 34)
                .addBox(-1.5F, 0, -1.5F, 3, 8, 3), PartPose.offset(-3, 11, 4));
        root.addOrReplaceChild("leg_br", CubeListBuilder.create().texOffs(36, 34)
                .addBox(-1.5F, 0, -1.5F, 3, 8, 3), PartPose.offset(3, 11, 4));
        root.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(48, 34)
                .addBox(-1.5F, -1.5F, 0, 3, 3, 10), PartPose.offset(0, 8, 5));
        root.addOrReplaceChild("wing_l", CubeListBuilder.create().texOffs(0, 48)
                .addBox(0, -2, -4, 1, 4, 8), PartPose.offset(3, 8, 0));
        root.addOrReplaceChild("wing_r", CubeListBuilder.create().texOffs(0, 48)
                .addBox(-1, -2, -4, 1, 4, 8), PartPose.offset(-3, 8, 0));
        root.addOrReplaceChild("fin_l", empty, PartPose.offset(4, 9, 0));
        root.addOrReplaceChild("fin_r", empty, PartPose.offset(-4, 9, 0));
        return LayerDefinition.create(mesh, 64, 64);
    }

    private void applyShape() {
        body.xScale = shape.bodyX; body.yScale = shape.bodyY; body.zScale = shape.bodyZ;
        neck.xScale = shape.neckX; neck.yScale = shape.neckY; neck.zScale = shape.neckZ;
        head.xScale = shape.headX; head.yScale = shape.headY; head.zScale = shape.headZ;
        snout.xScale = shape.snoutX; snout.yScale = shape.snoutY; snout.zScale = shape.snoutZ;
        for (ModelPart p : new ModelPart[]{legFL, legFR, legBL, legBR}) {
            p.xScale = shape.legX; p.yScale = shape.legY; p.zScale = shape.legZ;
        }
        tail.xScale = shape.tailX; tail.yScale = shape.tailY; tail.zScale = shape.tailZ;
        wingL.visible = shape.wings; wingR.visible = shape.wings;
        finL.visible = shape.fins; finR.visible = shape.fins;
        legFL.visible = shape.legs; legFR.visible = shape.legs; legBL.visible = shape.legs; legBR.visible = shape.legs;
        snout.visible = shape.snout;
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!(entity instanceof net.minecraft.world.entity.LivingEntity living)) return;
        ZawaAnimationController.animate(living, species, root, body, neck, head, snout, legFL, legFR, legBL, legBR,
                tail, wingL, wingR, finL, finR, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight,
                               int packedOverlay, int color) {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    private record Shape(float bodyX, float bodyY, float bodyZ, float neckX, float neckY, float neckZ,
                         float headX, float headY, float headZ, float snoutX, float snoutY, float snoutZ,
                         float legX, float legY, float legZ, float tailX, float tailY, float tailZ,
                         boolean legs, boolean wings, boolean fins, boolean snout) {
        static Shape forSpecies(String id) {
            return switch (id) {
                case "giraffe" -> new Shape(1.15F, 1.0F, 1.15F, 0.75F, 2.7F, 0.75F, 0.9F, 0.9F, 1.1F, 0.8F, 0.7F, 1.5F, 0.8F, 1.0F, 0.8F, 0.8F, 0.8F, 1.4F, true,false,false,true);
                case "giant_anteater" -> new Shape(1.4F, 1.0F, 1.35F, 0.8F, 1.0F, 0.8F, 0.8F, 0.9F, 1.0F, 0.45F, 0.45F, 2.5F, 0.8F, 1.1F, 0.8F, 1.1F, 1.0F, 1.8F, true,false,false,true);
                case "giant_panda" -> new Shape(1.45F, 1.25F, 1.35F, 1.0F, 0.9F, 1.0F, 1.25F, 1.15F, 1.15F, 0.9F, 0.8F, 0.9F, 1.15F, 1.0F, 1.15F, 0.7F, 0.8F, 0.7F, true,false,false,true);
                case "orca" -> new Shape(2.1F, 0.9F, 1.8F, 0.7F, 0.7F, 0.8F, 1.5F, 0.8F, 1.4F, 1.4F, 0.5F, 1.5F, 0,0,0, 0.7F,0.7F,1.8F, false,false,true,true);
                case "indian_gharial" -> new Shape(2.2F, 0.65F, 2.8F, 0.45F,0.5F,0.5F,0.8F,0.55F,1.4F,0.45F,0.4F,3.0F,0,0,0,1.0F,0.6F,2.0F,false,false,true,true);
                case "bald_eagle", "hornbill", "kakapo", "macaw" -> new Shape(0.8F,0.9F,0.9F,0.7F,0.8F,0.7F,0.9F,0.9F,0.9F,0.6F,0.6F,1.0F,0.65F,0.8F,0.65F,0.7F,0.7F,1.0F,true,true,false,true);
                case "emperor_penguin" -> new Shape(1.0F,1.35F,0.85F,0.8F,0.6F,0.8F,0.85F,0.8F,0.8F,0.5F,0.45F,0.8F,0.55F,0.8F,0.55F,0.5F,0.5F,0.7F,true,true,false,true);
                case "flamingo" -> new Shape(0.8F,0.9F,0.9F,0.45F,2.5F,0.45F,0.75F,0.7F,0.75F,0.45F,0.45F,1.5F,0.45F,2.0F,0.45F,0.5F,0.5F,1.0F,true,true,false,true);
                case "tree_frog" -> new Shape(0.9F,0.65F,0.9F,0.5F,0.5F,0.5F,1.1F,0.9F,1.0F,0.5F,0.45F,0.7F,0.5F,0.7F,0.5F,0.5F,0.5F,0.5F,true,false,false,true);
                case "african_lake_cichlid", "angelfish", "betta", "clownfish", "cod", "corydoras", "gramma", "plecostomus", "salmon" -> new Shape(1.7F,0.65F,1.2F,0.4F,0.4F,0.4F,0.9F,0.7F,1.0F,0.6F,0.5F,1.0F,0,0,0,1.3F,0.5F,1.0F,false,false,true,true);
                case "butterfly", "honey_bee" -> new Shape(0.55F,0.45F,0.7F,0.4F,0.4F,0.4F,0.65F,0.55F,0.6F,0.4F,0.4F,0.5F,0,0,0,0.4F,0.4F,0.4F,false,true,false,true);
                case "brown_rat", "black_footed_ferret", "red_panda", "ring_tailed_lemur", "sloth", "spider_monkey", "koala", "snow_leopard", "african_lion", "african_wild_dog", "grevys_zebra", "red_kangaroo", "mandrill", "common_chimpanzee", "coquerels_sifaka", "sumatran_orangutan", "western_lowland_gorilla", "asian_elephant", "polar_bear" -> new Shape(1.25F,1.0F,1.25F,0.8F,0.9F,0.8F,1.0F,0.95F,1.0F,0.75F,0.7F,0.9F,0.9F,1.0F,0.9F,1.0F,0.9F,1.3F,true,false,false,true);
                case "leafcutter_ant", "praying_mantis", "scorpion", "tarantula" -> new Shape(1.0F,0.55F,1.0F,0.3F,0.3F,0.3F,0.7F,0.55F,0.7F,0.4F,0.35F,0.6F,0.45F,0.55F,0.45F,1.0F,0.5F,0.8F,true,false,false,true);
                default -> new Shape(1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,true,false,false,true);
            };
        }
    }
}
