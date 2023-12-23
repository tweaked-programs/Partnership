package cc.tweaked_programs.partnership.client.registries

import cc.tweaked_programs.partnership.client.screen.BoatyardContainerScreen
import cc.tweaked_programs.partnership.main.registries.MenuRegistries
import net.minecraft.client.gui.screens.MenuScreens

object ScreenRegistries {
    init {
        MenuScreens.register(MenuRegistries.BOATYARD, ::BoatyardContainerScreen)
    }
}