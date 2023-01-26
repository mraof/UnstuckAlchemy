package com.mraof.unstuckalchemy.alchemy

import com.mraof.unstuckalchemy.UnstuckAlchemy.logger
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundTag

/**
 * Created by mraof on 2019 November 23 at 6:21 AM.
 */
object DamagedCosts : CostHandler {
    override fun calculateCost(stack: SimpleStack): Elements? {
        val item = stack.item
        if (item != null && item.isDamageable && stack.tag != null && stack.tag.contains("Damage")) {
            logger.info("Damaged: %s", stack)
            val tag = CompoundTag()
            tag.copyFrom(stack.tag)
            tag.remove("Damage")
            val undamaged = if (tag.isEmpty) {
                SimpleStack(item)
            } else {
                SimpleStack(item, tag)
            }
            logger.info("Undamaged: %s", undamaged)
            val baseElements = Alchemy.getElements(undamaged)
            if (baseElements != null) {
                val itemStack = ItemStack(item)
                itemStack.tag = stack.tag
                val maxDamage = item.maxDamage
                val damage = itemStack.damage
                if(damage >= maxDamage) {
                    return null
                }
                return baseElements * (maxDamage - damage) / maxDamage
            }
        }
        return null
    }
}
