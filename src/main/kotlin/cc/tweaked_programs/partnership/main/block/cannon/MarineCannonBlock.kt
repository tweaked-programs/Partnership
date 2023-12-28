package cc.tweaked_programs.partnership.main.block.cannon

import cc.tweaked_programs.partnership.main.registries.BlockEntityRegistries
import cc.tweaked_programs.partnership.main.registries.ItemRegistries
import com.mojang.serialization.MapCodec
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Items
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape


class MarineCannonBlock(properties: Properties) : BaseEntityBlock(properties), EntityBlock {

    override fun codec(): MapCodec<out BaseEntityBlock> = TODO("No thank you")

    init {
        registerDefaultState(stateDefinition.any()
            .setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH)
        )
    }

    override fun newBlockEntity(blockPos: BlockPos, blockState: BlockState): BlockEntity = MarineCannonBlockEntity(blockPos, blockState)

    override fun <T : BlockEntity> getTicker(level: Level, blockState: BlockState, blockEntityType: BlockEntityType<T>): BlockEntityTicker<T>?
    = if (level.isClientSide || blockEntityType != BlockEntityRegistries.MARINE_CANNON) null else BlockEntityTicker { level, blockPos, blockState, be: T ->
        MarineCannonBlockEntity.tick(level, blockPos, blockState, be as MarineCannonBlockEntity)
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun use(blockState: BlockState, level: Level, blockPos: BlockPos, player: Player,
                     interactionHand: InteractionHand, blockHitResult: BlockHitResult): InteractionResult {

        if (level.isClientSide) return InteractionResult.SUCCESS

        val mainHandItem = player.mainHandItem

        if (mainHandItem.isEmpty) {
            val menuProvider = blockState.getMenuProvider(level, blockPos)
            menuProvider?.let { player.openMenu(it) }
            return InteractionResult.SUCCESS
        }

        val blockEntity = level.getBlockEntity(blockPos)
        if (blockEntity !is MarineCannonBlockEntity)
            return InteractionResult.SUCCESS

        when (mainHandItem.item) {
            Items.GUNPOWDER -> {
                if (blockEntity.getPower() >= MarineCannonBlockEntity.MAX_POWER)
                    return InteractionResult.SUCCESS
                if (!player.isCreative)
                    mainHandItem.count -= 1

                blockEntity.loadOneGunpowder()
                level.playSound(
                    null,
                    blockPos,
                    SoundEvents.NOTE_BLOCK_SNARE.value(),
                    SoundSource.BLOCKS,
                    1f,
                    1F/3F * blockEntity.getPower()
                )
            }
            ItemRegistries.CANNONBALL -> {
                if (blockEntity.isLoaded())
                    return InteractionResult.SUCCESS
                if (!player.isCreative)
                    mainHandItem.count -= 1

                blockEntity.fill()
                level.playSound(
                    null,
                    blockPos,
                    SoundEvents.DECORATED_POT_PLACE,
                    SoundSource.BLOCKS,
                    1f,
                    0.3F
                )
            }
        }

        return InteractionResult.SUCCESS
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun neighborChanged(blockState: BlockState, level: Level, blockPos: BlockPos, block: Block, blockPos2: BlockPos, bl: Boolean) {
        if (level.hasNeighborSignal(blockPos) || level.hasNeighborSignal(blockPos.below())) {
            val blockEntity = level.getBlockEntity(blockPos)
            if (blockEntity !is MarineCannonBlockEntity)
                return

            if (blockEntity.getPower() > 0 && blockEntity.isLoaded()) {
                blockEntity.prepareShoot()
            }
        }
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun isSignalSource(state: BlockState): Boolean = true

    @Suppress("OVERRIDE_DEPRECATION")
    override fun getMenuProvider(blockState: BlockState, level: Level, blockPos: BlockPos): MenuProvider? {
        val blockEntity = level.getBlockEntity(blockPos)
        return if (blockEntity is MarineCannonBlockEntity) blockEntity else null
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) { builder
        .add(BlockStateProperties.HORIZONTAL_FACING)
    }

    override fun getStateForPlacement(ctx: BlockPlaceContext): BlockState? = defaultBlockState()
        .setValue(BlockStateProperties.HORIZONTAL_FACING, if (ctx.player?.isCrouching == true) ctx.horizontalDirection.opposite else ctx.horizontalDirection)

    @Suppress("OVERRIDE_DEPRECATION", "DEPRECATION")
    override fun triggerEvent(blockState: BlockState, level: Level, blockPos: BlockPos, i: Int, j: Int): Boolean {
        super.triggerEvent(blockState, level, blockPos, i, j)
        val blockEntity = level.getBlockEntity(blockPos) ?: return false
        return blockEntity.triggerEvent(i, j)
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun rotate(blockState: BlockState, rotation: Rotation): BlockState {
        return blockState.setValue(HorizontalDirectionalBlock.FACING, rotation.rotate(blockState.getValue(HorizontalDirectionalBlock.FACING) as Direction)) as BlockState
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun mirror(blockState: BlockState, mirror: Mirror): BlockState {
        return blockState.rotate(mirror.getRotation(blockState.getValue(HorizontalDirectionalBlock.FACING) as Direction))
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun getRenderShape(blockState: BlockState): RenderShape = RenderShape.INVISIBLE

    @Suppress("OVERRIDE_DEPRECATION")
    override fun getShape(blockState: BlockState, blockGetter: BlockGetter, blockPos: BlockPos,
                          collisionContext: CollisionContext
    ): VoxelShape = shape

    companion object {
        private val shape: VoxelShape = Shapes.empty()
            .let { Shapes.or(it, Shapes.create(0.31875, 0.31875, 0.31875, 0.68125, 0.68125, 0.68125)) }
    }
}