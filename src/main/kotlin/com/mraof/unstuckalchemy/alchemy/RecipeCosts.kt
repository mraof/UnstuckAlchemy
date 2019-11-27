package com.mraof.unstuckalchemy.alchemy

import com.mraof.unstuckalchemy.UnstuckAlchemy.logger
import com.mraof.unstuckalchemy.mixin.IngredientHooks
import net.minecraft.item.Items
import net.minecraft.recipe.*
import net.minecraft.server.MinecraftServer

object RecipeCosts : CostHandler {
    override fun addCosts(costs: CostMap, server: MinecraftServer) {
        val recipeManager = server.recipeManager
        for (recipe in recipeManager.values()) {
            if (recipe is ShapelessRecipe || recipe is ShapedRecipe || recipe is SmeltingRecipe || recipe is CuttingRecipe) {
                val output = recipe.output
                val outputCost = costs[SimpleStack(output.item, output.tag)]
                if (outputCost.value.elements.isNotEmpty()) {
                    continue
                }
                logger.info("Recipe for %s", output)
                val ingredients = ArrayList<List<Cost>>()
                for (input in recipe.previewInputs) {
                    input.ids
                    @Suppress("CAST_NEVER_SUCCEEDS")
                    val stackArray = (input as IngredientHooks).stackArrayUnsided
                    //TODO Handle multiple stacks from tags
                    if (stackArray.size > 1) {
                        val stackList = ArrayList<Cost>()
                        var count = 0
                        for (stack in stackArray) {
                            if (stack.item != Items.AIR) {
                                val cost = costs[SimpleStack(stack.item, stack.tag)]
                                logger.info("\t%s", stack)
                                if (count > 0 && stack.count != count) {
                                    logger.warn("Ingredient with different stack counts: %d, %d", count, stack.count)
                                }
                                count = stack.count
                                stackList.add(cost)
                            }
                        }
                        for (i in 0 until count) {
                            ingredients.add(stackList)
                        }
                    } else if (stackArray.size == 1) {
                        val stack = stackArray[0]
                        if (stack.item != Items.AIR) {
                            logger.info("\t%s", stack)
                            val cost = costs[SimpleStack(stack.item, stack.tag)]
                            for (i in 0 until stack.count) {
                                ingredients.add(listOf(cost))
                            }
                        }
                    }
                }
                if (recipe is AbstractCookingRecipe) {
                    ingredients.add(
                        listOf(
                            Cost(
                                Elements("energy", recipe.cookTime.toLong())
                            )
                        )
                    )
                }
                outputCost.addRecipe(recipe.id.toString(), ingredients, output.count)
            }
        }
    }
}
