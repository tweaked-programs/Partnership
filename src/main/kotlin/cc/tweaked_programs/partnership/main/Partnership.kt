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
	[x] Finish Boatyard block
		[x] Make block
		[x] Add block entity
		[x] Make an interface for it
		[x] Custom Recipe system etc.
	[-] Make first custom boat (!!!)
		[-] Add variants
			[x] Kayak
		[ ] Make boats fixable within leashes
	[-] Add decorative stuff
		[x] Metal scaffolding
		[ ] Marker buoys for borders on sea
		[ ] Light towers lamp
		[ ] Boat Cradle
	[-] Fun stuff
		[x] Paddle
		[ ] Hats
			[ ] Marine hat
		[ ] Marine Cannon
		[ ] Schiffe versenken game (maybe not...)
	[ ] Redo textures but good this time
	[ ] Add recipes
	[ ] Sort Item Group and secondary groups for items
*/

const val MOD_ID = "partnership"
const val BRAND_COLOR: Int = 0xFF2D54

object Partnership : ModInitializer {

	val logger: Logger = LoggerFactory.getLogger(MOD_ID)

	override fun onInitialize() {
		// WAKE UP
		BlockRegistries; BlockEntityRegistries; ItemRegistries; GroupRegistries; MenuRegistries; RecipeRegistries; EntityRegistries
	}
}