package cc.tweaked_programs.partnership.main.block

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.ChainBlock
import net.minecraft.world.level.block.SimpleWaterloggedBlock
import net.minecraft.world.level.block.TransparentBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.material.FluidState
import net.minecraft.world.level.material.Fluids
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

class MetalScaffoldingBlock(properties: Properties) : TransparentBlock(properties), SimpleWaterloggedBlock {

    init {
        registerDefaultState(stateDefinition.any()
            .setValue(BlockStateProperties.WATERLOGGED, false)
        )
    }

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

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) { builder
        .add(BlockStateProperties.WATERLOGGED)
    }

    override fun getStateForPlacement(ctx: BlockPlaceContext): BlockState? = defaultBlockState()
        .setValue(BlockStateProperties.WATERLOGGED, ctx.level
            .getFluidState(ctx.clickedPos).type === Fluids.WATER)

    @Suppress("OVERRIDE_DEPRECATION", "DEPRECATION")
    override fun updateShape(blockState: BlockState, direction: Direction, targetBlockState: BlockState,
                             levelAccessor: LevelAccessor, blockPos: BlockPos, targetBlockPos: BlockPos): BlockState? {

        if (blockState.getValue(ChainBlock.WATERLOGGED))
            levelAccessor.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor))

        return super.updateShape(blockState, direction, targetBlockState, levelAccessor, blockPos, targetBlockPos)
    }

    @Suppress("OVERRIDE_DEPRECATION", "DEPRECATION")
    override fun getFluidState(blockState: BlockState): FluidState {
        if (blockState.getValue(ChainBlock.WATERLOGGED))
            return Fluids.WATER.getSource(false)

        return super.getFluidState(blockState)
    }
}