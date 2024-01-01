package cc.tweaked_programs.mixin;

import cc.tweaked_programs.partnership.main.item.PaddleItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Player.class)
public abstract class PlayerMixin {

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
