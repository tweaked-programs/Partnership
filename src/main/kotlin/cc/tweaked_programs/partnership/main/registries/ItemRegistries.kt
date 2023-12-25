package cc.tweaked_programs.partnership.main.registries

import cc.tweaked_programs.partnership.main.MOD_ID
import cc.tweaked_programs.partnership.main.entity.Kayak
import cc.tweaked_programs.partnership.main.item.BoatyardItem
import cc.tweaked_programs.partnership.main.item.GenericBoatItem
import cc.tweaked_programs.partnership.main.item.PaddleItem
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.Tiers

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

    val KAYAK = create(
        name = "kayak"
    ) { properties ->
        properties.maxCount(1)
        GenericBoatItem(properties) { level, x, y, z ->
            Kayak(level, x, y, z)
        }
    }

    init {
        create(
            name = "metal_scaffolding"
        ) { properties ->
            BlockItem(
                BlockRegistries.METAL_SCAFFOLDING,
                properties.maxCount(64)
            )
        }

        create(
            name = "paddle"
        ) { properties ->
            properties.maxCount(1)
                .defaultDurability(Tiers.WOOD.uses)
                .durability(69) // Don't laugh.
            PaddleItem(1, 0.25F, (-1.8f), properties)
        }
    }
}