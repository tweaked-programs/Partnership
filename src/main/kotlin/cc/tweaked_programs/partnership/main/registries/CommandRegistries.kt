package cc.tweaked_programs.partnership.main.registries

import cc.tweaked_programs.partnership.main.level.command.MarkChunkAsSeaport
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback

object CommandRegistries {
    init {
        CommandRegistrationCallback.EVENT.register { dispatcher, commandBuildContext, commandSelection ->
            MarkChunkAsSeaport.register(dispatcher)
        }
    }
}