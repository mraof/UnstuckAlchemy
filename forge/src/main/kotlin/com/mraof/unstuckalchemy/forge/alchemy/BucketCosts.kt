package com.mraof.unstuckalchemy.forge.alchemy

import com.mraof.unstuckalchemy.alchemy.CostHandler
import com.mraof.unstuckalchemy.alchemy.CostMap
import com.mraof.unstuckalchemy.alchemy.CostRecipe
import com.mraof.unstuckalchemy.alchemy.SimpleStack
import net.minecraft.item.Items
import net.minecraft.server.MinecraftServer
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.FluidUtil
import net.minecraftforge.registries.ForgeRegistries

/**
 * Created by mraof on 2021 March 22 at 1:32 AM.
 */
object BucketCosts : CostHandler {
    override fun addCosts(costs: CostMap, server: MinecraftServer) {
        for((id, fluid) in ForgeRegistries.FLUIDS.entries) {
            val fluidCost = costs[SimpleStack(fluid)]
            val fluidBucket = FluidUtil.getFilledBucket(FluidStack(fluid, 1))
            val simpleStack = SimpleStack(fluidBucket.item, fluidBucket.tag)
            val bucketCost = costs[SimpleStack(Items.BUCKET)]
            val cost = costs[simpleStack]
            cost.addRecipe(CostRecipe("bucket:${id.value}", listOf(listOf(Pair(fluidCost, 1000)), listOf(Pair(bucketCost, 1)))))
        }
    }
}
