package cc.tweaked_programs.partnership.main.entity

import cc.tweaked_programs.partnership.main.registries.EntityRegistries
import cc.tweaked_programs.partnership.main.registries.ItemRegistries
import net.minecraft.core.particles.ItemParticleOption
import net.minecraft.core.particles.ParticleOptions
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.projectile.ThrowableItemProjectile
import net.minecraft.world.entity.vehicle.Boat
import net.minecraft.world.item.Item
import net.minecraft.world.level.Level
import net.minecraft.world.phys.EntityHitResult
import net.minecraft.world.phys.HitResult

class CannonballEntity : ThrowableItemProjectile {
    constructor(entityType: EntityType<out CannonballEntity>, level: Level) : super(entityType, level)

    constructor(level: Level, livingEntity: LivingEntity) : super(EntityRegistries.CANNONBALL, livingEntity, level)

    constructor(level: Level, d: Double, e: Double, f: Double) : super(EntityRegistries.CANNONBALL, d, e, f, level)

    override fun getDefaultItem(): Item = ItemRegistries.CANNONBALL

    private val particle: ParticleOptions
        get() {
            return (if (itemRaw.isEmpty) ParticleTypes.SMOKE else ItemParticleOption(
                ParticleTypes.ITEM,
                itemRaw
            )) as ParticleOptions
        }

    override fun handleEntityEvent(b: Byte) {
        if (b.toInt() == 3) {
            val particleOptions = this.particle

            for (i in 0..7)
                level().addParticle(particleOptions, this.x, this.y, this.z, 0.0, 0.0, 0.0)
        }
    }

    override fun onHitEntity(entityHitResult: EntityHitResult) {
        super.onHitEntity(entityHitResult)
        val entity = entityHitResult.entity
        if (entity is Boat) {
            entity.level().playSound(
                null,
                entity.onPos,
                SoundEvents.ZOMBIE_ATTACK_WOODEN_DOOR,
                SoundSource.NEUTRAL,
                1f,
                1.2F
            )
            entity.dropItem
            entity.kill()
        } else entity.hurt(damageSources().thrown(this, this.owner), DAMAGE)
    }

    override fun onHit(hitResult: HitResult) {
        super.onHit(hitResult)
        if (!level().isClientSide) {
            level().broadcastEntityEvent(this, 3.toByte())
            this.discard()
        }
    }

    companion object {
        const val DAMAGE: Float = 5.5F
    }
}