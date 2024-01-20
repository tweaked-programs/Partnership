package cc.tweaked_programs.partnership.main.level.command

import cc.tweaked_programs.partnership.main.level.IsDockSaveData
import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.IntegerArgumentType.getInteger
import com.mojang.brigadier.arguments.IntegerArgumentType.integer
import com.mojang.brigadier.builder.LiteralArgumentBuilder.literal
import com.mojang.brigadier.builder.RequiredArgumentBuilder.argument
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType
import net.minecraft.commands.CommandSourceStack
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.ChunkPos


object MarkChunkAsSeaport {
    fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
        dispatcher.register(literal<CommandSourceStack?>("partnership")
            .requires { source -> source.hasPermission(3) }

            .then(literal<CommandSourceStack>("isChunkDockable")
                .executes { context ->
                    if (context.source.entity == null)
                        throw SimpleCommandExceptionType(Component.translatable("command.partnership.feedback.error.players_only")).create()

                    val data = context.source.level.dataStorage.computeIfAbsent(IsDockSaveData.FACTORY, IsDockSaveData.FILE_NAME)
                    val chunk = context.source.entity?.chunkPosition()!!
                    val anchors = data.get(chunk)

                    val pos = "[${chunk.x}, ${chunk.z}]"
                    val result = when (anchors) {
                        0 -> Component.translatable("command.partnership.feedback.dockableChunkStateNone").string.format(pos)
                        1 -> Component.translatable("command.partnership.feedback.dockableChunkStateOne").string.format(pos)
                        else -> Component.translatable("command.partnership.feedback.dockableChunkStateMul").string.format(pos, anchors)
                    }

                    context.source.sendSuccess({ Component.literal(result) } , true)
                    Command.SINGLE_SUCCESS
                }

                .then(argument<CommandSourceStack, Int>("anchors", integer())
                    .executes { context ->

                        if (!context.source.isPlayer)
                            throw SimpleCommandExceptionType(Component.translatable("command.partnership.feedback.error.players_only")).create()

                        val anchors = getInteger(context, "anchors")
                        val chunk = context.source.player!!.chunkPosition()
                        val pos = "[${chunk.x}, ${chunk.z}]"

                        setChunk(context.source.level, chunk, anchors)

                        val result = when (anchors) {
                            0 -> Component.translatable("command.partnership.feedback.dockableChunkStateNone").string.format(pos)
                            1 -> Component.translatable("command.partnership.feedback.dockableChunkStateOne").string.format(pos)
                            else -> Component.translatable("command.partnership.feedback.dockableChunkStateMul").string.format(pos, anchors)
                        }

                        context.source.sendSuccess({ Component.literal(result) } , true)
                        Command.SINGLE_SUCCESS
                    })
            )
        )
    }

    fun setChunk(level: ServerLevel, chunk: ChunkPos, anchors: Int) {
        val data = level.dataStorage.computeIfAbsent(IsDockSaveData.FACTORY, IsDockSaveData.FILE_NAME)

        if (anchors == 0)
            data.dockableChunks.remove(chunk.toLong())
        else data.dockableChunks[chunk.toLong()] = anchors

        data.setDirty()
        level.dataStorage.save()
    }

    fun getAnchorsOfChunk(level: ServerLevel, chunk: ChunkPos): Int {
        val data = level.dataStorage.computeIfAbsent(IsDockSaveData.FACTORY, IsDockSaveData.FILE_NAME)
        return data.get(chunk)
    }
}
