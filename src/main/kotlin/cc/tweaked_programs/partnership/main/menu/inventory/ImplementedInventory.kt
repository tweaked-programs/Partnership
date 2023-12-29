package cc.tweaked_programs.partnership.main.menu.inventory

import net.minecraft.core.NonNullList
import net.minecraft.world.Container
import net.minecraft.world.ContainerHelper
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.ItemStack

interface ImplementedInventory : Container {
    val inventory: NonNullList<ItemStack>

    override fun getContainerSize(): Int = inventory.size

    override fun isEmpty(): Boolean {
        for (i in 0 until containerSize) {
            val stack = getItem(i)
            if (!stack.isEmpty)
                return false
        }
        return true
    }

    override fun getItem(slot: Int): ItemStack = inventory[slot]

    override fun removeItem(slot: Int, count: Int): ItemStack {
        val result = ContainerHelper.removeItem(inventory, slot, count)
        if (!result.isEmpty)
            setChanged()
        return result
    }

    override fun removeItemNoUpdate(slot: Int): ItemStack? {
        val items: List<ItemStack> = inventory
        return ContainerHelper.takeItem(items, slot)
    }

    override fun setItem(slot: Int, stack: ItemStack) {
        inventory[slot] = stack
        if (stack.count > stack.maxStackSize)
            stack.count = stack.maxStackSize
        setChanged()
    }

    override fun clearContent() = inventory.clear()

    override fun setChanged()

    override fun stillValid(player: Player): Boolean = true

    companion object {
        fun addPlayerInventory(player: Inventory, yOffset: Int = 0, addSlot: (Slot) -> Slot) {
            repeat(3) { column ->
                repeat(9) { row ->
                    addSlot.invoke(Slot(player, row + column * 9 + 9, 8 + row * 18, 86 + column * 18 + yOffset))
                }
            }
        }

        fun addPlayerHotbar(player: Inventory, yOffset: Int = 0, addSlot: (Slot) -> Slot) {
            repeat(9) { row ->
                addSlot.invoke(Slot(player, row, 8 + row * 18, 144 + yOffset))
            }
        }

        fun stackNewItemStackTo(destination: Inventory, item: ItemStack): ItemStack {
            var remaining = item

            while (!remaining.isEmpty) {
                var slot = destination.getSlotWithRemainingSpace(remaining)

                if (slot == -1) {
                    slot = destination.freeSlot
                    if (slot == -1)
                        return remaining
                }

                if (destination.items[slot].isEmpty) {
                    destination.add(slot, ItemStack(remaining.item))
                    destination.items[slot].count = 0
                }

                if (remaining.count + destination.items[slot].count > 64) {
                    // Fill slot, get remaining
                    destination.items[slot].count = 64
                    remaining.count = 64 - (remaining.count + destination.items[slot].count)
                } else {
                    // Put rest in it
                    destination.items[slot].count += remaining.count
                    remaining = ItemStack.EMPTY
                }
            }

            return ItemStack.EMPTY
        }
    }
}