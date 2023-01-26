package com.mraof.unstuckalchemy.forge

import com.mraof.unstuckalchemy.UnstuckAlchemy
import com.mraof.unstuckalchemy.UnstuckAlchemy.logger
import com.mraof.unstuckalchemy.alchemy.Alchemy
import com.mraof.unstuckalchemy.alchemy.InitialCosts
import com.mraof.unstuckalchemy.block.UnstuckAlchemyBlocks
import com.mraof.unstuckalchemy.forge.alchemy.BucketCosts
import com.mraof.unstuckalchemy.forge.alchemy.ImmersiveEngineeringCosts
import com.mraof.unstuckalchemy.item.UnstuckAlchemyItems
import me.shedaniel.architectury.platform.forge.EventBuses
import net.minecraftforge.event.AddReloadListenerEvent
import net.minecraftforge.fml.ModList
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.event.server.FMLServerStartedEvent
import net.minecraftforge.fml.event.server.FMLServerStoppedEvent
import thedarkcolour.kotlinforforge.KotlinModLoadingContext
import thedarkcolour.kotlinforforge.forge.FORGE_BUS
import thedarkcolour.kotlinforforge.forge.MOD_BUS

/**
 * Created by mraof on 2021 March 20 at 6:02 PM.
 */
@Mod(UnstuckAlchemy.MOD_ID)
object UnstuckAlchemyForge {
    init {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(UnstuckAlchemy.MOD_ID, KotlinModLoadingContext.get().getKEventBus())
        MOD_BUS.addListener(::setup)
        FORGE_BUS.addListener(::onServerStarted)
        FORGE_BUS.addListener(::onServerStop)
        FORGE_BUS.addListener(::onDatapackReload)
        UnstuckAlchemyBlocks.register()
        UnstuckAlchemyItems.register()
        if(ModList.get().isLoaded("immersiveengineering")) {
            Alchemy.handlers.add(ImmersiveEngineeringCosts)
        }
        Alchemy.handlers.add(BucketCosts)
    }

    private fun setup(event: FMLCommonSetupEvent) {
        logger.info("FML setup event called")
    }

    private fun onDatapackReload(event: AddReloadListenerEvent) {
        event.addListener(InitialCosts)
    }

    private fun onServerStarted(event: FMLServerStartedEvent) {
        logger.info("Server started")
        Alchemy.calculateCosts(event.server)
    }

    private fun onServerStop(event: FMLServerStoppedEvent) {
        InitialCosts.server = null;
    }
}
