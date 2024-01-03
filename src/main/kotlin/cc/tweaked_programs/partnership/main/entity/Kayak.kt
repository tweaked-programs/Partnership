package cc.tweaked_programs.partnership.main.entity

import cc.tweaked_programs.partnership.main.compat.Compat
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

class Kayak(type: EntityType<out Boat>, level: Level) : GenericBoat(type, level) {

    override val maxSpeed: Float = .05f
    override val backwardsSpeed: Float = .01f
    override val rotationSpeed: Float = .5F
    override val rotationBoostForGoodDrivers: Float = .008f

    companion object {
        const val SPEED_RANK: Int = 6
        const val MOBILITY_RANK: Int = 4
        const val SPACE_RANK: Int = 10
    }

    constructor(level: Level, x: Double, y: Double, z: Double) : this(EntityRegistries.KAYAK, level) {
        setPos(x, y, z)
        xo = x; yo = y; zo = z
    }

    override fun getDropItem(): Item = ItemRegistries.KAYAK

    override fun getSinglePassengerXOffset(): Float = 0.6F

    override fun getMaxPassengers(): Int {
        passengers.forEach {
            if (Compat.boatism.isEngine(it))
                return 4
        }
        return 3
    }

    fun getPassengerZOffset(entity: Entity): Float {
        val index = passengers.indexOf(entity)
        //val animalFactor = (if (entity is Animal) 1.2F else 1.0F)

        var offset = singlePassengerXOffset - (singlePassengerXOffset*index)// * animalFactor

        offset -= when (passengers.size) {
            1 -> singlePassengerXOffset
            2 -> singlePassengerXOffset/2
            else -> 0F
        }

        return offset
    }

    override fun positionRider(entity: Entity, moveFunction: MoveFunction) {
        super.positionRider(entity, moveFunction)
        if (entity is Animal) {
            entity.yBodyRot = 90F + rotationVector.y
            entity.yHeadRot = entity.getYHeadRot() + 180F
        }
    }

    override fun clampRotation(entity: Entity) {
        if (Compat.boatism.isEngine(entity))
            entity.setYBodyRot(yRot)
        else
            super.clampRotation(entity)
    }

    override fun getPassengerAttachmentPoint(entity: Entity, entityDimensions: EntityDimensions, f: Float): Vector3f
        = Vector3f(0.0F, entityDimensions.height / 2.5F, if (Compat.boatism.isEngine(entity)) -1.88F else getPassengerZOffset(entity))
}