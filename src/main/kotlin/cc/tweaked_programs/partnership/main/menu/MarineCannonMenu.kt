package cc.tweaked_programs.partnership.main.menu

import cc.tweaked_programs.partnership.main.registries.MenuRegistries
import net.minecraft.core.BlockPos
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.item.ItemStack

class MarineCannonMenu(syncId: Int, player: Inventory, val pos: BlockPos = BlockPos.ZERO) : AbstractContainerMenu(MenuRegistries.MARINE_CANNON, syncId){

    constructor(syncId: Int, player: Inventory, buf: FriendlyByteBuf) : this(syncId, player, buf.readBlockPos())

    override fun quickMoveStack(player: Player, i: Int): ItemStack = ItemStack.EMPTY

    override fun stillValid(player: Player): Boolean = true
}