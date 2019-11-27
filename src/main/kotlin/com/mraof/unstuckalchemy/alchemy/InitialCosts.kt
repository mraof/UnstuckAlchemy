package com.mraof.unstuckalchemy.alchemy

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.item.Items
import net.minecraft.resource.JsonDataLoader
import net.minecraft.resource.ResourceManager
import net.minecraft.server.MinecraftServer
import net.minecraft.util.Identifier
import net.minecraft.util.profiler.Profiler
import net.minecraft.util.registry.Registry
import java.math.BigDecimal

/**
 * Created by mraof on 2019 November 18 at 5:37 PM.
 */
object InitialCosts : CostHandler, JsonDataLoader(GsonBuilder().create(), "alchemy") {
    private val initialCosts = mutableMapOf<SimpleStack, Cost>()
    override fun apply(
        jsons: MutableMap<Identifier, JsonObject>?,
        resourceManager: ResourceManager?,
        profiler: Profiler?
    ) {
        initialCosts.clear()
        for ((file, json) in jsons!!) {
            val mod = file.path
            if (FabricLoader.getInstance().isModLoaded(mod)) {
                for (entry in json.getAsJsonArray("")) {
                    val o = entry.asJsonObject
                    val id = o.get("id").asString
                    val item = Registry.ITEM[Identifier(mod, id)]
                    if (item != Items.AIR) {
                        val elements = mutableMapOf<String, Double>()
                        for (element in o.getAsJsonArray("elements")) {
                            val name = element.asJsonArray[0].asString
                            val amount = element.asJsonArray[1].asDouble
                            elements[name] = amount
                        }
                        initialCosts[SimpleStack(item)] = Cost(Elements(elements))
                    }
                }
            }
        }
    }

    override fun prepare(resourceManager: ResourceManager?, profiler: Profiler?): MutableMap<Identifier, JsonObject> {
        return method_20731(resourceManager, profiler)
    }

    override fun addCosts(costs: CostMap, server: MinecraftServer) {
        costs.putAll(initialCosts)
    }
}
