package cc.tweaked_programs.partnership.main.compat

import cc.tweaked_programs.partnership.main.compat.boatism.BoatismCompat
import cc.tweaked_programs.partnership.main.compat.boatism.BoatismImpl
import net.fabricmc.loader.api.FabricLoader

object Compat {

    var boatism: BoatismCompat = BoatismCompat()

    fun check() {
        if (FabricLoader.getInstance().isModLoaded("boatism"))
            boatism = BoatismImpl()
    }
}