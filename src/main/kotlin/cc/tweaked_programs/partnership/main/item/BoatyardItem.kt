package cc.tweaked_programs.partnership.main.item

import cc.tweaked_programs.partnership.main.block.boatyard.BoatyardBlock
import cc.tweaked_programs.partnership.main.registries.BlockRegistries
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.properties.BlockStateProperties


class BoatyardItem(block: Block, properties: Properties) : BlockItem(block, properties) {

    override fun appendHoverText(itemStack: ItemStack, level: Level?, list: MutableList<Component>,
                                 tooltipFlag: TooltipFlag) {
        AdvancedItemDescription.appendHoverText(getDescriptionId(), true, list)
    }

    override fun place(blockPlaceContext: BlockPlaceContext): InteractionResult {
        val otherSide = BoatyardBlock.getOtherSide(blockPlaceContext.clickedPos,
            BlockRegistries.BOATYARD.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, blockPlaceContext.horizontalDirection.opposite))

        if (!blockPlaceContext.getLevel().getBlockState(otherSide).canBeReplaced())
            return InteractionResult.FAIL

        return super.place(blockPlaceContext)
    }
}