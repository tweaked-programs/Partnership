package cc.tweaked_programs.partnership.main.registries

import cc.tweaked_programs.partnership.main.MOD_ID
import cc.tweaked_programs.partnership.main.block.boatyard.BoatyardBlockEntity
import cc.tweaked_programs.partnership.main.block.cannon.MarineCannonBlockEntity
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.core.BlockPos
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

object BlockEntityRegistries {

    private fun <T : Block, U : BlockEntity> create(
        name: String,
        block: T,
        blockEntitySupplier: (BlockPos, BlockState) -> U): BlockEntityType<U> {

        return Registry.register(
            BuiltInRegistries.BLOCK_ENTITY_TYPE,
            ResourceLocation(MOD_ID, name),
            FabricBlockEntityTypeBuilder.create(blockEntitySupplier, block).build()
        )
    }

    val BOATYARD = create(
        name = "boatyard_block_entity",
        block = BlockRegistries.BOATYARD
    ) { pos, state -> BoatyardBlockEntity(pos, state) }

    val MARINE_CANNON = create(
        name = "marine_cannon_block_entity",
        block = BlockRegistries.MARINE_CANNON
    ) { pos, state -> MarineCannonBlockEntity(pos, state) }
}