package cc.tweaked_programs.partnership.main.registries

import cc.tweaked_programs.partnership.main.MOD_ID
import cc.tweaked_programs.partnership.main.block.AnchorBlock
import cc.tweaked_programs.partnership.main.block.MetalScaffoldingBlock
import cc.tweaked_programs.partnership.main.block.boatyard.BoatyardBlock
import cc.tweaked_programs.partnership.main.block.buoy.BuoyBlock
import cc.tweaked_programs.partnership.main.block.buoy.ChainBuoyBlock
import cc.tweaked_programs.partnership.main.block.cannon.MarineCannonBlock
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.material.MapColor
import net.minecraft.world.level.material.PushReaction


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

    val BOATYARD: BoatyardBlock = create(
        name = "boatyard"
    ) { properties ->
        BoatyardBlock(
            properties
                .strength(1F)
                .mapColor(MapColor.METAL)
                .sounds(SoundType.STONE)
        )
    }

    val METAL_SCAFFOLDING: MetalScaffoldingBlock = create(
        name = "metal_scaffolding"
    ) { properties ->
        MetalScaffoldingBlock(
            properties
                .strength(0.9F)
                .mapColor(MapColor.METAL)
                .sounds(SoundType.METAL)
                .nonOpaque()
        )
    }

    val BUOY: BuoyBlock = create(
        name = "buoy"
    ) { properties ->
        BuoyBlock(properties
            .strength(0.1F)
            .mapColor(MapColor.COLOR_RED)
            .nonOpaque()
            .sounds(SoundType.CHAIN))
    }

    val CHAIN_BUOY: ChainBuoyBlock = create(
        name = "chain_buoy"
    ) { properties ->
        ChainBuoyBlock(properties
            .strength(0.075F)
            .mapColor(MapColor.METAL)
            .nonOpaque()
            .sounds(SoundType.CHAIN))
    }

    val MARINE_CANNON = create(
        name = "marine_cannon"
    ) { properties ->
        MarineCannonBlock(properties
            .sounds(SoundType.STONE)
            .mapColor(MapColor.COLOR_BLACK)
            .strength(1.25F)
            .nonOpaque())
    }

    val ANCHOR = create(
        name = "anchor"
    ) { properties ->
        AnchorBlock(
            properties
                .sounds(SoundType.METAL)
                .mapColor(MapColor.COLOR_BLACK)
                .strength(6F)
                .pistonBehavior(PushReaction.IGNORE)
                .nonOpaque()
        )
    }
}