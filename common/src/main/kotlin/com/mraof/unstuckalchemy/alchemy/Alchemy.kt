package com.mraof.unstuckalchemy.alchemy

import com.mraof.unstuckalchemy.UnstuckAlchemy.logger
import net.minecraft.client.item.TooltipContext
import net.minecraft.item.ItemStack
import net.minecraft.server.MinecraftServer
import net.minecraft.tag.ItemTags
import net.minecraft.text.LiteralText
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import kotlin.math.roundToLong

/**
 * Created by mraof on 2019 November 18 at 5:02 PM.
 */
object Alchemy {
    val handlers = mutableListOf(InitialCosts, RecipeCosts, AxeStrippingCosts, PotionCosts, DamagedCosts, EnchantmentCosts)
    val costs = CostMap()

    fun calculateCosts(
        server: MinecraftServer,
    ) {
        logger.info("Data pack reloaded, calculating costs")
        for (handler in handlers) {
            handler.addCosts(costs, server)
        }

        // Tags are a last resort, add them if there's no other option
        for((id, tag) in ItemTags.getTagGroup().tags.toSortedMap(reverseOrder())) {
            logger.info("$id")
            val tagCost = costs.getCost(SimpleStack("tag:$id"))
            if(tagCost != null) {
                for(item in tag.values()) {
                    logger.info("$id: $item")
                    val itemCost = costs[SimpleStack(item)]
                    if(!itemCost.sumIngredients()) {
                        itemCost.value = tagCost.value
                        itemCost.recipe = "tag:$id"
                    }
                }
            }
        }

        var unknownCosts = 0
        val baseIngredients = ArrayList<SimpleStack>()
        for ((stack, cost) in costs) {
            when {
                cost.sumIngredients() -> {
                    //logger.info("%s: %s", stack, cost.value, cost)
                    val lost = cost.value.elements.filterValues { it % 1 != 0.0 }
                    if (lost.isNotEmpty()) {
                        logger.warn("Decimal parts lost for %s: %s", stack, lost)
                    }
                }
                cost.ingredients.isEmpty() -> baseIngredients.add(stack)
                else -> {
                    //logger.info("Non base unknown: %s", stack)
                    unknownCosts++
                }
            }
        }
        baseIngredients.sortBy { -costs[it].usages }
        logger.info("Base ingredients %s", baseIngredients)
        logger.info("Non base unknowns: %d", unknownCosts)
    }

    fun getTooltip(
        stack: ItemStack?,
        returnValue: MutableList<Text>?,
        tooltipContext: TooltipContext?,
    ) {
        if (tooltipContext!!.isAdvanced) {
            val simpleStack = SimpleStack(stack!!.item, stack.tag)
            val elements = getElements(simpleStack)
            if(elements != null) {
                for ((element, amount) in elements.elements) {
                    if (amount % 1 < 0.00001) {
                        returnValue!!.add(LiteralText("$element: ${amount.toLong()}"))
                    } else {
                        returnValue!!.add(LiteralText("$element: ${(amount * 100).roundToLong().toDouble() / 100.0}"))
                    }
                }
                val cost = costs.getCost(simpleStack)
                if(cost != null) {
                    returnValue!!.add(LiteralText("Recipe: ${cost.recipe}"))
                } else {
                    returnValue!!.add(LiteralText("No recipe"))
                }
            } else {
                returnValue!!.add(LiteralText("???"))
            }
        }
    }

    fun getElements(stack: SimpleStack): Elements? {
        val cost = costs.getCost(stack);
        if (cost != null && cost.value.elements.isNotEmpty()) {
            return cost.value
        }

        for (handler in handlers) {
            val elements = handler.calculateCost(stack)
            if (elements != null) {
                return elements
            }
        }

        return null
    }
}
