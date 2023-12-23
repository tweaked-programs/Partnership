package cc.tweaked_programs.partnership.main.registries

import cc.tweaked_programs.partnership.main.MOD_ID
import cc.tweaked_programs.partnership.main.block.boatyard.BoatyardBlock
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block

object BlockRegistries {

    private fun <T : Block> create(
        name: String,
        blockSupplier: (FabricBlockSettings) -> T): T {

        return Registry.register(
            BuiltInRegistries.BLOCK,
            ResourceLocation(MOD_ID, name),
            blockSupplier.invoke(FabricBlockSettings.create())
        )
    }

    val BOATYARD = create(
        name = "boatyard"
    ) { properties ->
        BoatyardBlock(properties
            .strength(1F)
            .requiresTool()
        )
    }
}