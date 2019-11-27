package com.mraof.unstuckalchemy.alchemy

import com.mraof.unstuckalchemy.UnstuckAlchemy.logger
import com.mraof.unstuckalchemy.events.DataPackReloadCallback
import com.mraof.unstuckalchemy.events.ItemStackTooltipCallback
import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.server.MinecraftServer
import net.minecraft.text.LiteralText
import net.minecraft.text.Text
import kotlin.math.roundToLong

/**
 * Created by mraof on 2019 November 18 at 5:02 PM.
 */
object Alchemy : DataPackReloadCallback, ItemStackTooltipCallback {
    val handlers = mutableListOf(InitialCosts, RecipeCosts, AxeStrippingCosts, PotionCosts, EnchantmentCosts, DamagedCosts)
    val costs = CostMap()
    override fun onReload(server: MinecraftServer?) {
        for (handler in handlers) {
            handler.addCosts(costs, server!!)
        }

        var unknownCosts = 0
        val baseIngredients = ArrayList<SimpleStack>()
        for ((stack, cost) in costs) {
            when {
                cost.sumIngredients() -> {
                    logger.info("%s: %s", stack, cost.value, cost)
                    val lost = cost.value.elements.filterValues { it % 1 != 0.0 }
                    if (lost.isNotEmpty()) {
                        logger.warn("Decimal parts lost for %s: %s", stack, lost)
                    }
                }
                cost.ingredients.isEmpty() -> baseIngredients.add(stack)
                else -> {
                    logger.info("Non base unknown: %s", stack)
                    unknownCosts++
                }
            }
        }
        baseIngredients.sortBy { -costs[it].usages }
        logger.info("Base ingredients %s", baseIngredients)
        logger.info("Non base unknowns: %d", unknownCosts)
    }

    override fun onTooltip(
        stack: ItemStack?,
        playerEntity: PlayerEntity?,
        tooltipContext: TooltipContext?,
        returnValue: MutableList<Text>?
    ) {
        if (tooltipContext!!.isAdvanced) {
            val elements = getElements(SimpleStack(stack!!.item, stack.tag))
            if(elements != null) {
                for ((element, amount) in elements.elements) {
                    if (amount % 1 < 0.00001) {
                        returnValue!!.add(LiteralText("$element: ${amount.toLong()}"))
                    } else {
                        returnValue!!.add(LiteralText("$element: ${(amount * 100).roundToLong().toDouble() / 100.0}"))
                    }
                }
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
