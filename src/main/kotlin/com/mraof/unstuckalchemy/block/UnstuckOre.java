package com.mraof.unstuckalchemy.block;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tools.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderLayer;
import net.minecraft.block.Material;

public class UnstuckOre extends Block {
    UnstuckOre(int miningLevel) {
        super(FabricBlockSettings.of(Material.STONE).strength(3.0F, 3.0F).breakByTool(FabricToolTags.PICKAXES, miningLevel).build());
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }
}
