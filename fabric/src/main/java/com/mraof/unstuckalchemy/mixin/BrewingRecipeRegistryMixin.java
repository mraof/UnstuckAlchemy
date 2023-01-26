package com.mraof.unstuckalchemy.mixin;

import com.mraof.unstuckalchemy.events.BrewingRecipeRegistryListener;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.recipe.BrewingRecipeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Created by mraof on 2019 November 17 at 7:59 PM.
 */
@Mixin(BrewingRecipeRegistry.class)
public class BrewingRecipeRegistryMixin {
    @Inject(at = @At("RETURN"), method = "registerItemRecipe")
    private static void afterRegisterItemRecipe(Item item, Item item2, Item item3, CallbackInfo info) {
        BrewingRecipeRegistryListener.INSTANCE.onRegisterItemRecipe(item, item2, item3);
    }

    @Inject(at = @At("RETURN"), method = "registerPotionRecipe")
    private static void afterRegisterPotionRecipe(Potion potion, Item item, Potion potion2, CallbackInfo info) {
        BrewingRecipeRegistryListener.INSTANCE.onRegisterPotionRecipe(potion, item, potion2);
    }
}
