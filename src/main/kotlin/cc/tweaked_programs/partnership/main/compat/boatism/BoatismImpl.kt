package cc.tweaked_programs.partnership.main.compat.boatism

import net.minecraft.world.entity.Entity
import net.shirojr.boatism.entity.custom.BoatEngineEntity

class BoatismImpl : BoatismCompat() {
    override fun isEngine(entity: Entity): Boolean = (entity is BoatEngineEntity)
}