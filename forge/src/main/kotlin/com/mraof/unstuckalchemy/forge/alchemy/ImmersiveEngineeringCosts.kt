package com.mraof.unstuckalchemy.forge.alchemy

import blusunrize.immersiveengineering.api.crafting.AlloyRecipe
import blusunrize.immersiveengineering.api.crafting.ArcFurnaceRecipe
import blusunrize.immersiveengineering.api.crafting.BlueprintCraftingRecipe
import blusunrize.immersiveengineering.api.crafting.MultiblockRecipe
import com.mraof.unstuckalchemy.UnstuckAlchemy.logger
import com.mraof.unstuckalchemy.alchemy.*
import net.minecraft.item.Items
import net.minecraft.server.MinecraftServer
import net.minecraft.util.Identifier

/**
 * Created by mraof on 2021 March 21 at 6:38 AM.
 */
object ImmersiveEngineeringCosts : CostHandler {
    override fun addCosts(costs: CostMap, server: MinecraftServer) {
        // Alloy smelter
        for ((id, recipe) in AlloyRecipe.recipeList) {
            logger.info(recipe)
            val output = recipe.output
            val outputCost = costs[SimpleStack(output.item, output.tag)]
            val ingredient0 = ArrayList<Pair<Cost, Int>>()
            for(stack in recipe.input0.matchingStacks) {
                if (stack.item != Items.AIR) {
                    val cost = costs[SimpleStack(stack.item, stack.tag)]
                    ingredient0.add(Pair(cost, stack.count))
                }
            }
            val ingredient1 = ArrayList<Pair<Cost, Int>>()
            for(stack in recipe.input1.matchingStacks) {
                if (stack.item != Items.AIR) {
                    val cost = costs[SimpleStack(stack.item, stack.tag)]
                    ingredient1.add(Pair(cost, stack.count))
                }
            }
            outputCost.addRecipe(CostRecipe(id.toString(), listOf(ingredient0, ingredient1), output.count))
        }
        addMultiblockRecipes(costs, ArcFurnaceRecipe.recipeList)
        addMultiblockRecipes(costs, BlueprintCraftingRecipe.recipeList)
    }
}

fun addMultiblockRecipes(costs: CostMap, recipeList: Map<Identifier, MultiblockRecipe>) {
    for((id, recipe) in recipeList) {
        //TODO There's fluid outputs too- figure that out
        val output = recipe.output
        val outputCost = costs[SimpleStack(output.item, output.tag)]
        val ingredients = ArrayList<List<Pair<Cost, Int>>>()
        for(input in recipe.itemInputs) {
            val ingredient = ArrayList<Pair<Cost, Int>>()
            for (stack in input.matchingStacks) {
                if (stack.item != Items.AIR) {
                    val cost = costs[SimpleStack(stack.item, stack.tag)]
                    ingredient.add(Pair(cost, stack.count))
                }
            }
            ingredients.add(ingredient)
        }
        if(recipe.fluidInputs != null) {
            for (input in recipe.fluidInputs) {
                val ingredient = ArrayList<Pair<Cost, Int>>()
                for (stack in input.matchingFluidStacks) {
                    val cost = costs[SimpleStack(stack.fluid, stack.tag)]
                    ingredient.add(Pair(cost, stack.amount))
                }
                ingredients.add(ingredient)
            }
        }
        outputCost.addRecipe(CostRecipe(id.toString(), ingredients, output.count))
    }
}
