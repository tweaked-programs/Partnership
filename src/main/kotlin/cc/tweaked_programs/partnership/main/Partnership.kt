package cc.tweaked_programs.partnership.main

import cc.tweaked_programs.partnership.main.compat.Compat
import cc.tweaked_programs.partnership.main.registries.*
import net.fabricmc.api.ModInitializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

const val MOD_ID = "partnership"
const val BRAND_COLOR: Int = 0xFF2D54

object Partnership : ModInitializer {

	val logger: Logger = LoggerFactory.getLogger(MOD_ID)

	override fun onInitialize() {
		// WAKE UP
		BlockRegistries; BlockEntityRegistries; ItemRegistries; GroupRegistries; MenuRegistries; RecipeRegistries; EntityRegistries; NetworkRegistries

		Compat.check()
	}
}