package cc.tweaked_programs.partnership.main.menu.inventory

import net.minecraft.world.Container
import net.minecraft.world.inventory.Slot

class GenericListenerSlot(container: Container, val i: Int, j: Int, k: Int, private val listener: (id: Int) -> Unit) : Slot(container, i, j, k) {
    override fun setChanged() {
        super.setChanged()
        listener.invoke(i)
    }
}