package cc.tweaked_programs.partnership.client.renderer.entity

import cc.tweaked_programs.partnership.client.model.entity.SailboatModel
import cc.tweaked_programs.partnership.main.MOD_ID
import cc.tweaked_programs.partnership.main.entity.Sailboat
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.Mth
import org.joml.Quaternionf

class SailboatRenderer(ctx: EntityRendererProvider.Context) : EntityRenderer<Sailboat>(ctx) {

    private val model: SailboatModel = SailboatModel(ctx.bakeLayer(SailboatModel.LAYER_LOCATION))

    override fun render(sailboat: Sailboat, f: Float, g: Float, poseStack: PoseStack, multiBufferSource: MultiBufferSource,
                        i: Int) {

        poseStack.pushPose()

        model.setupAnim(sailboat, g, 0.0f, -0.1f, 0.0f, 0.0f)

        val h: Float = sailboat.hurtTime.toFloat() - g
        var j: Float = sailboat.damage - g
        if (j < 0.0f) j = 0.0f

        if (h > 0.0f)
            poseStack.mulPose(Axis.XP.rotationDegrees(Mth.sin(h) * h * j / 10.0f * sailboat.hurtDir.toFloat()))
        if (!Mth.equal(sailboat.getBubbleAngle(g), 0.0f))
            poseStack.mulPose(Quaternionf().setAngleAxis(sailboat.getBubbleAngle(g) * (Math.PI.toFloat() / 180), 1.0f, 0.0f, 1.0f))

        poseStack.translate(0.0f, 1.49f, 0.0f)
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0f-f))

        poseStack.scale(-1.0f, -1.0f, 1.0f)

        val vertexConsumer = multiBufferSource.getBuffer(model.renderType(getTextureLocation(sailboat)))
        model.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f)

        poseStack.popPose()
        super.render(sailboat, f, g, poseStack, multiBufferSource, i)
    }

    override fun getTextureLocation(entity: Sailboat): ResourceLocation = TEXTURE

    companion object {
        val TEXTURE: ResourceLocation = ResourceLocation(MOD_ID, "textures/entity/sailboat/body.png")
    }
}