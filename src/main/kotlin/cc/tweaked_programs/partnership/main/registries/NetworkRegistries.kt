package cc.tweaked_programs.partnership.main.registries

import cc.tweaked_programs.partnership.main.block.cannon.MarineCannonBlockEntity
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking

object NetworkRegistries {

    init {
        ServerPlayNetworking.registerGlobalReceiver(MarineCannonBlockEntity.SYNC_ROT_NETWORK_ID) { minecraftServer, serverPlayer, serverGamePacketListenerImpl, friendlyByteBuf, packetSender ->
            minecraftServer.execute {
                val blockPos = friendlyByteBuf.readBlockPos()
                val xRot = friendlyByteBuf.readFloat()
                val yRot = friendlyByteBuf.readFloat()

                val blockEntity = serverPlayer.level().getBlockEntity(blockPos)
                if (blockEntity is MarineCannonBlockEntity)
                    blockEntity.setRotation(xRot, yRot)
            }
        }
    }
}