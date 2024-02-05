package cc.tweaked_programs.partnership.main.compat.boatism

import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.vehicle.Boat
import net.shirojr.boatism.entity.custom.BoatEngineEntity
import net.shirojr.boatism.util.BoatEngineCoupler
import net.shirojr.boatism.util.EntityHandler
import java.util.*
import kotlin.jvm.optionals.getOrNull

class BoatismImpl : BoatismCompat() {
    override fun isEngine(entity: Entity): Boolean = (entity is BoatEngineEntity)
    override fun calculateThrust(boat: Boat): Float {
        val coupler = boat as BoatEngineCoupler
        var speed = 0.0f
        coupler.`boatism$getBoatEngineEntityUuid`()?.let { uuid: Optional<UUID> ->
            {
                EntityHandler.getBoatEngineEntityFromUuid(uuid.getOrNull(), boat.level(), boat.position(), 10)?.also {
                    val thrust = it.get().engineHandler.calculateThrustModifier(boat)
                    val powerLevel = it.get().powerLevel * 0.008f
                    speed = powerLevel * thrust
                }
            }
        }
        return speed
    }
}