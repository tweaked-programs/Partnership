package cc.tweaked_programs.partnership.main.item.extendable

import cc.tweaked_programs.partnership.main.entity.GenericBoat
import net.minecraft.network.chat.Component
import net.minecraft.stats.Stats
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntitySelector
import net.minecraft.world.entity.player.Player
import net.minecraft.world.entity.vehicle.Boat
import net.minecraft.world.item.BoatItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.ClipContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.gameevent.GameEvent
import net.minecraft.world.phys.HitResult
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.jvm.reflect

class GenericBoatItem (
    properties: Properties,
    val speed: Int,
    val mobility: Int,
    val space: Int,
    private val boat: (Level, Double, Double, Double) -> GenericBoat) : BoatItem(false, Boat.Type.OAK, properties) {

    override fun appendHoverText(itemStack: ItemStack, level: Level?, list: MutableList<Component>, tooltipFlag: TooltipFlag)
        = AdvancedItemDescription.appendHoverText(descriptionId, true, list)

    override fun use(level: Level, user: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        val itemStack = user.getItemInHand(hand)
        val hitResult: HitResult = Item.getPlayerPOVHitResult(level, user, ClipContext.Fluid.ANY)

        if (hitResult.type == HitResult.Type.MISS)
            return InteractionResultHolder.pass(itemStack)

        val vec3 = user.getViewVector(1.0f)
        val list = level.getEntities(user, user.boundingBox.expandTowards(vec3.scale(5.0)).inflate(1.0),
            EntitySelector.NO_SPECTATORS.and { obj: Entity -> obj.isPickable })

        if (list.isNotEmpty()) {
            val vec32 = user.eyePosition
            val var11: Iterator<*> = list.iterator()

            while (var11.hasNext()) {
                val entity = var11.next() as Entity
                val aABB = entity.boundingBox.inflate(entity.pickRadius.toDouble())

                if (aABB.contains(vec32))
                    return InteractionResultHolder.pass(itemStack)
            }
        }

        if (hitResult.type != HitResult.Type.BLOCK)
            return InteractionResultHolder.pass(itemStack)

        val location = hitResult.location
        val boat = boat.invoke(level, location.x, location.y, location.z)
        boat.yRot = user.yRot

        if (!level.noCollision(boat, boat.boundingBox))
            return InteractionResultHolder.fail(itemStack)

        if (!level.isClientSide) {
            level.addFreshEntity(boat)
            level.gameEvent(user, GameEvent.ENTITY_PLACE, hitResult.location)
            if (!user.abilities.instabuild)
                itemStack.shrink(1)

        }

        user.awardStat(Stats.ITEM_USED[this])
        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide())
    }
}