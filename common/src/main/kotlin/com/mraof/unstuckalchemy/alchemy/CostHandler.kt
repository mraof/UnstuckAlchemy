package com.mraof.unstuckalchemy.alchemy

import net.minecraft.server.MinecraftServer

/**
 * Created by mraof on 2019 November 18 at 5:11 PM.
 */
interface CostHandler {
    fun addCosts(costs: CostMap, server: MinecraftServer) {}
    fun calculateCost(stack: SimpleStack): Elements? = null
}

