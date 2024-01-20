package cc.tweaked_programs.partnership.main.block

import cc.tweaked_programs.partnership.main.level.command.MarkChunkAsSeaport
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.material.FluidState
import net.minecraft.world.level.material.Fluids
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

class AnchorBlock(properties: Properties) : Block(properties), SimpleWaterloggedBlock {

    init {
        registerDefaultState(stateDefinition.any()
            .setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH)
            .setValue(BlockStateProperties.WATERLOGGED, false)
        )
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) { builder
        .add(BlockStateProperties.HORIZONTAL_FACING)
        .add(BlockStateProperties.WATERLOGGED)
    }

    override fun getStateForPlacement(ctx: BlockPlaceContext): BlockState? = defaultBlockState()
        .setValue(BlockStateProperties.HORIZONTAL_FACING, if (ctx.player?.isCrouching == true) ctx.horizontalDirection.opposite else ctx.horizontalDirection)
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

    @Suppress("OVERRIDE_DEPRECATION")
    override fun rotate(blockState: BlockState, rotation: Rotation): BlockState {
        return blockState.setValue(
            HorizontalDirectionalBlock.FACING, rotation.rotate(blockState.getValue(
                HorizontalDirectionalBlock.FACING) as Direction)) as BlockState
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun mirror(blockState: BlockState, mirror: Mirror): BlockState {
        return blockState.rotate(mirror.getRotation(blockState.getValue(HorizontalDirectionalBlock.FACING) as Direction))
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun getShape(blockState: BlockState, blockGetter: BlockGetter, blockPos: BlockPos,
                          collisionContext: CollisionContext
    ): VoxelShape = when(blockState.getValue(HorizontalDirectionalBlock.FACING)) {
        Direction.NORTH -> Shapes.create(0.0, 0.0, 0.0, 1.0, 1.0, 0.125)
        Direction.EAST -> Shapes.create(0.875, 0.0, 0.0, 1.0, 1.0, 1.0)
        Direction.SOUTH -> Shapes.create(0.0, 0.0, 0.875, 1.0, 1.0, 1.0)
        Direction.WEST -> Shapes.create(0.0, 0.0, 0.0, 0.125, 1.0, 1.0)
        else -> Shapes.block()
    }

    @Suppress("OVERRIDE_DEPRECATION", "DEPRECATION")
    override fun onPlace(blockState: BlockState, level: Level, blockPos: BlockPos, blockState2: BlockState, bl: Boolean) {
        super.onPlace(blockState, level, blockPos, blockState2, bl)
        if (!level.isClientSide && level is ServerLevel) {
            val chunkPos = level.getChunk(blockPos).pos
            val anchors = MarkChunkAsSeaport.getAnchorsOfChunk(level, chunkPos)

            MarkChunkAsSeaport.setChunk(level, chunkPos, anchors+1)
        }
    }

    @Suppress("OVERRIDE_DEPRECATION", "DEPRECATION")
    override fun onRemove(blockState: BlockState, level: Level, blockPos: BlockPos, blockState2: BlockState, bl: Boolean) {
        super.onRemove(blockState, level, blockPos, blockState2, bl)
        if (!level.isClientSide && level is ServerLevel) {
            val chunkPos = level.getChunk(blockPos).pos
            val anchors = MarkChunkAsSeaport.getAnchorsOfChunk(level, chunkPos)

            MarkChunkAsSeaport.setChunk(level, chunkPos, anchors-1)
        }
    }
}