package cc.tweaked_programs.partnership.main.registries

import cc.tweaked_programs.partnership.main.MOD_ID
import cc.tweaked_programs.partnership.main.entity.Kayak
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricEntityTypeBuilder
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityDimensions
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory

object EntityRegistries {
    private fun <E: Entity, T: EntityType<E>> create(name: String, type: T): T {
        return Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            ResourceLocation(MOD_ID, name),
            type
        )
    }

    val KAYAK: EntityType<Kayak> = create(
        name = "kayak",
        type = FabricEntityTypeBuilder.create(MobCategory.MISC, ::Kayak)
            .dimensions(EntityDimensions(1.6F, 0.6F, false))
            .build()
    )
}