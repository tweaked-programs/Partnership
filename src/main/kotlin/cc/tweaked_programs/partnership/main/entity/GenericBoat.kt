package cc.tweaked_programs.partnership.main.entity

import cc.tweaked_programs.partnership.main.compat.Compat
import net.minecraft.util.Mth
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.vehicle.Boat
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.level.Level
import kotlin.math.PI

abstract class GenericBoat(type: EntityType<out Boat>, level: Level) : Boat(type, level) {

    protected abstract val maxSpeed: Float
    protected abstract val backwardsSpeed: Float
    protected abstract val rotationSpeed: Float
    protected abstract val rotationBoostForGoodDrivers: Float

    override fun controlBoat() {
        if (!isVehicle)
            return
        var speed = 0.0f

        if (inputLeft)
            deltaRotation -= rotationSpeed
        if (inputRight)
            deltaRotation += rotationSpeed
        if (inputRight != inputLeft && !inputUp && !inputDown)
            speed += rotationBoostForGoodDrivers

        yRot += deltaRotation

        if (inputUp)
            speed += maxSpeed
        if (inputDown)
            speed -= backwardsSpeed

        speed += Compat.boatism.calculateThrust(this)

        deltaMovement = deltaMovement.add(
            (Mth.sin(-yRot * MAGIKK) * speed).toDouble(),
            0.0,
            (Mth.cos(yRot * MAGIKK) * speed).toDouble()
        )
        setPaddleState(
            inputRight && !inputLeft || inputUp,
            inputLeft && !inputRight || inputUp
        )
    }

    override fun getDropItem(): Item = Items.AIR

    companion object {
        const val MAGIKK: Float = (PI.toFloat() / 180f)
    }
}