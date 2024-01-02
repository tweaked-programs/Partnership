package cc.tweaked_programs.partnership.client.screen

import cc.tweaked_programs.partnership.main.MOD_ID
import cc.tweaked_programs.partnership.main.item.extendable.GenericBoatItem
import cc.tweaked_programs.partnership.main.menu.BoatyardMenu
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.item.BoatItem
import kotlin.math.max

class BoatyardScreen(
    menu: BoatyardMenu,
    inventory: Inventory,
    title: Component) : AbstractContainerScreen<BoatyardMenu>(menu, inventory, title) {

    companion object {
        val BG_TEXTURE = ResourceLocation(MOD_ID, "textures/gui/container/boatyard.png")
    }

    override fun init() {
        imageWidth = 183
        imageHeight = 178

        titleLabelX = 8
        titleLabelY = 0
        inventoryLabelY = 79

        this.leftPos = (this.width - this.imageWidth) / 2 + 3
        this.topPos = (this.height - this.imageHeight) / 2 + 6
    }

    override fun renderBg(guiGraphics: GuiGraphics, delta: Float, mouseX: Int, mouseY: Int) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader)
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F)
        RenderSystem.setShaderTexture(0, BG_TEXTURE)

        val x = (width-imageWidth)/2+3
        val y = (height-imageHeight)/2

        guiGraphics.blit(BG_TEXTURE, x, y, 0, 0, imageWidth, imageHeight)
    }

    override fun render(guiGraphics: GuiGraphics, i: Int, j: Int, f: Float) {
        super.render(guiGraphics, i, j, f)
        this.renderTooltip(guiGraphics, i, j)
    }

    override fun renderLabels(guiGraphics: GuiGraphics, i: Int, j: Int) {
        guiGraphics.drawString(font, title, titleLabelX, titleLabelY, 0x404040, false)
        guiGraphics.drawString(font, playerInventoryTitle, inventoryLabelX, inventoryLabelY, 0x404040, false)

        val item = menu.getSlot(BoatyardMenu.RESULT_SLOT).item.item
        if (item is BoatItem) {
            var speed = 5; var mobility = 5; var space = 5

            if (item is GenericBoatItem) {
                speed = item.speed
                mobility = item.mobility
                space = item.space
            }

            guiGraphics.drawString(font, Component.literal("⚡ ").withColor(0x16D2FC).append(primitiveProgressBar(speed+1)), imageWidth-30, 23, 0x404040, true)
            guiGraphics.drawString(font, Component.literal("⚓ ").withColor(0xE53D51).append(primitiveProgressBar(mobility+1)), imageWidth-29, 23 + (font.lineHeight+3), 0x404040, true)
            guiGraphics.drawString(font, Component.literal("✉ ").withColor(0x755146).append(primitiveProgressBar(space+1)), imageWidth-27, 23 + ((font.lineHeight+3)*2), 0x404040, true)
        }
    }

    private fun primitiveProgressBar(progress: Int): MutableComponent
        = Component.literal("|".repeat(progress/2)).withColor(0x55BB55)
            .append(if (progress % 2 == 0) Component.literal("|").withColor(0x338833) else Component.literal("|").withColor(0x333333))
            .append(Component.literal("|".repeat(max(0, 5-(progress+1)/2))).withColor(0x333333))
}