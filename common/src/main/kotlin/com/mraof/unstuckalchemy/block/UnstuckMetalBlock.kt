package com.mraof.unstuckalchemy.block

import me.shedaniel.architectury.registry.BlockProperties
import me.shedaniel.architectury.registry.ToolType
import net.minecraft.block.Block
import net.minecraft.block.Material

class UnstuckMetalBlock : Block {
    internal constructor(miningLevel: Int) : super(
        BlockProperties.of(Material.METAL).tool(ToolType.PICKAXE, miningLevel).strength(3.0f, 3.0f)
    )

    constructor(miningLevel: Int, lightLevel: Int) : super(
        BlockProperties.of(Material.METAL).tool(ToolType.PICKAXE, miningLevel).strength(3.0f, 3.0f)
            .luminance { lightLevel }
    )
}
