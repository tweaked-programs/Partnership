package cc.tweaked_programs.partnership.client.compat.emi

import cc.tweaked_programs.partnership.main.MOD_ID
import cc.tweaked_programs.partnership.main.recipe.BoatyardRecipe
import dev.emi.emi.api.recipe.EmiRecipe
import dev.emi.emi.api.recipe.EmiRecipeCategory
import dev.emi.emi.api.render.EmiTexture
import dev.emi.emi.api.stack.EmiIngredient
import dev.emi.emi.api.stack.EmiStack
import dev.emi.emi.api.widget.WidgetHolder
import net.minecraft.core.RegistryAccess
import net.minecraft.resources.ResourceLocation

class BoatyardEmiRecipe(recipe: BoatyardRecipe) : EmiRecipe {

    private val input: MutableList<EmiIngredient> = recipe.getIngredientsAsItemStacks().map { EmiStack.of(it) }.toMutableList()
    private val output: MutableList<EmiStack> = mutableListOf(EmiStack.of(recipe.getResultItem(RegistryAccess.EMPTY)))
    private val id = recipe.getResultItem(RegistryAccess.EMPTY).item.asItem().descriptionId

    override fun getCategory(): EmiRecipeCategory = PartnershipEmi.BOATYARD_CATEGORY

    override fun getId() = ResourceLocation(MOD_ID, id)

    override fun getInputs() = input

    override fun getOutputs() = output

    override fun getDisplayWidth(): Int = 128

    override fun getDisplayHeight(): Int = 58

    override fun addWidgets(widgets: WidgetHolder) {
        val offset = 2

        widgets.addTexture(EmiTexture.EMPTY_ARROW, 79, (getDisplayHeight()/2)-(EmiTexture.EMPTY_ARROW.height/2)-1)

        repeat (3) { column ->
            repeat (4) { row ->
                widgets.addSlot(inputs[row + column * 4], offset + row * 18, offset + column * 18)
            }
        }

        widgets.addSlot(output[0], getDisplayWidth()-18-offset, getDisplayHeight()/2-9)
    }
}