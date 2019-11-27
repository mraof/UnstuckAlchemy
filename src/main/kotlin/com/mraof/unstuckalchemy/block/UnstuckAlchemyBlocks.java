package com.mraof.unstuckalchemy.block;

import com.mraof.unstuckalchemy.UnstuckAlchemy;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * Created by mraof on 2019 November 16 at 11:27 PM.
 */
public class UnstuckAlchemyBlocks {
    public static Block URANIUM_ORE = new UnstuckOre(2);
    public static Block URANIUM_BLOCK = new UnstuckMetalBlock(2, 5);
    public static Block DEPLETED_URANIUM_BLOCK = new UnstuckMetalBlock(2);
    public static void register() {
        registerStandard("uranium_ore", URANIUM_ORE);
        registerStandard("uranium_block", URANIUM_BLOCK);
        registerStandard("depleted_uranium_block", DEPLETED_URANIUM_BLOCK);
    }

    private static void registerStandard(String id, Block block) {
        Registry.register(Registry.BLOCK, new Identifier(UnstuckAlchemy.MOD_ID, id), block);
        Registry.register(Registry.ITEM, new Identifier(UnstuckAlchemy.MOD_ID, id), new BlockItem(block, new Item.Settings().group(UnstuckAlchemy.BLOCK_GROUP)));
    }
}
