package cc.tweaked_programs.partnership.main.block.buoy

import net.minecraft.core.BlockPos
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

class ChainBuoyBlock(properties: Properties) : BuoyBlock(properties) {

    @Suppress("OVERRIDE_DEPRECATION")
    override fun getShape(blockState: BlockState, blockGetter: BlockGetter, blockPos: BlockPos,
                          collisionContext: CollisionContext
    ): VoxelShape = shape

    companion object {
        private val shape: VoxelShape = Shapes.empty()
            .let { Shapes.or(it, Shapes.create(0.25, -0.25, 0.25, 0.75, 0.25, 0.75)) }
    }
}