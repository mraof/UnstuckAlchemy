package com.mraof.unstuckalchemy.alchemy

import net.minecraft.block.Block
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.nbt.CompoundTag
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

data class SimpleStack(val id: String, val tag: CompoundTag? = null) {
    constructor(fluid: Fluid) : this("fluid:" + Registry.FLUID.getId(fluid).toString())
    constructor(fluid: Fluid, tag: CompoundTag?) : this("fluid:" + Registry.FLUID.getId(fluid).toString(), tag)
    constructor(item: Item) : this("item:" + Registry.ITEM.getId(item).toString())
    constructor(item: Item, tag: CompoundTag?) : this("item:" + Registry.ITEM.getId(item).toString(), tag)
    constructor(block: Block) : this("item:" + Registry.ITEM.getId(block.asItem()).toString())

    override fun toString(): String {
        return if (tag != null) {
            "$id {$tag}"
        } else {
            id
        }
    }

    val item: Item?
        get() {
            return if (id.startsWith("item:")) {
                Registry.ITEM.get(Identifier(id.substring(5)))
            } else {
                null
            }
        }

    val fluid: Fluid?
        get() {
            return if (id.startsWith("item:")) {
                Registry.FLUID.get(Identifier(id.substring(5)))
            } else {
                null
            }
        }
}
