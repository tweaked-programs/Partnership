package cc.tweaked_programs.partnership.client

import cc.tweaked_programs.partnership.client.model.KayakModel
import cc.tweaked_programs.partnership.client.registries.ScreenRegistries
import cc.tweaked_programs.partnership.client.renderer.entity.KayakRenderer
import cc.tweaked_programs.partnership.main.registries.BlockRegistries
import cc.tweaked_programs.partnership.main.registries.EntityRegistries.KAYAK
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry
import net.minecraft.client.renderer.RenderType

object PartnershipClient : ClientModInitializer {
	override fun onInitializeClient() {
		// WAKE UP
		ScreenRegistries

		blockRendering()
		entityRendering()
	}

	private fun blockRendering() {
		BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistries.METAL_SCAFFOLDING, RenderType.cutout())
	}

	private fun entityRendering() {
		EntityRendererRegistry.register(KAYAK, ::KayakRenderer)
		EntityModelLayerRegistry.registerModelLayer(KayakModel.LAYER_LOCATION, KayakModel::createBodyLayer)
	}
}