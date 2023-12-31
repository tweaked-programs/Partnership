package cc.tweaked_programs.mixin;

import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.VehicleEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Boat.class)
abstract public class BoatMixin extends VehicleEntity implements VariantHolder<Boat.Type> {
    public BoatMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean hurt(DamageSource damageSource, float f) {
        if (damageSource.is(DamageTypeTags.IS_PROJECTILE))
            return false;
        return super.hurt(damageSource, f);
    }
}
