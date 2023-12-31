package cc.tweaked_programs.partnership.client.model.entity

import cc.tweaked_programs.partnership.main.MOD_ID
import cc.tweaked_programs.partnership.main.entity.Lifebuoy
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

class LifebuoyModel(model: ModelPart) : EntityModel<Lifebuoy>() {

    private var body: ModelPart = model.getChild("body")

    override fun setupAnim(entity: Lifebuoy, limbSwing: Float, limbSwingAmount: Float, ageInTicks: Float, netHeadYaw: Float, headPitch: Float) {

    }

    override fun renderToBuffer(poseStack: PoseStack, vertexConsumer: VertexConsumer, packedLight: Int,
                                packedOverlay: Int, red: Float, green: Float, blue: Float, alpha: Float) {

        body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha)
    }

    companion object {
        val LAYER_LOCATION: ModelLayerLocation = ModelLayerLocation(ResourceLocation(MOD_ID, "lifebuoy"), "main")

        fun createBodyLayer(): LayerDefinition {
            val meshDefinition = MeshDefinition()
            val partDefinition = meshDefinition.root

            /*val body = */partDefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(28, 28)
                    .addBox(-5.0f, -8.0f, -15.0f, 10.0f, 8.0f, 8.0f, CubeDeformation(0.0f))
                    .texOffs(28, 10).addBox(-5.0f, -8.0f, 7.0f, 10.0f, 8.0f, 8.0f, CubeDeformation(0.0f))
                    .texOffs(0, 18).addBox(7.0f, -8.0f, -5.0f, 8.0f, 8.0f, 10.0f, CubeDeformation(0.0f))
                    .texOffs(0, 0).addBox(-15.0f, -8.0f, -5.0f, 8.0f, 8.0f, 10.0f, CubeDeformation(0.0f))
                    .texOffs(48, 52).addBox(-13.0f, -8.0f, 5.0f, 8.0f, 8.0f, 8.0f, CubeDeformation(0.0f))
                    .texOffs(0, 52).addBox(5.0f, -8.0f, 5.0f, 8.0f, 8.0f, 8.0f, CubeDeformation(0.0f))
                    .texOffs(0, 36).addBox(-13.0f, -8.0f, -13.0f, 8.0f, 8.0f, 8.0f, CubeDeformation(0.0f))
                    .texOffs(24, 44).addBox(5.0f, -8.0f, -13.0f, 8.0f, 8.0f, 8.0f, CubeDeformation(0.0f)),
                PartPose.offset(0.0f, 24.0f, 0.0f)
            )

            return LayerDefinition.create(meshDefinition, 128, 128)
        }
    }
}