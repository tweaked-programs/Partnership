package cc.tweaked_programs.partnership.main.menu.inventory

import net.minecraft.world.Container
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.ItemStack

class GenericOutputOnlySlot(
    container: Container,
    i: Int,
    j: Int,
    k: Int
) : Slot(container, i, j, k) {

    override fun mayPlace(itemStack: ItemStack): Boolean {
        return false
    }

    override fun onTake(player: Player, itemStack: ItemStack) {
        this.checkTakeAchievements(itemStack)
        super.onTake(player, itemStack)
    }

    override fun onQuickCraft(itemStack: ItemStack, i: Int) {
        this.checkTakeAchievements(itemStack)
    }

    override fun isFake(): Boolean = true

}