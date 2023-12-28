package cc.tweaked_programs.partnership.main.registries

import cc.tweaked_programs.partnership.main.MOD_ID
import cc.tweaked_programs.partnership.main.menu.BoatyardMenu
import cc.tweaked_programs.partnership.main.menu.MarineCannonMenu
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.flag.FeatureFlagSet
import net.minecraft.world.inventory.MenuType

object MenuRegistries {
    val BOATYARD: MenuType<BoatyardMenu> = Registry.register(
        BuiltInRegistries.MENU,
        ResourceLocation(MOD_ID, "boatyard_menu"),
        MenuType(::BoatyardMenu, FeatureFlagSet.of())
    )

    val MARINE_CANNON: ExtendedScreenHandlerType<MarineCannonMenu> = Registry.register(
        BuiltInRegistries.MENU,
        ResourceLocation(MOD_ID, "marine_cannon_menu"),
        ExtendedScreenHandlerType(::MarineCannonMenu)
    )
}