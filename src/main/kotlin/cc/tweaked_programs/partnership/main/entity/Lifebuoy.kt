package cc.tweaked_programs.partnership.main.entity

import cc.tweaked_programs.partnership.main.registries.EntityRegistries
import cc.tweaked_programs.partnership.main.registries.ItemRegistries
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundEvents
import net.minecraft.util.Mth
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityDimensions
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.animal.Animal
import net.minecraft.world.entity.vehicle.Boat
import net.minecraft.world.item.Item
import net.minecraft.world.level.Level
import org.joml.Vector3f

class Lifebuoy(type: EntityType<out Boat>, level: Level) : GenericBoat(type, level) {

    override val maxSpeed: Float = .02F
    override val backwardsSpeed: Float = .02F
    override val rotationSpeed: Float = 0F
    override val rotationBoostForGoodDrivers: Float = 0.01F

    companion object {
        const val SPEED_RANK: Int = 3
        const val MOBILITY_RANK: Int = 6
        const val SPACE_RANK: Int = 2
    }

    constructor(level: Level, x: Double, y: Double, z: Double) : this(EntityRegistries.LIFEBUOY, level) {
        setPos(x, y, z)
        xo = x; yo = y; zo = z
    }

    override fun getDropItem(): Item = ItemRegistries.LIFEBUOY

    override fun getSinglePassengerXOffset(): Float = 0.6F

    override fun getPassengerAttachmentPoint(entity: Entity, entityDimensions: EntityDimensions, f: Float): Vector3f
        = Vector3f(0.0f, entityDimensions.height / 3.0f - .3F, 0F)

    override fun getMaxPassengers(): Int = 1

    override fun clampRotation(entity: Entity) {
        if (entity is Animal) {
            super.clampRotation(entity)
            return
        }
        val f = Mth.wrapDegrees(entity.yRot - this.yRot)
        entity.setYBodyRot(f + yRot)
        this.yRot += f
    }

    override fun getPaddleSound(): SoundEvent? = when (status) {
        Status.IN_WATER, Status.UNDER_WATER, Status.UNDER_FLOWING_WATER -> SoundEvents.TURTLE_SWIM
        else -> null
    }
}