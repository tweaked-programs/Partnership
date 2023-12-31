package cc.tweaked_programs.partnership.main.entity

import cc.tweaked_programs.partnership.main.registries.EntityRegistries
import cc.tweaked_programs.partnership.main.registries.ItemRegistries
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityDimensions
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.animal.Animal
import net.minecraft.world.entity.vehicle.Boat
import net.minecraft.world.item.Item
import net.minecraft.world.level.Level
import org.joml.Vector3f

class Sailboat(type: EntityType<out Boat>, level: Level) : GenericBoat(type, level) {

    override val maxSpeed: Float = .0575f
    override val backwardsSpeed: Float = .03f
    override val rotationSpeed: Float =.8F
    override val rotationBoostForGoodDrivers: Float = .012f

    companion object {
        const val SPEED_RANK: Int = 10
        const val MOBILITY_RANK: Int = 10
        const val SPACE_RANK: Int = 10
    }

    constructor(level: Level, x: Double, y: Double, z: Double) : this(EntityRegistries.SAILBOAT, level) {
        setPos(x, y, z)
        xo = x; yo = y; zo = z
    }

    override fun getDropItem(): Item = ItemRegistries.SAILBOAT

    override fun getSinglePassengerXOffset(): Float = 0.3F

    override fun getMaxPassengers(): Int = 1

    private fun getPassengerZOffset(entity: Entity): Float = singlePassengerXOffset

    override fun positionRider(entity: Entity, moveFunction: MoveFunction) {
        super.positionRider(entity, moveFunction)
        if (entity is Animal) {
            entity.yBodyRot = 90F + rotationVector.y
            entity.yHeadRot = entity.getYHeadRot() + 180F
        }
    }

    override fun getPassengerAttachmentPoint(entity: Entity, entityDimensions: EntityDimensions, f: Float): Vector3f
        = Vector3f(0.0f, entityDimensions.height / 2.5f, getPassengerZOffset(entity))
}