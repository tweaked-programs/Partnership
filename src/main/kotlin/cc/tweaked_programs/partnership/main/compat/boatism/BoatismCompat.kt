package cc.tweaked_programs.partnership.main.compat.boatism

import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.vehicle.Boat


open class BoatismCompat {
    open fun isEngine(entity: Entity): Boolean = false
    open fun calculateThrust(boat: Boat): Float = 0.0f
}