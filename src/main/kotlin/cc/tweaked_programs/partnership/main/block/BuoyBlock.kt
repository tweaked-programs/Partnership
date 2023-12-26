package cc.tweaked_programs.partnership.main.block

import com.mojang.serialization.MapCodec
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.BushBlock
import net.minecraft.world.level.block.IceBlock
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.Fluids
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

class BuoyBlock(properties: Properties) : BushBlock(properties) {
    override fun codec(): MapCodec<out BushBlock> = BlockBehaviour.simpleCodec(::BuoyBlock)

    override fun mayPlaceOn(blockState: BlockState, blockGetter: BlockGetter, blockPos: BlockPos): Boolean {
        val fluidState = blockGetter.getFluidState(blockPos)
        val fluidState2 = blockGetter.getFluidState(blockPos.above())
        return (fluidState.type === Fluids.WATER || blockState.block is IceBlock) && fluidState2.type === Fluids.EMPTY
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun getShape(blockState: BlockState, blockGetter: BlockGetter, blockPos: BlockPos,
                          collisionContext: CollisionContext): VoxelShape = shape

    @Suppress("OVERRIDE_DEPRECATION")
    override fun getVisualShape(
        blockState: BlockState,
        blockGetter: BlockGetter,
        blockPos: BlockPos,
        collisionContext: CollisionContext
    ): VoxelShape = Shapes.empty()

    @Suppress("OVERRIDE_DEPRECATION")
    override fun getShadeBrightness(blockState: BlockState, blockGetter: BlockGetter, blockPos: BlockPos): Float = 1.0f

    override fun propagatesSkylightDown(blockState: BlockState, blockGetter: BlockGetter, blockPos: BlockPos): Boolean = true

    @Suppress("OVERRIDE_DEPRECATION")
    override fun skipRendering(blockState: BlockState, blockState2: BlockState, direction: Direction): Boolean
        = if (blockState2.`is`(this)) true else super.skipRendering(blockState, blockState2, direction)
    
    companion object {
        private val shape: VoxelShape = Shapes.empty()
            .let { Shapes.or(it, Shapes.create(0.125, -0.1875, 0.125, 0.875, -0.125, 0.875)) }
            .let { Shapes.or(it, Shapes.create(0.0625, -0.125, 0.0625, 0.9375, 0.25, 0.9375)) }
            .let { Shapes.or(it, Shapes.create(0.1875, 0.25, 0.1875, 0.8125, 1.0, 0.8125)) }
    }
}