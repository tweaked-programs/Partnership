package cc.tweaked_programs.partnership.main.registries

import cc.tweaked_programs.partnership.main.MOD_ID
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack

object GroupRegistries {

    private val group = FabricItemGroup.builder()
        .icon { ItemStack(ItemRegistries.BOATYARD) }
        .title(Component.translatable("itemGroup.$MOD_ID"))
        .build()

    init {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ResourceLocation(MOD_ID, "item_group"), group)

        val groupRegistryKey = ResourceKey.create(
            BuiltInRegistries.CREATIVE_MODE_TAB.key(),
            ResourceLocation(MOD_ID, "item_group")
        )

        ItemGroupEvents.modifyEntriesEvent(groupRegistryKey).register { group ->
            ItemRegistries.registered.forEach { item -> group.prepend(item) }
        }
    }
}