package com.mraof.unstuckalchemy.alchemy

import net.minecraft.block.Block
import net.minecraft.item.AxeItem
import net.minecraft.item.ToolMaterials
import net.minecraft.server.MinecraftServer

object AxeStrippingCosts : CostHandler {
    override fun addCosts(costs: CostMap, server: MinecraftServer) {
        for ((unstripped, stripped) in AxeStripping.strippedBlocks) {
            val ingredientCost = costs[SimpleStack(unstripped)]
            val outputCost = costs[SimpleStack(stripped)]
            outputCost.addRecipe("axe_stripping", ingredientCost)
        }
    }

    object AxeStripping : AxeItem(ToolMaterials.WOOD, 0F, 0F, Settings()) {
        val strippedBlocks: MutableMap<Block, Block> = STRIPPED_BLOCKS!!
    }
}
