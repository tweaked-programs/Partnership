package cc.tweaked_programs.partnership.client

import cc.tweaked_programs.partnership.client.registries.ScreenRegistries
import net.fabricmc.api.ClientModInitializer

object PartnershipClient : ClientModInitializer {
	override fun onInitializeClient() {
		// WAKE UP
		ScreenRegistries
	}
}