package com.mraof.unstuckalchemy.block

import me.shedaniel.architectury.registry.BlockProperties
import me.shedaniel.architectury.registry.ToolType
import net.minecraft.block.Block
import net.minecraft.block.Material

class UnstuckOre internal constructor(miningLevel: Int) : Block(
    BlockProperties.of(Material.STONE).tool(ToolType.PICKAXE, miningLevel).strength(3.0f, 3.0f)
)
