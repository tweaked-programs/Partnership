package cc.tweaked_programs.partnership.client

import cc.tweaked_programs.partnership.client.model.KayakModel
import cc.tweaked_programs.partnership.client.registries.ScreenRegistries
import cc.tweaked_programs.partnership.client.renderer.armor.HatRenderer
import cc.tweaked_programs.partnership.client.renderer.blockentity.MarineCannonBlockEntityRenderer
import cc.tweaked_programs.partnership.client.renderer.entity.KayakRenderer
import cc.tweaked_programs.partnership.main.registries.BlockEntityRegistries
import cc.tweaked_programs.partnership.main.registries.BlockRegistries
import cc.tweaked_programs.partnership.main.registries.EntityRegistries.CANNONBALL
import cc.tweaked_programs.partnership.main.registries.EntityRegistries.KAYAK
import cc.tweaked_programs.partnership.main.registries.ItemRegistries
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.entity.ThrownItemRenderer

object PartnershipClient : ClientModInitializer {
	override fun onInitializeClient() {
		// WAKE UP
		ScreenRegistries

		// Client
		blockRendering()
		entityRendering()
		blockEntityRenderer()
		customArmorRenderer()
	}

	private fun blockRendering() {
		BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistries.METAL_SCAFFOLDING, RenderType.cutout())
		BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistries.BUOY, RenderType.cutout())
	}

	private fun entityRendering() {
		EntityRendererRegistry.register(KAYAK, ::KayakRenderer)
		EntityModelLayerRegistry.registerModelLayer(KayakModel.LAYER_LOCATION, KayakModel::createBodyLayer)

		EntityRendererRegistry.register(CANNONBALL) { context: EntityRendererProvider.Context -> ThrownItemRenderer(context) }
	}

	private fun blockEntityRenderer() {
		BlockEntityRenderers.register(BlockEntityRegistries.MARINE_CANNON, ::MarineCannonBlockEntityRenderer)
	}

	private fun customArmorRenderer() {
		ArmorRenderer.register(HatRenderer(), ItemRegistries.CAPTAINS_HAT)
		ArmorRenderer.register(HatRenderer(), ItemRegistries.SAILORS_HAT)
	}
}