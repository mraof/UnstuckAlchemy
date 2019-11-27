package com.mraof.unstuckalchemy.mixin;

import com.mraof.unstuckalchemy.events.ItemStackTooltipCallback;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

/**
 * Created by mraof on 2019 November 21 at 8:27 PM.
 */
@Mixin(ItemStack.class)
public class ItemStackMixin {
    @Inject(at = @At("RETURN"), method = "getTooltip")
    public void onTooltip(PlayerEntity playerEntity, TooltipContext tooltipContext, CallbackInfoReturnable<List<Text>> cir) {
        ItemStackTooltipCallback.EVENT.invoker().onTooltip((ItemStack) (Object) this, playerEntity, tooltipContext, cir.getReturnValue());
    }
}
