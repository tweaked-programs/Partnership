package cc.tweaked_programs.partnership.main.item.extendable

import net.minecraft.network.chat.Component
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.PlaceOnWaterBlockItem
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block

class DescriptivePlaceOnWaterBlockItem(block: Block, properties: Properties) : PlaceOnWaterBlockItem(block, properties) {

    override fun appendHoverText(itemStack: ItemStack, level: Level?, list: MutableList<Component>, tooltipFlag: TooltipFlag)
            = AdvancedItemDescription.appendHoverText(descriptionId, false, list)
}