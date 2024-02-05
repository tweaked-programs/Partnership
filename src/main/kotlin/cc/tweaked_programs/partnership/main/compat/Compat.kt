package cc.tweaked_programs.partnership.main.compat

import cc.tweaked_programs.partnership.main.compat.boatism.BoatismCompat
import cc.tweaked_programs.partnership.main.compat.boatism.BoatismImpl
import net.fabricmc.loader.api.FabricLoader

object Compat {
    const val MOD_ID_BOATISM = "boatism"

    var boatism: BoatismCompat = BoatismCompat()

    fun check() {
        if (FabricLoader.getInstance().isModLoaded(MOD_ID_BOATISM))
            boatism = BoatismImpl()
    }
}