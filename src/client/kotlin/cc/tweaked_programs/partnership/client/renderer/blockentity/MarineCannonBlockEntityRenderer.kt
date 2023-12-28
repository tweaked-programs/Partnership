package cc.tweaked_programs.partnership.client.renderer.blockentity

import cc.tweaked_programs.partnership.client.model.marine_cannon.MarineCannonLegsModel
import cc.tweaked_programs.partnership.client.model.marine_cannon.MarineCannonPipeModel
import cc.tweaked_programs.partnership.main.block.cannon.MarineCannonBlockEntity
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import com.mojang.math.Axis
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.client.renderer.texture.OverlayTexture

class MarineCannonBlockEntityRenderer(ctx: BlockEntityRendererProvider.Context) : BlockEntityRenderer<MarineCannonBlockEntity> {

    private val legs = MarineCannonLegsModel(MarineCannonLegsModel.createBodyLayer().bakeRoot())
    private val pipe = MarineCannonPipeModel(MarineCannonPipeModel.createBodyLayer().bakeRoot())

    override fun render(blockEntity: MarineCannonBlockEntity, f: Float, poseStack: PoseStack,
                        multiBufferSource: MultiBufferSource, packedLight: Int, j: Int) {

        poseStack.pushPose()

        poseStack.translate(0.5F, 1.49F, 0.5F)
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0f))
        poseStack.scale(-1.0f, -1.0f, 1.0f)

        legs.setupAnim(blockEntity, f)
        pipe.setupAnim(blockEntity, f)

        val legsVertexConsumer: VertexConsumer = multiBufferSource.getBuffer(legs.renderType(MarineCannonLegsModel.TEXTURE))
        legs.renderToBuffer(poseStack, legsVertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f)

        val pipeVertexConsumer: VertexConsumer = multiBufferSource.getBuffer(pipe.renderType(MarineCannonPipeModel.TEXTURE))
        pipe.renderToBuffer(poseStack, pipeVertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f)

        poseStack.popPose()
    }
}