package cc.tweaked_programs.partnership.main.block.cannon

import cc.tweaked_programs.partnership.main.registries.BlockEntityRegistries
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.ContainerHelper
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class MarineCannonBlockEntity(private val pos: BlockPos, val state: BlockState) : BlockEntity(BlockEntityRegistries.MARINE_CANNON, pos, state) {


    override fun saveAdditional(compoundTag: CompoundTag) {
        super.saveAdditional(compoundTag)
    }

    override fun load(compoundTag: CompoundTag) {
        super.load(compoundTag)
    }
}