package com.mraof.unstuckalchemy.alchemy

import com.mraof.unstuckalchemy.UnstuckAlchemy.logger
import net.minecraft.fluid.Fluids
import net.minecraft.server.MinecraftServer
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import reborncore.common.crafting.RebornFluidRecipe
import reborncore.common.crafting.RebornRecipe
import reborncore.common.crafting.ingredient.FluidIngredient
import reborncore.common.fluid.container.ItemFluidInfo
import techreborn.api.recipe.recipes.FluidReplicatorRecipe
import techreborn.api.recipe.recipes.FusionReactorRecipe

/**
 * Created by mraof on 2019 November 20 at 4:45 AM.
 */
object TechRebornCosts : CostHandler {
    override fun addCosts(costs: CostMap, server: MinecraftServer) {
        val recipeManager = server.recipeManager
        for (recipe in recipeManager.values()) {
            if (recipe is RebornRecipe) {
                if(recipe is FluidReplicatorRecipe) {
                    continue
                }
                logger.info(
                    "TechReborn recipe for %s (%s)",
                    recipe.outputs.map {
                        var s = it.toString()
                        if (it.tag != null) {
                            s += " ${it.tag}"
                        }
                        s
                    }, recipe.javaClass.simpleName)
                var energy = recipe.power * recipe.time
                if (recipe is FusionReactorRecipe) {
                    energy += recipe.startEnergy
                }

                var outputFluidHolders = 0
                var outputCount = 0
                for (output in recipe.outputs) {
                    outputCount += output.count
                    if (output.item is ItemFluidInfo) {
                        outputFluidHolders += output.count
                    }
                }
                val totalCost = mutableListOf<List<Pair<Cost, Int>>>()
                if (energy > 0) {
                    totalCost.add(listOf(Pair(Cost(Elements("energy", energy.toLong())), 1)))
                }
                for (ingredient in recipe.rebornIngredients) {
                    val ingredientCosts = mutableSetOf<Pair<Cost, Int>>()
                    if (ingredient is FluidIngredient) {
                        //A simple method call would be better but I'm not sure about adding a mixin for tech reborn
                        val json = ingredient.toJson()
                        val fluid = Registry.FLUID.get(Identifier(json.get("fluid").asString))
                        //TODO Currently just assuming the fluid is one bucket's worth
                        val stack = SimpleStack(fluid)
                        logger.info("\t%d %s", ingredient.count, stack)
                        val cost = costs[stack]
                        ingredientCosts.add(Pair(cost, 1000))
                        outputFluidHolders -= ingredient.count
                    } else {
                        val stacks = ingredient.previewStacks
                        if (stacks.isNotEmpty()) {
                            val firstStack = stacks[0].item
                            if (outputFluidHolders > 0 && firstStack is ItemFluidInfo && firstStack.getFluid(stacks[0]) == Fluids.EMPTY) {
                                outputFluidHolders -= stacks[0].count
                                logger.info("\tFluid holder: %s, %s", stacks[0], stacks[0].tag)
                            } else {
                                for (itemStack in stacks) {
                                    val stack = SimpleStack(itemStack.item, itemStack.tag)
                                    logger.info("\t%d %s", itemStack.count, stack)
                                    ingredientCosts.add(Pair(costs[stack], itemStack.count))
                                }
                            }
                        }
                    }
                    totalCost.add(ingredientCosts.toList())
                }

                if (recipe is RebornFluidRecipe) {
                    val fluid = recipe.fluidInstance
                    val stack = SimpleStack(fluid.fluid)
                    logger.info("\tRecipe fluid: %s", stack)
                    val cost = costs[stack]
                    totalCost.add(listOf(Pair(cost, fluid.amount)))
                }

                if (outputFluidHolders > 0) {
                    logger.warn("Recipe %s output has %d more fluid holders than input", recipe.id, outputFluidHolders)
                }

                for (output in recipe.outputs) {
                    val item = output.item
                    var multiplier = 1
                    val stack = if (item is ItemFluidInfo) {
                        val fluid = item.getFluid(output)
                        if (fluid != Fluids.EMPTY) {
                            multiplier = 1000
                            SimpleStack(fluid)
                        } else {
                            SimpleStack(item, output.tag)
                        }
                    } else {
                        SimpleStack(output.item, output.tag)
                    }
                    logger.info("Makes %d/%d %s", multiplier, outputCount, stack)
                    val cost = costs[stack]
                    cost.addRecipe(CostRecipe(recipe.id.toString(), totalCost, outputCount * multiplier))
                }
            }
        }

        for (item in Registry.ITEM) {
            if (item is ItemFluidInfo) {
                val emptyCostList = listOf(
                    Pair(costs[SimpleStack(item.empty.item, item.empty.tag)], 1),
                    Pair(costs[SimpleStack(item.empty.item)], 1)
                )
                for (fluid in Registry.FLUID) {
                    if (fluid != Fluids.EMPTY) {
                        val full = item.getFull(fluid)
                        val fluidCost = costs[SimpleStack(fluid)]
                        val ingredients = listOf(emptyCostList, listOf(Pair(fluidCost, 1000)))
                        val cost = costs[SimpleStack(full.item, full.tag)]
                        cost.addRecipe(CostRecipe("techreborn_fluidinfo:${Registry.FLUID.getId(fluid)}", ingredients))
                    }
                }
            }
        }
    }
}
