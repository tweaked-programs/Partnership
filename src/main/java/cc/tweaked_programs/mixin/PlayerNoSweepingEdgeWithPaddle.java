package cc.tweaked_programs.mixin;

import cc.tweaked_programs.partnership.main.item.PaddleItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Player.class)
public abstract class PlayerNoSweepingEdgeWithPaddle extends LivingEntity {

    protected PlayerNoSweepingEdgeWithPaddle(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Redirect(
            method = "attack",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getItem()Lnet/minecraft/world/item/Item;")
    )
    public Item partnership$noSweepingWithPaddle$attack(ItemStack instance) {
        if (instance.getItem() instanceof PaddleItem)
            return ItemStack.EMPTY.getItem();
        return instance.getItem();
    }
}
