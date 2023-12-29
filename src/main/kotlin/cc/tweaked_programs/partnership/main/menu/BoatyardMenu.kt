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
                val index = row + column * 4
                addSlot(GenericListenerSlot(inventory, index, 8+row*18, 14+column*18) {
                    updateRecipeOutput()
                })
            }
        }

        addSlot(GenericOutputOnlySlot(inventory, RESULT_SLOT, 13+6*18, 15+1*18))

        ImplementedInventory.addPlayerInventory(player = player, yOffset = 3) { slot -> addSlot(slot) }
        ImplementedInventory.addPlayerHotbar(player = player, yOffset = 3) { slot -> addSlot(slot) }

        updateRecipeOutput()
    }

    override fun quickMoveStack(player: Player, i: Int): ItemStack {
        val slot = slots[i]

        if (!slot.hasItem())
            return ItemStack.EMPTY

        if (i == RESULT_SLOT) {
            // craft as much as we can. This code is slow and ugly as hell, but I don't really have time to make this properly.
            while (slots[RESULT_SLOT].hasItem()) {
                val before = slots[RESULT_SLOT].item.count
                val remaining = ImplementedInventory.stackNewItemStackTo(player.inventory, slots[RESULT_SLOT].item.copy())
                slots[RESULT_SLOT].set(remaining)
                if (before == remaining.count)
                    break

                removeCraftingGridOnce()
                updateRecipeOutput()
            }
        } else if (CRAFTING_SLOTS.contains(i)) {
            // From container to player
            val remaining = ImplementedInventory.stackNewItemStackTo(player.inventory, slots[i].item.copy())
            slots[i].set(remaining)
        } else if (i > RESULT_SLOT) {
            // from player to container
            for (it in 0..<RESULT_SLOT) {
                if (slots[it].hasItem()) {
                    if (slots[it].item.equals(slot.item.item))
                        if ((slots[it].item.count + slot.item.count) <= 64) {
                            slots[it].item.count += slot.item.count
                            break
                        } else {
                            slot.item.count = 64 - (slots[it].item.count+slot.item.count)
                            slots[it].item.count = 64
                        }
                } else if (inventory is BoatyardBlockEntity) {
                    inventory.setItem(it, slot.item.copy())
                    break
                }
            }
            slots[i].set(ItemStack.EMPTY)
        } else return ItemStack.EMPTY

        return ItemStack.EMPTY
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

    private fun removeCraftingGridOnce() {
        if (player.player !is ServerPlayer || inventory !is BoatyardBlockEntity)
            return

        inventory.removeItem(RESULT_SLOT, 1)
    }
    
    companion object {
        const val RESULT_SLOT = INV_SIZE-1
        val CRAFTING_SLOTS = 0..<RESULT_SLOT
    }
}