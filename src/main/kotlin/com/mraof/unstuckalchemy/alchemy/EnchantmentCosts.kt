package com.mraof.unstuckalchemy.alchemy

import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.item.EnchantedBookItem
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundTag
import net.minecraft.server.MinecraftServer
import kotlin.math.pow

/**
 * Created by mraof on 2019 November 23 at 4:36 AM.
 */
object EnchantmentCosts : CostHandler {
    override fun calculateCost(stack: SimpleStack): Elements? {
        val item = stack.item
        if (item != null && stack.tag != null) {
            val tag = CompoundTag()
            tag.copyFrom(stack.tag)
            val itemStack = ItemStack(item)
            itemStack.tag = tag
            val enchantments = EnchantmentHelper.getEnchantments(itemStack)
            if(enchantments.isNotEmpty()) {
                val key = if (item is EnchantedBookItem) {
                    "StoredEnchantments"
                } else {
                    "Enchantments"
                }
                tag.remove(key)
                val disenchanted = if(tag.isEmpty) {
                    SimpleStack(item)
                } else {
                    SimpleStack(item, tag)
                }
                val baseElements = Alchemy.getElements(disenchanted)
                if(baseElements != null) {
                    val elements = LinkedHashMap(baseElements.elements)
                    var magic = elements.getOrDefault("magic", 0.0)
                    for((enchantment, level) in enchantments) {
                        var amount = 1
                        for(i in 0 until level) {
                            amount *= 2
                        }
                        magic += amount
                    }
                    elements["magic"] = magic
                    return Elements(elements)
                }
            }
        }
        return null
    }
}
