package cc.tweaked_programs.partnership.client.renderer.entity

import cc.tweaked_programs.partnership.client.model.KayakModel
import cc.tweaked_programs.partnership.main.MOD_ID
import cc.tweaked_programs.partnership.main.entity.Kayak
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.Mth
import org.joml.Quaternionf

class KayakRenderer(ctx: EntityRendererProvider.Context) : EntityRenderer<Kayak>(ctx) {

    private val model: KayakModel = KayakModel(ctx.bakeLayer(KayakModel.LAYER_LOCATION))

    override fun render(kayak: Kayak, f: Float, g: Float, poseStack: PoseStack, multiBufferSource: MultiBufferSource,
                        i: Int) {

        poseStack.pushPose()

        model.setupAnim(kayak, g, 0.0f, -0.1f, 0.0f, 0.0f)

        val h: Float = kayak.hurtTime.toFloat() - g
        var j: Float = kayak.damage - g
        if (j < 0.0f) j = 0.0f

        if (h > 0.0f)
            poseStack.mulPose(Axis.XP.rotationDegrees(Mth.sin(h) * h * j / 10.0f * kayak.hurtDir.toFloat()))
        if (!Mth.equal(kayak.getBubbleAngle(g), 0.0f))
            poseStack.mulPose(Quaternionf().setAngleAxis(kayak.getBubbleAngle(g) * (Math.PI.toFloat() / 180), 1.0f, 0.0f, 1.0f))

        poseStack.translate(0.0f, 1.49f, 0.0f)
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0f-f))

        poseStack.scale(-1.0f, -1.0f, 1.0f)

        val vertexConsumer = multiBufferSource.getBuffer(model.renderType(getTextureLocation(kayak)))
        model.advancedRenderToBuffer(kayak, poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f)

        if (!kayak.isUnderWater) {
            val vertexConsumer2 = multiBufferSource.getBuffer(RenderType.waterMask())
            model.waterPatch().render(poseStack, vertexConsumer2, i, OverlayTexture.NO_OVERLAY)
        }

        poseStack.popPose()
        super.render(kayak, f, g, poseStack, multiBufferSource, i)
    }

    override fun getTextureLocation(entity: Kayak): ResourceLocation = TEXTURE

    companion object {
        val TEXTURE: ResourceLocation = ResourceLocation(MOD_ID, "textures/entity/kayak/body.png")
    }
}