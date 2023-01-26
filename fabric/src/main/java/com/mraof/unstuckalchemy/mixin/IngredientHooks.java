package com.mraof.unstuckalchemy.mixin;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * Created by mraof on 2019 November 17 at 7:59 PM.
 */
@Mixin(Ingredient.class)
public interface IngredientHooks {
    @Accessor("matchingStacks")
    ItemStack[] getStackArrayUnsided();
}
