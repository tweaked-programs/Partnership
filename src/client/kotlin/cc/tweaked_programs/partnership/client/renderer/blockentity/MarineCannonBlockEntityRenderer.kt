package cc.tweaked_programs.partnership.client.renderer.blockentity

import cc.tweaked_programs.partnership.main.block.cannon.MarineCannonBlockEntity
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider

class MarineCannonBlockEntityRenderer(ctx: BlockEntityRendererProvider.Context) : BlockEntityRenderer<MarineCannonBlockEntity> {
    override fun render(
        blockEntity: MarineCannonBlockEntity,
        f: Float,
        poseStack: PoseStack,
        multiBufferSource: MultiBufferSource,
        i: Int,
        j: Int
    ) {
        TODO("Not yet implemented")
    }
}