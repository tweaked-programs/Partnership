package cc.tweaked_programs.partnership.main.block

import net.minecraft.core.BlockPos
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.SimpleWaterloggedBlock
import net.minecraft.world.level.block.TransparentBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

// TODO("Implement SimpleWaterloggedBlock")
class MetalScaffoldingBlock(properties: Properties) : TransparentBlock(properties), SimpleWaterloggedBlock {

    @Suppress("OVERRIDE_DEPRECATION")
    override fun getShape(blockState: BlockState, blockGetter: BlockGetter, blockPos: BlockPos,
                          collisionContext: CollisionContext): VoxelShape {
        if (!collisionContext.isHoldingItem(blockState.block.asItem())) {
            var shape: VoxelShape = Shapes.empty()
            shape = Shapes.or(shape, Shapes.create(0.0, 0.0, 0.0, 0.0625, 1.0, 1.0))
            shape = Shapes.or(shape, Shapes.create(0.9375, 0.0, 0.0, 1.0, 1.0, 1.0))
            shape = Shapes.or(shape, Shapes.create(0.0625, 0.0, 0.0, 0.9375, 1.0, 0.0625))
            shape = Shapes.or(shape, Shapes.create(0.0625, 0.0, 0.9375, 0.9375, 1.0, 1.0))

            return shape
        } else return Shapes.block()
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun getInteractionShape(blockState: BlockState, blockGetter: BlockGetter, blockPos: BlockPos): VoxelShape {
        return Shapes.block()
    }
}