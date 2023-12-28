package cc.tweaked_programs.partnership.client.renderer.armor

import cc.tweaked_programs.partnership.client.model.hat.CaptainsHatModel
import cc.tweaked_programs.partnership.client.model.hat.SailorsHatModel
import cc.tweaked_programs.partnership.main.registries.ItemRegistries
import com.mojang.blaze3d.vertex.PoseStack
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer
import net.minecraft.client.model.HumanoidModel
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack

class HatRenderer : ArmorRenderer {

    override fun render(posestack: PoseStack, multiBufferSource: MultiBufferSource, itemStack: ItemStack,
                        entity: LivingEntity, slot: EquipmentSlot, light: Int, contextModel: HumanoidModel<LivingEntity>) {


        when (itemStack.item) {
            ItemRegistries.CAPTAINS_HAT -> {
                CAPTAINS_HAT.setupAnim(contextModel)
                CAPTAINS_HAT.renderToBuffer(posestack, multiBufferSource.getBuffer(CAPTAINS_HAT.renderType(CaptainsHatModel.TEXTURE)), light, OverlayTexture.NO_OVERLAY, 1F, 1F, 1F, 1F)
            }
            ItemRegistries.SAILORS_HAT -> {
                SAILORS_HAT.setupAnim(contextModel)
                SAILORS_HAT.renderToBuffer(posestack, multiBufferSource.getBuffer(SAILORS_HAT.renderType(SailorsHatModel.TEXTURE)), light, OverlayTexture.NO_OVERLAY, 1F, 1F, 1F, 1F)
            }
        }
    }

    companion object {
        val CAPTAINS_HAT = CaptainsHatModel(CaptainsHatModel.createBodyLayer().bakeRoot())
        val SAILORS_HAT = SailorsHatModel(SailorsHatModel.createBodyLayer().bakeRoot())
    }
}