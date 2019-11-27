package com.mraof.unstuckalchemy.block;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tools.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderLayer;
import net.minecraft.block.Material;

public class UnstuckMetalBlock extends Block {
    UnstuckMetalBlock(int miningLevel) {
        super(FabricBlockSettings.of(Material.METAL).strength(3.0F, 3.0F).breakByTool(FabricToolTags.PICKAXES, miningLevel).build());
    }

    public UnstuckMetalBlock(int miningLevel, int lightLevel) {
        super(FabricBlockSettings.of(Material.METAL).strength(3.0F, 3.0F).breakByTool(FabricToolTags.PICKAXES, miningLevel).lightLevel(lightLevel).build());
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.SOLID;
    }
}
