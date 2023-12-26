package cc.tweaked_programs.partnership.main.item

import net.minecraft.network.chat.Component
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Equipable
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block

class Hat(block: Block, private val sound: SoundEvent, properties: Properties) : BlockItem(block, properties), Equipable {

    override fun appendHoverText(itemStack: ItemStack, level: Level?, list: MutableList<Component>, tooltipFlag: TooltipFlag)
            = AdvancedItemDescription.appendHoverText(descriptionId, false, list)

    override fun use(level: Level, player: Player, interactionHand: InteractionHand): InteractionResultHolder<ItemStack>?
        = this.swapWithEquipmentSlot(this, level, player, interactionHand)

    override fun getEquipSound(): SoundEvent = sound
    override fun getEquipmentSlot(): EquipmentSlot = EquipmentSlot.HEAD
}