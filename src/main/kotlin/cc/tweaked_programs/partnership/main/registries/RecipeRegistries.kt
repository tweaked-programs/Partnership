package cc.tweaked_programs.partnership.main.registries

import cc.tweaked_programs.partnership.main.MOD_ID
import cc.tweaked_programs.partnership.main.recipe.BoatyardRecipe
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation

object RecipeRegistries {

    init {
        Registry.register(
            BuiltInRegistries.RECIPE_SERIALIZER,
            ResourceLocation(MOD_ID, "boatyard_construct_serializer"),
            BoatyardRecipe.SERIALIZER
        )

        Registry.register(
            BuiltInRegistries.RECIPE_TYPE,
            ResourceLocation(MOD_ID, "boatyard_construct_recipe"),
            BoatyardRecipe.Companion.Type.TYPE
        )
    }
}