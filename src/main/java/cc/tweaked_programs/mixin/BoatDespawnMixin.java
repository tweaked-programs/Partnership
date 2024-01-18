package cc.tweaked_programs.mixin;

import cc.tweaked_programs.partnership.main.registries.GameRuleRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.VehicleEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Boat.class)
abstract public class BoatDespawnMixin extends VehicleEntity implements VariantHolder<Boat.Type> {
    public BoatDespawnMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Unique
    @SuppressWarnings("WrongEntityDataParameterClass")
    private static final EntityDataAccessor<Integer> DATA_ID_DESPAWN = SynchedEntityData.defineId(Boat.class, EntityDataSerializers.INT);

    @Inject(at = @At("RETURN"), method = "<init>(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/level/Level;)V")
    public void partnership$defineDespawnTimer(EntityType<? extends Boat> entityType, Level level, CallbackInfo ci) {
        int timer = getDefinedDespawnTimer(level);
        if (timer < 0)
            timer = Integer.MAX_VALUE;

        setDespawnTimer(timer);
    }

    @Inject(at = @At("HEAD"), method = "tick")
    public void partnership$shouldDespawn(CallbackInfo ci) {
        int despawnTimer = getDespawnTimer();
        int definedDespawnTimer = getDefinedDespawnTimer(this.level());

        if (definedDespawnTimer >= 0) {
            // Boats should despawn
            if (this.getPassengers().isEmpty()) {
                // Countdown starts as boat is not being used anymore
                if (despawnTimer > 0) {
                    despawnTimer--;

                    // Make sure to stay in bounds
                    if (despawnTimer > definedDespawnTimer)
                        despawnTimer = definedDespawnTimer;

                    // Update
                    setDespawnTimer(despawnTimer);
                } else if (despawnTimer == 0) {
                    // Goodbye :)
                    this.discard();
                }
            } else if (despawnTimer >= 0) {
                // Reset timer
                setDespawnTimer(getDefinedDespawnTimer(this.level()));
            }
        }
    }

    @Inject(at = @At("TAIL"), method = "defineSynchedData")
    protected void partnership$defineDespawnDataId(CallbackInfo ci) {
        this.entityData.define(DATA_ID_DESPAWN, -1);
    }

    @Inject(at = @At("TAIL"), method = "addAdditionalSaveData")
    protected void partnership$addDespawnSaveData(CompoundTag compoundTag, CallbackInfo ci) {
        compoundTag.putInt("despawnTimer", getDespawnTimer());
    }

    @Inject(at = @At("TAIL"), method = "readAdditionalSaveData")
    protected void readAdditionalSaveData(CompoundTag compoundTag, CallbackInfo ci) {
        setDespawnTimer(compoundTag.getInt("despawnTimer"));
    }

    @Unique
    public void setDespawnTimer(int timer) {
        this.entityData.set(DATA_ID_DESPAWN, timer);
    }

    @Unique
    public int getDespawnTimer() {
        return this.entityData.get(DATA_ID_DESPAWN);
    }

    @Unique
    private int getDefinedDespawnTimer(Level level) {
        return level.getGameRules().getInt(GameRuleRegistries.INSTANCE.getBOAT_DESPAWN_TIMER());
    }
}
