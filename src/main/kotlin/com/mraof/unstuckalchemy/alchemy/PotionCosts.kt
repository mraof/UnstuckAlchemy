package com.mraof.unstuckalchemy.alchemy

import com.mraof.unstuckalchemy.events.BrewingRecipeRegistryListener
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.nbt.CompoundTag
import net.minecraft.potion.PotionUtil
import net.minecraft.potion.Potions
import net.minecraft.server.MinecraftServer

object PotionCosts : CostHandler {
    override fun addCosts(costs: CostMap, server: MinecraftServer) {
        val glassBottleCost = costs[SimpleStack(Items.GLASS_BOTTLE)]

        val waterTag = PotionUtil.setPotion(ItemStack(Items.POTION), Potions.WATER).tag
        val waterBottleCost = costs[SimpleStack(Items.POTION, waterTag)]
        waterBottleCost.addRecipe("code:water_bottle", glassBottleCost, Cost(Elements("water", 48)))

        val dragonBreathCost = costs[SimpleStack(Items.DRAGON_BREATH)]
        dragonBreathCost.addRecipe("code:dragon_breath", glassBottleCost, Cost(Elements("void", 4)), Cost(Elements("magic", 6)))

        addPotionItems(costs, waterTag, waterTag)

        for ((ingredients, result) in BrewingRecipeRegistryListener.potionRecipes) {
            val ingredientPotionTag = PotionUtil.setPotion(ItemStack(Items.POTION), ingredients.first).tag
            val ingredientItemCost = costs[SimpleStack(ingredients.second)]
            val resultPotionTag = PotionUtil.setPotion(ItemStack(Items.POTION), result).tag
            val ingredientPotionCost = costs[SimpleStack(Items.POTION, ingredientPotionTag)]
            val resultPotionCost = costs[SimpleStack(Items.POTION, resultPotionTag)]
            resultPotionCost.addRecipe("potion", ingredientPotionCost, ingredientItemCost)

            addPotionItems(costs, ingredientPotionTag, resultPotionTag)
        }
    }

    private fun addPotionItems(
        costs: CostMap,
        ingredientPotionTag: CompoundTag?,
        resultPotionTag: CompoundTag?
    ) {
        for ((itemIngredients, itemResult) in BrewingRecipeRegistryListener.itemRecipes) {
            val ingredientCost0 = costs[SimpleStack(itemIngredients.first, ingredientPotionTag)]
            val ingredientCost1 = costs[SimpleStack(itemIngredients.second)]
            val resultCost = costs[SimpleStack(itemResult, resultPotionTag)]
            resultCost.addRecipe("potion", ingredientCost0, ingredientCost1)
        }
    }
}
