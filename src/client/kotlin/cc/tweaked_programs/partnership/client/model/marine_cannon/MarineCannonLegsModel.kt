package cc.tweaked_programs.partnership.client.model.marine_cannon

import cc.tweaked_programs.partnership.main.MOD_ID
import cc.tweaked_programs.partnership.main.block.cannon.MarineCannonBlockEntity
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import net.minecraft.client.model.Model
import net.minecraft.client.model.geom.ModelLayerLocation
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.client.model.geom.PartPose
import net.minecraft.client.model.geom.builders.CubeDeformation
import net.minecraft.client.model.geom.builders.CubeListBuilder
import net.minecraft.client.model.geom.builders.LayerDefinition
import net.minecraft.client.model.geom.builders.MeshDefinition
import net.minecraft.client.renderer.RenderType
import net.minecraft.core.Direction
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import java.util.function.Function
import kotlin.math.PI


class MarineCannonLegsModel(root: ModelPart) :
    Model(Function { resourceLocation: ResourceLocation -> RenderType.entityTranslucent(resourceLocation) }) {

    private val root: ModelPart = root.getChild("root")

    fun setupAnim(entity: MarineCannonBlockEntity, partialTicks: Float) {
        root.setRotation(0F, (when (entity.state.getValue(BlockStateProperties.HORIZONTAL_FACING)) {
            Direction.NORTH -> 0F
            Direction.EAST -> 270F
            Direction.SOUTH -> 180F
            Direction.WEST -> 90F
            else -> 0F
        } + entity.getXRot()) * MAGIKK, 0F)
    }

    override fun renderToBuffer(poseStack: PoseStack, vertexConsumer: VertexConsumer, packedLight:Int,
                                packedOverlay: Int, red: Float, green: Float, blue: Float, alpha: Float) {

        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha)
    }

    companion object {
        val LAYER_LOCATION: ModelLayerLocation = ModelLayerLocation(ResourceLocation(MOD_ID, "marine_cannon"), "main")

        val TEXTURE = ResourceLocation(MOD_ID, "textures/entity/marine_cannon/legs.png")

        private const val MAGIKK: Float = (PI.toFloat() / 180f)

        fun createBodyLayer(): LayerDefinition {
            val meshDefinition = MeshDefinition()
            val partDefinition = meshDefinition.root

            val root = partDefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0f, 23.0f, 0.0f))

            /*val cube_r1 = */root.addOrReplaceChild(
                "cube_r1",
                CubeListBuilder.create().texOffs(0, 0)
                    .addBox(3.0f, -6.2866f, -4.6194f, 2.0f, 8.2f, 2.0f, CubeDeformation(0.0f))
                    .texOffs(0, 0).addBox(-5.0f, -6.2866f, -4.6194f, 2.0f, 8.2f, 2.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, -0.3927f, 0.0f, 0.0f)
            )

            /*val cube_r2 = */root.addOrReplaceChild(
                "cube_r2",
                CubeListBuilder.create().texOffs(0, 0)
                    .addBox(3.0f, -6.2866f, 2.6194f, 2.0f, 8.2f, 2.0f, CubeDeformation(0.0f))
                    .texOffs(0, 0).addBox(-5.0f, -6.2866f, 2.6194f, 2.0f, 8.2f, 2.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0.3927f, 0.0f, 0.0f)
            )

            return LayerDefinition.create(meshDefinition, 16, 16)
        }
    }
}