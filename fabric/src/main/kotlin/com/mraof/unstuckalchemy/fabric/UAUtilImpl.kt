package com.mraof.unstuckalchemy.fabric

import com.mraof.unstuckalchemy.events.BrewingRecipeRegistryListener
import com.mraof.unstuckalchemy.item.UAItem
import com.mraof.unstuckalchemy.item.UnstuckAlchemyItems
import com.mraof.unstuckalchemy.mixin.IngredientHooks
import net.fabricmc.fabric.api.registry.FuelRegistry
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.PotionItem
import net.minecraft.potion.Potion
import net.minecraft.recipe.Ingredient

/**
 * Created by mraof on 2021 March 20 at 2:50 AM.
 */
object UAUtilImpl {
    @JvmStatic
    fun getIngredientStacks(ingredient: Ingredient?): Array<out ItemStack> {
        return (ingredient as IngredientHooks).stackArrayUnsided
    }

    @JvmStatic
    fun getPotionRecipes(): HashMap<Pair<Potion, Item>, Potion> {
        return BrewingRecipeRegistryListener.potionRecipes
    }

    @JvmStatic
    fun getPotionItemRecipes(): HashMap<Pair<PotionItem, Item>, PotionItem> {
        return BrewingRecipeRegistryListener.itemRecipes
    }

    @JvmStatic
    fun addFuel(fuel: UAItem, amount: Int) {
        FuelRegistry.INSTANCE.add(fuel, amount)
    }
}
