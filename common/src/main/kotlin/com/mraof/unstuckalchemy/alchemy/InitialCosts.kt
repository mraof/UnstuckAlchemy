package com.mraof.unstuckalchemy.alchemy

import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.mraof.unstuckalchemy.UnstuckAlchemy.logger
import me.shedaniel.architectury.platform.Platform
import net.minecraft.item.Items
import net.minecraft.resource.JsonDataLoader
import net.minecraft.resource.ResourceManager
import net.minecraft.server.MinecraftServer
import net.minecraft.util.Identifier
import net.minecraft.util.profiler.Profiler
import net.minecraft.util.registry.Registry

/**
 * Created by mraof on 2019 November 18 at 5:37 PM.
 */
object InitialCosts : CostHandler, JsonDataLoader(GsonBuilder().create(), "alchemy") {
    private val initialCosts = mutableMapOf<SimpleStack, Cost>()

    var server: MinecraftServer? = null

    override fun apply(
        jsons: MutableMap<Identifier, JsonElement>?,
        resourceManager: ResourceManager?,
        profiler: Profiler?
    ) {
        initialCosts.clear()
        for ((file, json) in jsons!!) {
            val mod = file.path
            if (Platform.isModLoaded(mod)) {
                for((type, typeArray) in json.asJsonObject.entrySet()) {
                    for (entry in typeArray.asJsonArray) {
                        val o = entry.asJsonObject
                        val id = o.get("id").asString
                        val elements = mutableMapOf<String, Double>()
                        for (element in o.getAsJsonArray("elements")) {
                            val name = element.asJsonArray[0].asString
                            val amount = element.asJsonArray[1].asDouble
                            elements[name] = amount
                        }
                        val cost = Cost(Elements(elements))
                        cost.recipe = "explicit"
                        when (type) {
                            "item" -> {
                                val item = Registry.ITEM[Identifier(mod, id)]
                                if (item != Items.AIR) {
                                    initialCosts[SimpleStack(item)] = cost
                                }
                            }
                            "tag" -> {
                                initialCosts[SimpleStack("tag:$mod:$id")] = cost
                            }
                            "fluid" -> {
                                val fluid = Registry.FLUID[Identifier(mod, id)]
                                initialCosts[SimpleStack(fluid)] = cost
                            }
                        }
                    }

                }
            }
        }

        if(server != null) {
            Alchemy.calculateCosts(server!!)
        }
    }

    override fun addCosts(costs: CostMap, server: MinecraftServer) {
        costs.putAll(initialCosts)
    }
}
