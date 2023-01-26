package com.mraof.unstuckalchemy.block

import com.mraof.unstuckalchemy.UnstuckAlchemy
import com.mraof.unstuckalchemy.item.UnstuckAlchemyItems
import me.shedaniel.architectury.registry.DeferredRegister
import net.minecraft.block.Block
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.util.registry.Registry

/**
 * Created by mraof on 2019 November 16 at 11:27 PM.
 */
object UnstuckAlchemyBlocks {
    val BLOCKS = DeferredRegister.create(UnstuckAlchemy.MOD_ID, Registry.BLOCK_KEY)
    var URANIUM_ORE: Block = UnstuckOre(2)
    var URANIUM_BLOCK: Block = UnstuckMetalBlock(2, 5)
    var DEPLETED_URANIUM_BLOCK: Block = UnstuckMetalBlock(2)
    fun register() {
        registerStandard("uranium_ore", URANIUM_ORE)
        registerStandard("uranium_block", URANIUM_BLOCK)
        registerStandard("depleted_uranium_block", DEPLETED_URANIUM_BLOCK)

        BLOCKS.register()
    }

    private fun registerStandard(id: String, block: Block) {
        BLOCKS.register(id) { block }
        UnstuckAlchemyItems.registerItem(id, BlockItem(block, Item.Settings().group(UnstuckAlchemy.BLOCK_GROUP)))
    }
}
