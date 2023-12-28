package cc.tweaked_programs.partnership.client.registries

import cc.tweaked_programs.partnership.client.screen.BoatyardScreen
import cc.tweaked_programs.partnership.client.screen.MarineCannonScreen
import cc.tweaked_programs.partnership.main.registries.MenuRegistries
import net.minecraft.client.gui.screens.MenuScreens

object ScreenRegistries {
    init {
        MenuScreens.register(MenuRegistries.BOATYARD, ::BoatyardScreen)
        MenuScreens.register(MenuRegistries.MARINE_CANNON, ::MarineCannonScreen)
    }
}