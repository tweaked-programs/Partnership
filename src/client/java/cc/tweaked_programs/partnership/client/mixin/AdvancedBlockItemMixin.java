package cc.tweaked_programs.partnership.client.mixin;

import cc.tweaked_programs.partnership.main.item.AdvancedItemDescription;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AdvancedItemDescription.class)
public abstract class AdvancedBlockItemMixin {
    private boolean isHoldingShift() {
        return Screen.hasShiftDown();
    }
}
