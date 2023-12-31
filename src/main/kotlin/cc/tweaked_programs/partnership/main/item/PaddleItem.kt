package cc.tweaked_programs.partnership.main.item

import cc.tweaked_programs.partnership.main.item.extendable.AdvancedItemDescription
import com.google.common.collect.ImmutableMultimap
import com.google.common.collect.Multimap
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.attributes.Attribute
import net.minecraft.world.entity.ai.attributes.AttributeModifier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.item.*
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

class PaddleItem(private val knockbackLvl: Int, damage: Float, speed: Float, properties: Properties) : SwordItem(Tiers.WOOD, damage.toInt(), speed, properties), Vanishable {

    private val finalDamage = (damage + tier.attackDamageBonus)
    private var defaultModifiers: Multimap<Attribute, AttributeModifier>? = null

    init {
        val builder = ImmutableMultimap.builder<Attribute, AttributeModifier>()
        builder.put(
            Attributes.ATTACK_DAMAGE, AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier",
                finalDamage.toDouble(), AttributeModifier.Operation.ADDITION)
        )
        builder.put(
            Attributes.ATTACK_SPEED, AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier",
                speed.toDouble(), AttributeModifier.Operation.ADDITION)
        )
        this.defaultModifiers = builder.build()
    }

    override fun appendHoverText(itemStack: ItemStack, level: Level?, list: MutableList<Component>, tooltipFlag: TooltipFlag)
            = AdvancedItemDescription.appendHoverText(descriptionId, false, list)

    fun getKnockbackBaseLevel(): Int = knockbackLvl

    override fun hurtEnemy(itemStack: ItemStack, from: LivingEntity, to: LivingEntity): Boolean {
        itemStack.hurtAndBreak(1, to) { livingEntityx: LivingEntity ->
            livingEntityx.broadcastBreakEvent(EquipmentSlot.MAINHAND)
        }
        return true
    }

    override fun getDamage(): Float = finalDamage

    override fun getDestroySpeed(itemStack: ItemStack, blockState: BlockState): Float = 1.0F

    override fun mineBlock(itemStack: ItemStack, level: Level, blockState: BlockState, blockPos: BlockPos,
                           livingEntity: LivingEntity): Boolean = false

    override fun isCorrectToolForDrops(blockState: BlockState): Boolean = false

    override fun getDefaultAttributeModifiers(equipmentSlot: EquipmentSlot): Multimap<Attribute, AttributeModifier>?
        = if (equipmentSlot == EquipmentSlot.MAINHAND) this.defaultModifiers else super.getDefaultAttributeModifiers(equipmentSlot)
}