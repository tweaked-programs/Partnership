package cc.tweaked_programs.partnership.main.recipe

import cc.tweaked_programs.partnership.main.block.boatyard.BoatyardBlockEntity.Companion.INV_SIZE
import cc.tweaked_programs.partnership.main.menu.inventory.ImplementedInventory
import cc.tweaked_programs.partnership.main.recipe.BoatyardRecipe.Companion.Type.Companion.CODEC
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.core.NonNullList
import net.minecraft.core.RegistryAccess
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.RecipeType
import net.minecraft.world.level.Level


class BoatyardRecipe(private val ingredients: MutableList<ItemStack> = MutableList(INV_SIZE-1) {ItemStack.EMPTY },
                     private val output: ItemStack = ItemStack.EMPTY
) : Recipe<ImplementedInventory> {

    override fun matches(inv: ImplementedInventory, level: Level): Boolean {
        if (level.isClientSide)
            return false

        ingredients.withIndex().forEach { slot ->
            if (slot.value.count > inv.getItem(slot.index).count
                || !slot.value.item.equals(inv.getItem(slot.index).item))
                return false
        }

        return true
    }

    override fun assemble(inv: ImplementedInventory, registryAccess: RegistryAccess): ItemStack = output.copy()

    override fun canCraftInDimensions(i: Int, j: Int): Boolean = true

    override fun getIngredients(): NonNullList<Ingredient> {
        val list = NonNullList.create<Ingredient>()
        list.addAll(ingredients.map { Ingredient.of(it) })
        return list
    }

    fun getIngredientsAsItemStacks(): NonNullList<ItemStack> {
        val list = NonNullList.create<ItemStack>()
        list.addAll(ingredients)
        return list
    }

    override fun getResultItem(registryAccess: RegistryAccess): ItemStack = output

    override fun getSerializer(): RecipeSerializer<*> = SERIALIZER

    override fun getType(): RecipeType<*> = Type.TYPE

    companion object {

        val SERIALIZER = Serializer()

        class Serializer : RecipeSerializer<BoatyardRecipe> {

            override fun codec(): Codec<BoatyardRecipe> = CODEC

            override fun fromNetwork(friendlyByteBuf: FriendlyByteBuf): BoatyardRecipe {
                val ingredients: MutableList<ItemStack> = MutableList(INV_SIZE-1) { ItemStack.EMPTY }

                repeat(INV_SIZE-1) { index ->
                    ingredients[index] = friendlyByteBuf.readItem()
                }
                val output: ItemStack = friendlyByteBuf.readItem()

                return BoatyardRecipe(ingredients, output)
            }

            override fun toNetwork(friendlyByteBuf: FriendlyByteBuf, recipe: BoatyardRecipe) {
                recipe.getIngredientsAsItemStacks().forEach { ingredient ->
                    friendlyByteBuf.writeItem(ingredient)
                }
                friendlyByteBuf.writeItem(recipe.getResultItem(RegistryAccess.EMPTY))
            }
        }

        class Type : RecipeType<BoatyardRecipe> {
            companion object {
                val TYPE: Type = Type()

                val CODEC: Codec<BoatyardRecipe> =
                    RecordCodecBuilder.create { instance: RecordCodecBuilder.Instance<BoatyardRecipe> -> instance.group(
                        ItemStack.CODEC.listOf().fieldOf("ingredients").forGetter { it.getIngredientsAsItemStacks() },
                        ItemStack.CODEC.fieldOf("output").forGetter { it.getResultItem(RegistryAccess.EMPTY) }
                    ).apply(instance) { output, ingredient ->
                        BoatyardRecipe(output, ingredient)
                    } }
            }
        }
    }
}
