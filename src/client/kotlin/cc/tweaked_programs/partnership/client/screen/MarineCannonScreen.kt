package cc.tweaked_programs.partnership.client.screen

import cc.tweaked_programs.partnership.main.block.cannon.MarineCannonBlockEntity
import cc.tweaked_programs.partnership.main.menu.MarineCannonMenu
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.gui.screens.inventory.MenuAccess
import net.minecraft.core.BlockPos
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory
import kotlin.math.roundToInt


class MarineCannonScreen(private val menu: MarineCannonMenu, inventory: Inventory, title: Component) : Screen(title), MenuAccess<MarineCannonMenu> {

    private fun renderLabels(guiGraphics: GuiGraphics) {
        val message = Component.translatable("menu.partnership.marine_cannon.escape")
        val rotate = Component.translatable("menu.partnership.marine_cannon.rotate")

        guiGraphics.drawString(this.font, rotate, (guiGraphics.guiWidth()/2)-(this.font.width(rotate)/2), guiGraphics.guiHeight()/2+this.font.lineHeight, 0xFFFFFF, true)
        guiGraphics.drawString(this.font, message, (guiGraphics.guiWidth()/2)-(this.font.width(message)/2), guiGraphics.guiHeight()/2+this.font.lineHeight*2+1, 0xAAAAAA, true)
    }

    override fun renderBackground(guiGraphics: GuiGraphics, i: Int, j: Int, f: Float) { }

    override fun render(guiGraphics: GuiGraphics, i: Int, j: Int, f: Float) {
        super.render(guiGraphics, i, j, f)
        renderLabels(guiGraphics)
    }

    override fun isPauseScreen(): Boolean = false

    override fun mouseDragged(mouseX: Double, mouseY: Double, button: Int, dirX: Double, dirY: Double): Boolean {
        getBlockEntity()?.rotate((dirX * 2F).roundToInt().toFloat(), (dirY * 2F).roundToInt().toFloat())
        return super.mouseDragged(mouseX, mouseY, button, dirX, dirY)
    }

    override fun mouseReleased(d: Double, e: Double, i: Int): Boolean {
        val blockEntity = getBlockEntity()

        if (blockEntity != null) {
            ClientPlayNetworking.send(
                MarineCannonBlockEntity.SYNC_ROT_NETWORK_ID,
                packet(menu.pos, blockEntity.getXRot(), blockEntity.getYRot())
            )
        }

        return super.mouseReleased(d, e, i)
    }

    private fun getBlockEntity(): MarineCannonBlockEntity? {
        val player = Minecraft.getInstance().player
        if (player?.level() == null)
            return null

        val blockEntity = player.level().getBlockEntity(menu.pos)
        if (blockEntity is MarineCannonBlockEntity)
            return blockEntity

        return null
    }

    companion object {
        fun packet(pos: BlockPos, xRot: Float, yRot: Float): FriendlyByteBuf {
            val buf = PacketByteBufs.create()

            buf.writeBlockPos(pos)
            buf.writeFloat(xRot)
            buf.writeFloat(yRot)

            return buf
        }
    }

    override fun getMenu(): MarineCannonMenu = menu
}