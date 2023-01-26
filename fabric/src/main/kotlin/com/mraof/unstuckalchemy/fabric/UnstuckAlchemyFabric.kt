package com.mraof.unstuckalchemy.fabric

import com.mraof.unstuckalchemy.UnstuckAlchemy.logger
import com.mraof.unstuckalchemy.alchemy.Alchemy
import com.mraof.unstuckalchemy.alchemy.InitialCosts
import com.mraof.unstuckalchemy.alchemy.TechRebornCosts
import com.mraof.unstuckalchemy.block.UnstuckAlchemyBlocks
import com.mraof.unstuckalchemy.events.DataPackReloadListener
import com.mraof.unstuckalchemy.events.ServerStartedListener
import com.mraof.unstuckalchemy.item.UnstuckAlchemyItems
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.resource.ReloadableResourceManager

object UnstuckAlchemyFabric : ModInitializer {
    override fun onInitialize() {
        logger.info("Hello Fabric world!")
        UnstuckAlchemyBlocks.register()
        UnstuckAlchemyItems.register()
        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register(DataPackReloadListener)
        ServerLifecycleEvents.SERVER_STARTED.register(ServerStartedListener)
        if(FabricLoader.getInstance().isModLoaded("techreborn")) {
            Alchemy.handlers.add(TechRebornCosts)
        }
    }

    @JvmStatic
    fun registerServerResources(resourceManager: ReloadableResourceManager) {
        resourceManager.registerListener(InitialCosts)
    }
}
