package cc.tweaked_programs.partnership.client.model

import cc.tweaked_programs.partnership.main.MOD_ID
import cc.tweaked_programs.partnership.main.entity.Kayak
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import net.minecraft.client.model.EntityModel
import net.minecraft.client.model.WaterPatchModel
import net.minecraft.client.model.geom.ModelLayerLocation
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.client.model.geom.PartPose
import net.minecraft.client.model.geom.builders.*
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.animal.Animal
import net.minecraft.world.entity.vehicle.Boat.PADDLE_LEFT
import net.minecraft.world.entity.vehicle.Boat.PADDLE_RIGHT
import kotlin.math.sin


class KayakModel(model: ModelPart) : EntityModel<Kayak>(), WaterPatchModel {

    private var root: ModelPart = model.getChild("root")

    private val paddleOne: ModelPart = model.getChild("paddleOne")
    private val paddleTwo: ModelPart = model.getChild("paddleTwo")
    private val paddleThree: ModelPart = model.getChild("paddleThree")

    /*private val center: ModelPart = root.getChild("center")
    private val corners: ModelPart = root.getChild("corners")
    private val front: ModelPart = root.getChild("front")
    private val back: ModelPart = root.getChild("back")*/

    private var waterPatch: ModelPart = model.getChild("waterPatch")

    override fun renderToBuffer(poseStack: PoseStack, vertexConsumer: VertexConsumer, light: Int, overlay: Int, red: Float,
                                green: Float, blue: Float, alpha: Float) {

        root.render(poseStack, vertexConsumer, light, overlay, red, green, blue, alpha)
        //waterPatch.render(poseStack, vertexConsumer, light, overlay, red, green, blue, alpha)
    }

    fun advancedRenderToBuffer(kayak: Kayak, poseStack: PoseStack, vertexConsumer: VertexConsumer, light: Int,
                               overlay: Int, red: Float, green: Float, blue: Float, alpha: Float) {

        renderToBuffer(poseStack, vertexConsumer, light, overlay, red, green, blue, alpha)

        if (kayak.passengers.size > 0)
            kayak.passengers.withIndex().forEach { (index, entity) ->
                if (entity !is Animal)
                    getPaddle(index).render(poseStack, vertexConsumer, light, overlay, red, green, blue, alpha)
            }
        else getPaddle(0).render(poseStack, vertexConsumer, light, overlay, red, green, blue, alpha)
    }

    override fun setupAnim(kayak: Kayak, f: Float, g: Float, h: Float, i: Float, j: Float) {
        if (kayak.passengers.size > 0) {
            kayak.passengers.withIndex().forEach { (index, entity) ->
                if (entity !is Animal)
                    paddleAnim(kayak, entity, index, f)
            }
        } else paddleAnim(kayak, null, 0, f)
    }

    private fun paddleAnim(kayak: Kayak, entity: Entity?, index: Int, frame: Float) {
        var time: Float = kayak.getRowingTime(PADDLE_LEFT, frame)
        if (time == 0F) time = kayak.getRowingTime(PADDLE_RIGHT, frame)
        time /= 1.6F

        val paddle = getPaddle(index)

        if (kayak.passengers.size == 0) {
            paddle.setPos(6.0F, -20.5F, -1.4F)
            paddle.setRotation(1.7F, 1.4F, -0.1F)
        } else if (entity != null) {
            paddle.setPos(0.0F, 0.0F, 2.5F-kayak.getPassengerZOffset(entity)*18F)
            paddle.setRotation(
                (sin(time) * 0.2F),
                (sin(time) * -0.6F),
                (sin(time) * 0.24F + (if(time != 0F) -0.075F else 0F))
            )
        }
        paddle.y += 23F
    }

    private fun getPaddle(index: Int) = listOf(paddleOne, paddleTwo, paddleThree)[index]

    override fun waterPatch(): ModelPart = waterPatch

    companion object {

        val LAYER_LOCATION: ModelLayerLocation = ModelLayerLocation(ResourceLocation(MOD_ID, "kayak"), "main")

        private fun newPaddle(sub: String, modelPartData: PartDefinition): PartDefinition {
            return modelPartData.addOrReplaceChild(
                "paddle$sub",
                CubeListBuilder.create()
                    .texOffs(38, 34).addBox(-18.0f, -12.0f, -9.0f, 36.0f, 2.0f, 2.0f, CubeDeformation(0.0f))
                    .texOffs(0, 7).addBox(18.0f, -13.0f, -8.5f, 10.0f, 4.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(0, 17).addBox(20.0f, -14.0f, -8.5f, 7.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(0, 19).addBox(20.0f, -9.0f, -8.5f, 7.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(0, 21).addBox(-27.0f, -9.0f, -8.5f, 7.0f, 1.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(0, 12).addBox(-28.0f, -13.0f, -8.5f, 10.0f, 4.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(0, 23).addBox(-27.0f, -14.0f, -8.5f, 7.0f, 1.0f, 1.0f, CubeDeformation(0.0f)),
                PartPose.offset(0.0f, 1.0f, -11.0f)
            )
        }

        fun createBodyLayer(): LayerDefinition {
            val modelData = MeshDefinition()
            val modelPartData = modelData.root

            val root =
                modelPartData.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0f, 24.0f, 0.0f))

            newPaddle("One", modelPartData)
            newPaddle("Two", modelPartData)
            newPaddle("Three", modelPartData)

            /*val waterPatch =*/ modelPartData.addOrReplaceChild(
                "waterPatch",
                CubeListBuilder.create()
                    .texOffs(12, 82).addBox(-6.0f, -9.0f, -15.0f, 12.0f, 5.0f, 30.0f, CubeDeformation(0.0f)),
                PartPose.offset(0.0f, 24.0f, 0.0f)
            )

            val center = root.addOrReplaceChild(
                "center",
                CubeListBuilder.create()
                    .texOffs(38, 43).addBox(6.0f, -9.0f, -15.0f, 4.0f, 9.0f, 30.0f, CubeDeformation(0.0f))
                    .texOffs(0, 34).addBox(-10.0f, -9.0f, -15.0f, 4.0f, 9.0f, 30.0f, CubeDeformation(0.0f))
                    .texOffs(0, 0).addBox(-6.0f, -4.0f, -15.0f, 12.0f, 4.0f, 30.0f, CubeDeformation(0.0f)),
                PartPose.offset(0.0f, 0.0f, 0.0f)
            )

            /*val corners =*/ center.addOrReplaceChild(
                "corners",
                CubeListBuilder.create()
                    .texOffs(0, 34).addBox(-6.0f, -9.0f, 13.0f, 2.0f, 5.0f, 2.0f, CubeDeformation(0.0f))
                    .texOffs(22, 7).addBox(4.0f, -9.0f, 13.0f, 2.0f, 5.0f, 2.0f, CubeDeformation(0.0f))
                    .texOffs(16, 17).addBox(4.0f, -9.0f, -15.0f, 2.0f, 5.0f, 2.0f, CubeDeformation(0.0f))
                    .texOffs(22, 22).addBox(-6.0f, -9.0f, -15.0f, 2.0f, 5.0f, 2.0f, CubeDeformation(0.0f)),
                PartPose.offset(0.0f, 0.0f, 0.0f)
            )

            /*val front =*/ root.addOrReplaceChild(
                "front",
                CubeListBuilder.create()
                    .texOffs(54, 15).addBox(-9.0f, -9.0f, -23.0f, 18.0f, 7.0f, 8.0f, CubeDeformation(0.0f))
                    .texOffs(0, 75).addBox(-7.0f, -9.0f, -30.0f, 14.0f, 5.0f, 7.0f, CubeDeformation(0.0f))
                    .texOffs(0, 0).addBox(-5.0f, -9.0f, -34.0f, 10.0f, 3.0f, 4.0f, CubeDeformation(0.0f)),
                PartPose.offset(0.0f, 0.0f, 0.0f)
            )

            /*val back =*/ root.addOrReplaceChild(
                "back",
                CubeListBuilder.create()
                    .texOffs(54, 0).addBox(-9.0f, -9.0f, -47.0f, 18.0f, 7.0f, 8.0f, CubeDeformation(0.0f))
                    .texOffs(76, 38).addBox(-8.0f, -9.0f, -39.0f, 16.0f, 5.0f, 2.0f, CubeDeformation(0.0f)),
                PartPose.offset(0.0f, 0.0f, 62.0f)
            )
            return LayerDefinition.create(modelData, 128, 128)
        }
    }
}