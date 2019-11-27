package com.mraof.unstuckalchemy

import com.mraof.unstuckalchemy.alchemy.Alchemy
import com.mraof.unstuckalchemy.alchemy.InitialCosts
import com.mraof.unstuckalchemy.alchemy.TechRebornCosts
import com.mraof.unstuckalchemy.block.UnstuckAlchemyBlocks
import com.mraof.unstuckalchemy.events.DataPackReloadCallback
import com.mraof.unstuckalchemy.events.ItemStackTooltipCallback
import com.mraof.unstuckalchemy.item.UnstuckAlchemyItems
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.item.ItemStack
import net.minecraft.server.MinecraftServer
import net.minecraft.util.Identifier
import org.apache.logging.log4j.LogManager

// For support join https://discord.gg/v6v4pMv

object UnstuckAlchemy : ModInitializer {
    const val MOD_ID = "unstuck_alchemy"
    @JvmField
    val logger = LogManager.getFormatterLogger("Unstuck Alchemy")!!
    @JvmField
    val BLOCK_GROUP =
        FabricItemGroupBuilder.build(Identifier(MOD_ID, "blocks")) { ItemStack(UnstuckAlchemyBlocks.URANIUM_ORE) }!!

    override fun onInitialize() {
        logger.info("Hello Fabric world!")
        UnstuckAlchemyBlocks.register()
        UnstuckAlchemyItems.register()
        DataPackReloadCallback.EVENT.register(Alchemy)
        ItemStackTooltipCallback.EVENT.register(Alchemy)
        if(FabricLoader.getInstance().isModLoaded("techreborn")) {
            Alchemy.handlers.add(TechRebornCosts)
        }
    }

    @JvmStatic
    fun onServerInit(server: MinecraftServer) {
        server.dataManager.registerListener(InitialCosts)
    }
}
