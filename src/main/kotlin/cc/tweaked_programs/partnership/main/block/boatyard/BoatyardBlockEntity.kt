package cc.tweaked_programs.partnership.main.block.boatyard

import cc.tweaked_programs.partnership.main.Partnership.logger
import cc.tweaked_programs.partnership.main.menu.BoatyardMenu
import cc.tweaked_programs.partnership.main.menu.ImplementedInventory
import cc.tweaked_programs.partnership.main.recipe.BoatyardRecipe
import cc.tweaked_programs.partnership.main.registries.BlockEntityRegistries.BOATYARD
import net.minecraft.core.BlockPos
import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.world.ContainerHelper
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.entity.player.StackedContents
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.CraftingContainer
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties

// TODO("Make sides locked for item pulling from last slot")
// TODO("Some mouse shortcuts dont work properly")
class BoatyardBlockEntity(val pos: BlockPos, val state: BlockState) : BlockEntity(BOATYARD, pos, state),
    MenuProvider, ImplementedInventory, CraftingContainer {

    val isDummy: Boolean = state.getValue(BlockStateProperties.EXTENDED)
    override val inventory: NonNullList<ItemStack> = NonNullList.withSize(INV_SIZE, ItemStack.EMPTY)

    override fun saveAdditional(compoundTag: CompoundTag) {
        if (isDummy) return

        ContainerHelper.saveAllItems(compoundTag, inventory)
        super.saveAdditional(compoundTag)
    }

    override fun load(compoundTag: CompoundTag) {
        if (isDummy) return

        super.load(compoundTag)
        ContainerHelper.loadAllItems(compoundTag, inventory)
    }

    override fun removeItem(slot: Int, count: Int): ItemStack {
        if (slot == INV_SIZE-1 && hasLevel()) {
            val optional = level!!.recipeManager.getRecipeFor(BoatyardRecipe.Companion.Type.TYPE, this, level!!)

            if (optional.isPresent) {
                val recipe = optional.get().value
                recipe.getIngredientsAsItemStacks().withIndex().forEach { (index, slot) ->
                    removeItem(index, slot.count)
                }
            }
        }
        val output = super.removeItem(slot, count)
        updateRecipeOutput(false)
        return output
    }

    fun updateRecipeOutput(remove: Boolean = true) {
        if (!hasLevel() || level!!.isClientSide)
            return

        val optional = level!!.recipeManager.getRecipeFor(BoatyardRecipe.Companion.Type.TYPE, this, level!!)

        if (optional.isPresent) {
            val recipe = optional.get().value
            if (recipe.matches(this, level!!)) {
                val output = recipe.assemble(this, level!!.registryAccess())
                setItem(INV_SIZE-1, output)
                return
            }
        }

        if (remove)
            setItem(INV_SIZE-1, ItemStack.EMPTY)
    }

    override fun fillStackedContents(stackedContents: StackedContents) {
        for (itemStack in this.items)
            stackedContents.accountSimpleStack(itemStack)
    }

    override fun getWidth(): Int = 4

    override fun getHeight(): Int = 3

    override fun getItems(): MutableList<ItemStack> = inventory

    override fun createMenu(syncId: Int, inventory: Inventory, player: Player): AbstractContainerMenu
            = BoatyardMenu(syncId, inventory, this)

    override fun getDisplayName(): Component = Component.translatable("block.partnership.boatyard")

    companion object {
        fun getMainBE(part: BoatyardBlockEntity): BoatyardBlockEntity? {
            if (part.isDummy) {
                val mainBE = part.level?.getBlockEntity(BoatyardBlock.getOtherSide(part.pos, part.state))
                if (mainBE is BoatyardBlockEntity && !mainBE.isDummy)
                    return mainBE
            } else return part

            logger.error("Could not get the main blockentity from the Boatyard at ${part.pos}")
            return null
        }

        const val INV_SIZE: Int = 13
    }
}