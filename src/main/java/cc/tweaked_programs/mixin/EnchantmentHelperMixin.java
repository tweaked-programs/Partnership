package cc.tweaked_programs.mixin;

import cc.tweaked_programs.partnership.main.item.PaddleItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {

    @Redirect(
            method = "getKnockbackBonus",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;getEnchantmentLevel(Lnet/minecraft/world/item/enchantment/Enchantment;Lnet/minecraft/world/entity/LivingEntity;)I")
    )
    private static int partnership$baseKnockbackLevel$getKnockbackBonus(Enchantment enchantment, LivingEntity livingEntity) {
        ItemStack item = livingEntity.getMainHandItem();
        int base = 0;
        if (item.getItem() instanceof PaddleItem paddle)
            base = paddle.getKnockbackBaseLevel();

        return EnchantmentHelper.getEnchantmentLevel(Enchantments.KNOCKBACK, livingEntity) + base;
    }
}
