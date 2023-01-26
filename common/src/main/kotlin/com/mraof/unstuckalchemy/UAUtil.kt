package com.mraof.unstuckalchemy

import com.mraof.unstuckalchemy.item.UAItem
import me.shedaniel.architectury.ExpectPlatform
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.PotionItem
import net.minecraft.potion.Potion
import net.minecraft.recipe.Ingredient

/**
 * Created by mraof on 2021 March 20 at 2:50 AM.
 */
@Suppress("UNUSED_PARAMETER")
object UAUtil {
    @JvmStatic
    @ExpectPlatform
    fun getIngredientStacks(ingredient: Ingredient?): Array<out ItemStack> {
        throw AssertionError()
    }

    @JvmStatic
    @ExpectPlatform
    fun getPotionRecipes(): HashMap<Pair<Potion, Item>, Potion> {
        throw AssertionError()
    }

    @JvmStatic
    @ExpectPlatform
    fun getPotionItemRecipes(): HashMap<Pair<PotionItem, Item>, PotionItem> {
        throw AssertionError()
    }

    @JvmStatic
    @ExpectPlatform
    fun addFuel(fuel: UAItem, amount: Int) {
        throw AssertionError()
    }
}
