package com.mraof.unstuckalchemy.events

import com.mraof.unstuckalchemy.alchemy.Alchemy
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.minecraft.resource.ServerResourceManager
import net.minecraft.server.MinecraftServer

/**
 * Created by mraof on 2021 March 20 at 1:32 AM.
 */
object DataPackReloadListener : ServerLifecycleEvents.EndDataPackReload {
    override fun endDataPackReload(
        server: MinecraftServer?,
        serverResourceManager: ServerResourceManager?,
        success: Boolean
    ) {
        //Alchemy.calculateCosts(server!!)
    }
}
