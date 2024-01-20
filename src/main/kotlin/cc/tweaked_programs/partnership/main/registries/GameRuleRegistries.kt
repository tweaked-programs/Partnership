package cc.tweaked_programs.partnership.main.registries

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry
import net.minecraft.world.level.GameRules

object GameRuleRegistries {
    val BOAT_DESPAWN_TIMER: GameRules.Key<GameRules.IntegerValue> = GameRuleRegistry.register("boatDespawnTimer", GameRules.Category.MISC, GameRuleFactory.createIntRule(-1))
}