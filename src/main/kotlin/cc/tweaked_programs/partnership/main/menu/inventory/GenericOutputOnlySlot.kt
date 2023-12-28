package cc.tweaked_programs.partnership.main.menu.inventory

import net.minecraft.world.Container
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.ItemStack

class GenericOutputOnlySlot(
    private val player: Player,
    container: Container,
    i: Int,
    j: Int,
    k: Int) : Slot(container, i, j, k) {

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

    override fun checkTakeAchievements(itemStack: ItemStack) {
        /*itemStack.onCraftedBy(this.player.level(), this.player, this.removeCount)
        var `object`: Any? = this.player
        if (`object` is ServerPlayer) {
            `object` = this.container
            if (`object` is AbstractFurnaceBlockEntity) {
                `object`.awardUsedRecipesAndPopExperience(`object`)
            }
        }
        this.removeCount = 0*/
    }
}