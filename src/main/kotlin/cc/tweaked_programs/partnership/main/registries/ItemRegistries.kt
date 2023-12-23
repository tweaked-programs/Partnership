package cc.tweaked_programs.partnership.main.registries

import cc.tweaked_programs.partnership.main.MOD_ID
import cc.tweaked_programs.partnership.main.item.BoatyardItem
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation

import net.minecraft.world.item.Item

object ItemRegistries {

    val registered = mutableListOf<Item>()

    private fun <T : Item> create(name: String, itemSupplier: (FabricItemSettings) -> T): T {
        val item = Registry.register(
            BuiltInRegistries.ITEM,
            ResourceLocation(MOD_ID, name),
            itemSupplier.invoke(FabricItemSettings())
        )

        registered.add(item)
        return item
    }

    val BOATYARD = create(
        name = "boatyard"
    ) { properties ->
        BoatyardItem(
            BlockRegistries.BOATYARD,
            properties.maxCount(64)
        )
    }
}