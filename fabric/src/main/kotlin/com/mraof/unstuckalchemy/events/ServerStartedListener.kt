package com.mraof.unstuckalchemy.events

import com.mraof.unstuckalchemy.alchemy.Alchemy
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.minecraft.server.MinecraftServer

/**
 * Created by mraof on 2021 March 20 at 1:59 AM.
 */
object ServerStartedListener : ServerLifecycleEvents.ServerStarted{
    override fun onServerStarted(server: MinecraftServer?) {
        // The data pack reload event should have been called instead if it's dedicated
        if(!server!!.isDedicated) {
            Alchemy.calculateCosts(server)
        }
    }
}
