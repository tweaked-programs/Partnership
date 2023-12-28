package cc.tweaked_programs.partnership.main.menu

import cc.tweaked_programs.partnership.main.block.boatyard.BoatyardBlockEntity
import cc.tweaked_programs.partnership.main.block.boatyard.BoatyardBlockEntity.Companion.INV_SIZE
import cc.tweaked_programs.partnership.main.menu.inventory.GenericListenerSlot
import cc.tweaked_programs.partnership.main.menu.inventory.GenericOutputOnlySlot
import cc.tweaked_programs.partnership.main.menu.inventory.ImplementedInventory
import cc.tweaked_programs.partnership.main.registries.MenuRegistries
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.Container
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerListener
import net.minecraft.world.item.ItemStack

class BoatyardMenu(
    syncId: Int,
    private val player: Inventory,
    private val inventory: Container = SimpleContainer(INV_SIZE)
) : AbstractContainerMenu(MenuRegistries.BOATYARD, syncId), ContainerListener {

    init {
        checkContainerSize(inventory, INV_SIZE)
        inventory.startOpen(player.player)

        repeat (3) { column ->
            repeat (4) { row ->
                addSlot(GenericListenerSlot(inventory, row + column * 4, 8 + row * 18, 14 + column * 18) {
                    updateRecipeOutput()
                })
            }
        }

        addSlot(GenericOutputOnlySlot(player.player, inventory, INV_SIZE-1, 13 + 6 * 18, 15 + 1 * 18))

        ImplementedInventory.addPlayerInventory(player = player, yoffset = 3) { slot -> addSlot(slot) }
        ImplementedInventory.addPlayerHotbar(player = player, yoffset = 3) { slot -> addSlot(slot) }

        updateRecipeOutput()
    }

    override fun quickMoveStack(player: Player, i: Int): ItemStack? {
        val slot = slots[i]
        if (!slot.hasItem()) return ItemStack.EMPTY

        val itemStack2 = slot.item.copy()

        val targetStart = if (i < INV_SIZE-1) INV_SIZE-1 else 0
        val targetEnd = if (i < INV_SIZE-1) 49 else INV_SIZE-1

        if (!moveItemStackTo(itemStack2, targetStart, targetEnd, i < INV_SIZE-1))
            return ItemStack.EMPTY

        if (itemStack2.isEmpty)
            slot.set(ItemStack.EMPTY)
        else slot.setChanged()

        if (itemStack2.count == itemStack2.count)
            return ItemStack.EMPTY

        slot.onTake(player, itemStack2)

        updateRecipeOutput()
        return itemStack2
    }

    override fun stillValid(player: Player): Boolean = inventory.stillValid(player)

    override fun slotChanged(abstractContainerMenu: AbstractContainerMenu, i: Int, itemStack: ItemStack) {
        updateRecipeOutput()
    }

    override fun dataChanged(abstractContainerMenu: AbstractContainerMenu, i: Int, j: Int) { }

    private fun updateRecipeOutput() {
        if (player.player !is ServerPlayer || inventory !is BoatyardBlockEntity)
            return

        inventory.updateRecipeOutput()
    }
}