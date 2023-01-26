@file:Suppress("UNCHECKED_CAST", "unused")

package com.mraof.unstuckalchemy.forge

import com.mraof.unstuckalchemy.UnstuckAlchemy.logger
import com.mraof.unstuckalchemy.item.UAItem
import cpw.mods.modlauncher.api.INameMappingService
import me.shedaniel.architectury.platform.Platform
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.PotionItem
import net.minecraft.potion.Potion
import net.minecraft.recipe.BrewingRecipeRegistry
import net.minecraft.recipe.Ingredient
import net.minecraftforge.fml.common.ObfuscationReflectionHelper.findField
import net.minecraftforge.fml.common.ObfuscationReflectionHelper.remapName
import net.minecraftforge.registries.IRegistryDelegate
import java.lang.reflect.Field

/**
 * Created by mraof on 2021 March 20 at 2:50 AM.
 */
object UAUtilImpl {
    private val itemRecipes = HashMap<Pair<PotionItem, Item>, PotionItem>()
    private val potionRecipes = HashMap<Pair<Potion, Item>, Potion>()

    @JvmStatic
    fun getIngredientStacks(ingredient: Ingredient?): Array<out ItemStack> {
        //TODO Check if this really works on the server
        return ingredient!!.matchingStacksClient
    }

    @JvmStatic
    fun getPotionRecipes(): HashMap<Pair<Potion, Item>, Potion> {
        if (potionRecipes.isEmpty()) {
            for(recipe in BrewingRecipeRegistry.POTION_RECIPES) {
                val input = recipe.input.get()
                val output = recipe.output.get()
                val ingredient = recipe.ingredient
                for(stack in ingredient.matchingStacksClient) {
                    potionRecipes[Pair(input, stack.item)] = output
                }
            }
        }
        return potionRecipes
    }

    @JvmStatic
    fun getPotionItemRecipes(): HashMap<Pair<PotionItem, Item>, PotionItem> {
        if (itemRecipes.isEmpty()) {
            for(recipe in BrewingRecipeRegistry.ITEM_RECIPES) {
                val input = recipe.input.get()
                val output = recipe.output.get()
                val ingredient = recipe.ingredient
                for(stack in ingredient.matchingStacksClient) {
                    itemRecipes[Pair(input as PotionItem, stack.item)] = output as PotionItem
                }
            }
        }
        return itemRecipes
    }

    @JvmStatic
    fun addFuel(fuel: UAItem, amount: Int) {
        fuel.burnTime = amount
    }
}
