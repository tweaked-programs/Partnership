package cc.tweaked_programs.partnership.main

import cc.tweaked_programs.partnership.main.registries.*
import net.fabricmc.api.ModInitializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/*
[ ] = Planned
[-] = In work
[x] = Finished

TODO
	[x] Finish Boatyard block								POLISHING
		[x] Make block
		[x] Add block entity
		[x] Make an interface for it
		[x] Custom Recipe system etc.
	[-] Make first custom boat (!!!)
		[-] Add variants									POLISHING
			[x] Kayak
	[x] Add decorative stuff
		[x] Metal scaffolding
		[x] Marker buoys for borders on sea					POLISHING
	[x] Fun stuff
		[x] Paddle
		[x] Hats
			[x] Captain's hat
			[x] Sailor's cap
		[x] Marine Cannon									POLISHING
	[x] EMI Support
	[-] Redo textures but good this time
	[ ] Add recipes
	[x] Sort Item Group and secondary groups for items
	[ ] Remove normal boat recipe
*/

const val MOD_ID = "partnership"
const val BRAND_COLOR: Int = 0xFF2D54

object Partnership : ModInitializer {

	val logger: Logger = LoggerFactory.getLogger(MOD_ID)

	override fun onInitialize() {
		// WAKE UP
		BlockRegistries; BlockEntityRegistries; ItemRegistries; GroupRegistries; MenuRegistries; RecipeRegistries; EntityRegistries; NetworkRegistries
	}
}