package cc.tweaked_programs.partnership.client.model.marine_cannon

import cc.tweaked_programs.partnership.main.MOD_ID
import cc.tweaked_programs.partnership.main.block.cannon.MarineCannonBlockEntity
import cc.tweaked_programs.partnership.main.block.cannon.MarineCannonBlockEntity.Companion.COUNTDOWN
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
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import java.util.function.Function
import kotlin.math.PI


class MarineCannonPipeModel(root: ModelPart) :
    Model(Function { resourceLocation: ResourceLocation -> RenderType.entityTranslucent(resourceLocation) }) {

    private val pipe: ModelPart = root.getChild("pipe")
    private val string: ModelPart = pipe.getChild("string")

    fun setupAnim(entity: MarineCannonBlockEntity, partialTicks: Float) {
        val rotation = (entity.state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + entity.getXRot()) * MAGIKK
        pipe.setRotation(entity.getYRot() * MAGIKK, rotation, 0F)
        string.visible = entity.isShooting()
        string.z = -2.01F+((3F/COUNTDOWN*(entity.getTimeLeft().toFloat())).toInt())
    }

    override fun renderToBuffer(poseStack: PoseStack, vertexConsumer: VertexConsumer, packedLight: Int,
                                packedOverlay: Int, red: Float, green: Float, blue: Float, alpha: Float) {

        pipe.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha)
    }

    companion object {

        val LAYER_LOCATION: ModelLayerLocation = ModelLayerLocation(ResourceLocation(MOD_ID, "marine_cannon"), "main")

        val TEXTURE = ResourceLocation(MOD_ID, "textures/entity/marine_cannon/pipe.png")

        private const val MAGIKK: Float = (PI.toFloat() / 180f)

        fun createBodyLayer(): LayerDefinition {
            val meshdefinition = MeshDefinition()
            val partdefinition = meshdefinition.root

            val pipe = partdefinition.addOrReplaceChild(
                "pipe",
                CubeListBuilder.create().texOffs(0, 0)
                    .addBox(-1.0f, -1.0f, 3.0f, 2.0f, 2.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(0, 0).addBox(-3.0f, -3.0f, -3.0f, 6.0f, 6.0f, 6.0f, CubeDeformation(0.0f))
                    .texOffs(0, 12).addBox(-2.0f, -2.0f, -9.0f, 4.0f, 4.0f, 6.0f, CubeDeformation(0.0f)),
                PartPose.offset(0.0f, 16.0f, 0.0f)
            )

            /*val string = */pipe.addOrReplaceChild(
                "string",
                CubeListBuilder.create().texOffs(14, 12)
                    .addBox(-0.5f, -0.5f, 4.0f, 1.0f, 1.0f, 3.0f, CubeDeformation(0.0f)),
                PartPose.offset(0.0f, 0.0f, 0.0f)
            )

            return LayerDefinition.create(meshdefinition, 32, 32)
        }
    }
}