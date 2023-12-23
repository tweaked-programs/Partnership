package cc.tweaked_programs.partnership.client.screen

import cc.tweaked_programs.partnership.main.MOD_ID
import cc.tweaked_programs.partnership.main.menu.BoatyardMenu
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.player.Inventory

// TODO("Tooltips are not rendered properly")
// TODO("Add helpful infos to 'note'")
class BoatyardContainerScreen(
    menu: BoatyardMenu,
    inventory: Inventory,
    title: Component) : AbstractContainerScreen<BoatyardMenu>(menu, inventory, title) {

    companion object {
        val BG_TEXTURE = ResourceLocation(MOD_ID, "textures/gui/container/boatyard.png")
    }

    override fun init() {
        super.init()

        imageWidth = 183
        imageHeight = 178

        titleLabelX = 8
        titleLabelY -= 6
        inventoryLabelY += 5

        //addWidget()
    }

    override fun renderBg(guiGraphics: GuiGraphics, delta: Float, mouseX: Int, mouseY: Int) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader)
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F)
        RenderSystem.setShaderTexture(0, BG_TEXTURE)

        val x = (width-imageWidth)/2+3
        val y = (height-imageHeight)/2

        guiGraphics.blit(BG_TEXTURE, x, y, 0, 0, imageWidth, imageHeight)
    }

    override fun renderLabels(guiGraphics: GuiGraphics, i: Int, j: Int) {
        guiGraphics.drawString(font, title, titleLabelX, titleLabelY, 0xBFBFBF, false)
        guiGraphics.drawString(font, playerInventoryTitle, inventoryLabelX, inventoryLabelY, 0x404040, false)
    }

    override fun render(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, delta: Float) {
        renderBackground(guiGraphics, mouseX, mouseY, delta)
        super.render(guiGraphics, mouseX, mouseY, delta)
    }
}