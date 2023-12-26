package cc.tweaked_programs.partnership.main.block.cannon

import com.mojang.serialization.MapCodec
import net.minecraft.world.level.block.HorizontalDirectionalBlock
import net.minecraft.world.level.block.state.BlockBehaviour

class MarineCannonBlock(properties: Properties) : HorizontalDirectionalBlock(properties) {

    override fun codec(): MapCodec<out HorizontalDirectionalBlock> = BlockBehaviour.simpleCodec(::MarineCannonBlock)


}