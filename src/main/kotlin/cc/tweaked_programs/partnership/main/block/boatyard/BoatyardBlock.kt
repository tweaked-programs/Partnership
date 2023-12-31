package cc.tweaked_programs.partnership.main.block.boatyard

import cc.tweaked_programs.partnership.main.Partnership.logger
import cc.tweaked_programs.partnership.main.menu.BoatyardMenu
import com.mojang.serialization.MapCodec
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.Containers
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.material.FluidState
import net.minecraft.world.level.material.Fluids
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape

class BoatyardBlock(properties: Properties) : HorizontalDirectionalBlock(properties), EntityBlock, SimpleWaterloggedBlock {

    override fun newBlockEntity(blockPos: BlockPos, blockState: BlockState): BlockEntity {
        return BoatyardBlockEntity(blockPos, blockState)
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun use(blockState: BlockState, level: Level, blockPos: BlockPos, player: Player,
                     interactionHand: InteractionHand, blockHitResult: BlockHitResult): InteractionResult {

        if (level.isClientSide) return InteractionResult.SUCCESS

        val menuProvider = blockState.getMenuProvider(level, blockPos)

        menuProvider?.let { player.openMenu(it) }

        return InteractionResult.SUCCESS
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun getMenuProvider(blockState: BlockState, level: Level, blockPos: BlockPos): MenuProvider? {
        val blockEntity = level.getBlockEntity(blockPos)
        return if (blockEntity is BoatyardBlockEntity) BoatyardBlockEntity.getMainBE(blockEntity) else null
    }

    init {
        registerDefaultState(stateDefinition.any()
            .setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH)
            .setValue(BlockStateProperties.EXTENDED, false)
            .setValue(BlockStateProperties.WATERLOGGED, false)
        )
    }

    @Suppress("DEPRECATION", "OVERRIDE_DEPRECATION")
    override fun onRemove(blockState: BlockState, level: Level, blockPos: BlockPos, newBlockState: BlockState,
                          moved: Boolean) {
        if (blockState.block == newBlockState.block)
            return

        val blockEntity = level.getBlockEntity(blockPos)

        if (blockEntity is BoatyardBlockEntity) {
            blockEntity.rawRemoveItem(BoatyardMenu.RESULT_SLOT, 64)
            Containers.dropContents(level, blockPos, blockEntity)
        }
        super.onRemove(blockState, level, blockPos, newBlockState, moved)
    }

    override fun codec(): MapCodec<out HorizontalDirectionalBlock> = throw NotImplementedError("Normally not required in 1.20.4")

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) { builder
        .add(BlockStateProperties.HORIZONTAL_FACING)
        .add(BlockStateProperties.EXTENDED)
        .add(BlockStateProperties.WATERLOGGED)
    }

    override fun getStateForPlacement(ctx: BlockPlaceContext): BlockState? = defaultBlockState()
        .setValue(BlockStateProperties.HORIZONTAL_FACING, ctx.horizontalDirection.opposite)
        .setValue(BlockStateProperties.EXTENDED, false)
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

    override fun setPlacedBy(level: Level, blockPos: BlockPos, blockState: BlockState, livingEntity: LivingEntity?,
        itemStack: ItemStack) {

        if (level.isClientSide)
            return

        super.setPlacedBy(level, blockPos, blockState, livingEntity, itemStack)

        val otherSide = getOtherSide(blockPos, blockState)

        level.setBlock(otherSide, blockState
            .setValue(BlockStateProperties.HORIZONTAL_FACING,
                blockState.getValue(BlockStateProperties.HORIZONTAL_FACING))
            .setValue(BlockStateProperties.EXTENDED, true)
            .setValue(BlockStateProperties.WATERLOGGED, level.isWaterAt(otherSide)) as BlockState, 3)

        level.blockUpdated(blockPos, Blocks.AIR)
        blockState.updateNeighbourShapes(level, blockPos, 3)
    }

    override fun playerWillDestroy(level: Level, blockPos: BlockPos, blockState: BlockState,
                                   player: Player): BlockState {

        if (level.isClientSide)
            return blockState

        val otherSide = getOtherSide(blockPos, blockState)

        if (level.getBlockState(otherSide).block is BoatyardBlock) {
            level.destroyBlock(otherSide, false)
            level.levelEvent(player, 2001, otherSide, getId(level.getBlockState(otherSide)))
        }

        return super.playerWillDestroy(level, blockPos, blockState, player)
    }

    override fun hasDynamicShape(): Boolean = false

    @Suppress("OVERRIDE_DEPRECATION")
    override fun getShape(blockState: BlockState, blockGetter: BlockGetter, blockPos: BlockPos,
                          collisionContext: CollisionContext): VoxelShape {

        return when(Pair(blockState.getValue(BlockStateProperties.HORIZONTAL_FACING), blockState.getValue(BlockStateProperties.EXTENDED))) {
            Pair(Direction.NORTH, true) -> BoatyardShapes.LEFT_NORTH
            Pair(Direction.EAST, true) -> BoatyardShapes.LEFT_EAST
            Pair(Direction.SOUTH, true) -> BoatyardShapes.LEFT_SOUTH
            Pair(Direction.WEST, true) -> BoatyardShapes.LEFT_WEST

            Pair(Direction.NORTH, false) -> BoatyardShapes.RIGHT_NORTH
            Pair(Direction.EAST, false) -> BoatyardShapes.RIGHT_EAST
            Pair(Direction.SOUTH, false) -> BoatyardShapes.RIGHT_SOUTH
            Pair(Direction.WEST, false) -> BoatyardShapes.RIGHT_WEST

            else -> BoatyardShapes.LEFT_NORTH
        }
    }

    companion object {
        fun getOtherSide(blockPos: BlockPos, blockState: BlockState): BlockPos {
            if (blockState.block !is BoatyardBlock)
                logger.warn("An invalid blockState has been given to 'BoatyardBlock.getOtherSide'")

            val openSide = blockState.getValue(BlockStateProperties.HORIZONTAL_FACING).let {
                if (blockState.getValue(BlockStateProperties.EXTENDED))
                    it.counterClockWise
                else
                    it.clockWise
            }

            return blockPos.relative(openSide)
        }
    }
}