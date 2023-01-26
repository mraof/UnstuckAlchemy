package com.mraof.unstuckalchemy

import com.mraof.unstuckalchemy.alchemy.Alchemy
import com.mraof.unstuckalchemy.block.UnstuckAlchemyBlocks
import me.shedaniel.architectury.event.events.TooltipEvent
import me.shedaniel.architectury.registry.CreativeTabs
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier
import org.apache.logging.log4j.LogManager

/**
 * Created by mraof on 2021 March 20 at 2:37 AM.
 */
object UnstuckAlchemy {
    const val MOD_ID = "unstuck_alchemy"
    @JvmField
    val logger = LogManager.getFormatterLogger("Unstuck Alchemy")!!
    @JvmField
    var BLOCK_GROUP = CreativeTabs.create(Identifier(MOD_ID, "blocks")) { ItemStack(UnstuckAlchemyBlocks.URANIUM_ORE) }

    init {
        TooltipEvent.ITEM.register(Alchemy::getTooltip)
    }
}
