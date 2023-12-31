package cc.tweaked_programs.partnership.client.renderer.entity

import cc.tweaked_programs.partnership.client.model.entity.LifebuoyModel
import cc.tweaked_programs.partnership.main.MOD_ID
import cc.tweaked_programs.partnership.main.entity.Lifebuoy
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.Mth
import org.joml.Quaternionf

class LifebuoyRenderer(ctx: EntityRendererProvider.Context) : EntityRenderer<Lifebuoy>(ctx) {

    private val model: LifebuoyModel = LifebuoyModel(ctx.bakeLayer(LifebuoyModel.LAYER_LOCATION))

    override fun render(lifebuoy: Lifebuoy, f: Float, g: Float, poseStack: PoseStack, multiBufferSource: MultiBufferSource,
                        i: Int) {

        poseStack.pushPose()

        model.setupAnim(lifebuoy, g, 0.0f, -0.1f, 0.0f, 0.0f)

        val h: Float = lifebuoy.hurtTime.toFloat() - g
        var j: Float = lifebuoy.damage - g
        if (j < 0.0f) j = 0.0f

        if (h > 0.0f)
            poseStack.mulPose(Axis.XP.rotationDegrees(Mth.sin(h) * h * j / 10.0f * lifebuoy.hurtDir.toFloat()))
        if (!Mth.equal(lifebuoy.getBubbleAngle(g), 0.0f))
            poseStack.mulPose(Quaternionf().setAngleAxis(lifebuoy.getBubbleAngle(g) * (Math.PI.toFloat() / 180), 1.0f, 0.0f, 1.0f))

        poseStack.translate(0.0f, 1.49f, 0.0f)
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0f-(lifebuoy.controllingPassenger?.yRot ?: 0F)))

        poseStack.scale(-1.0f, -1.0f, 1.0f)

        val vertexConsumer = multiBufferSource.getBuffer(model.renderType(getTextureLocation(lifebuoy)))
        model.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f)

        poseStack.popPose()
        super.render(lifebuoy, f, g, poseStack, multiBufferSource, i)
    }

    override fun getTextureLocation(entity: Lifebuoy): ResourceLocation = TEXTURE

    companion object {
        val TEXTURE: ResourceLocation = ResourceLocation(MOD_ID, "textures/entity/lifebuoy/body.png")
    }
}