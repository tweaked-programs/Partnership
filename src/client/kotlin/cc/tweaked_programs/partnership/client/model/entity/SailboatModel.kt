package cc.tweaked_programs.partnership.client.model.entity

import cc.tweaked_programs.partnership.main.MOD_ID
import cc.tweaked_programs.partnership.main.entity.Sailboat
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import net.minecraft.client.model.EntityModel
import net.minecraft.client.model.geom.ModelLayerLocation
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.client.model.geom.PartPose
import net.minecraft.client.model.geom.builders.CubeDeformation
import net.minecraft.client.model.geom.builders.CubeListBuilder
import net.minecraft.client.model.geom.builders.LayerDefinition
import net.minecraft.client.model.geom.builders.MeshDefinition
import net.minecraft.resources.ResourceLocation


class SailboatModel(model: ModelPart) : EntityModel<Sailboat>() {

    private val root: ModelPart = model.getChild("root")

    override fun setupAnim(entity: Sailboat, f: Float, g: Float, h: Float, i: Float, j: Float) {

    }

    override fun renderToBuffer(poseStack: PoseStack, vertexConsumer: VertexConsumer, i: Int, j: Int, f: Float, g: Float, h: Float, k: Float) {
        root.render(poseStack, vertexConsumer, i, j, f, g, h, k)
    }

    companion object {
        val LAYER_LOCATION: ModelLayerLocation = ModelLayerLocation(ResourceLocation(MOD_ID, "sailboat"), "main")

        fun createBodyLayer(): LayerDefinition {
            val meshDefinition = MeshDefinition()
            val partDefinition = meshDefinition.root

            val root = partDefinition.addOrReplaceChild(
                "root",
                CubeListBuilder.create().texOffs(0, 0)
                    .addBox(-9.0f, -6.0f, -18.0f, 18.0f, 2.0f, 36.0f, CubeDeformation(0.0f))
                    .texOffs(0, 0).addBox(-9.0f, -6.0f, -18.0f, 18.0f, 2.0f, 36.0f, CubeDeformation(0.0f))
                    .texOffs(0, 60).addBox(-6.5f, -4.0f, -18.0f, 13.0f, 2.0f, 36.0f, CubeDeformation(0.0f)),
                PartPose.offset(0.0f, 26.0f, 0.0f)
            )

            /*val sail = */root.addOrReplaceChild(
                "sail",
                CubeListBuilder.create().texOffs(54, 46)
                    .addBox(-13.0f, -36.0f, 4.0f, 25.0f, 4.0f, 2.0f, CubeDeformation(0.0f))
                    .texOffs(0, 40).addBox(-13.0f, -32.0f, 2.0f, 25.0f, 18.0f, 2.0f, CubeDeformation(0.0f))
                    .texOffs(54, 40).addBox(-13.0f, -14.0f, 4.0f, 25.0f, 4.0f, 2.0f, CubeDeformation(0.0f))
                    .texOffs(54, 56).addBox(-13.0f, -12.0f, 6.0f, 25.0f, 2.0f, 2.0f, CubeDeformation(0.0f))
                    .texOffs(54, 52).addBox(-13.0f, -36.0f, 6.0f, 25.0f, 2.0f, 2.0f, CubeDeformation(0.0f))
                    .texOffs(0, 0).addBox(-2.0f, -37.0f, 6.0f, 4.0f, 31.0f, 4.0f, CubeDeformation(0.0f)),
                PartPose.offset(0.0f, 0.0f, 0.0f)
            )

            return LayerDefinition.create(meshDefinition, 128, 128)
        }
    }
}