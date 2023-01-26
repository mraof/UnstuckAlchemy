package com.mraof.unstuckalchemy.alchemy

import com.mraof.unstuckalchemy.UAUtil
import com.mraof.unstuckalchemy.UnstuckAlchemy.logger
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.CraftingInventory
import net.minecraft.inventory.Inventories
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.recipe.*
import net.minecraft.screen.ScreenHandler
import net.minecraft.server.MinecraftServer

object RecipeCosts : CostHandler {
    override fun addCosts(costs: CostMap, server: MinecraftServer) {
        val recipeManager = server.recipeManager
        for (recipe in recipeManager.values()) {
            if (recipe is ShapelessRecipe || recipe is ShapedRecipe || recipe is SmeltingRecipe || recipe is CuttingRecipe) {
                val output = recipe.output
                val outputCost = costs[SimpleStack(output.item, output.tag)]
                if (outputCost.value.elements.isNotEmpty()) {
                    continue
                }
                logger.info("Recipe for %s called %s", output, recipe.id)
                val ingredients = ArrayList<List<Pair<Cost, Int>>>()
                for (input in recipe.previewInputs) {
                    input.ids
                    @Suppress("CAST_NEVER_SUCCEEDS")
                    val stackArray = UAUtil.getIngredientStacks(input)
                    if (stackArray.size > 1) {
                        val stackList = ArrayList<Pair<Cost, Int>>()
                        for (stack in stackArray) {
                            if (stack.item != Items.AIR) {
                                val cost = costs[SimpleStack(stack.item, stack.tag)]
                                stackList.add(Pair(cost, stack.count))
                            }
                        }
                        ingredients.add(stackList)
                    } else if (stackArray.size == 1) {
                        val stack = stackArray[0]
                        if (stack.item != Items.AIR) {
                            logger.info("\t%s", stack)
                            val cost = costs[SimpleStack(stack.item, stack.tag)]
                            ingredients.add(listOf(Pair(cost, stack.count)))
                        }
                    }
                }

                //Forgot how my own code works so I need to rewrite this- inner list is alternate ingredients e.g. different types of wood planks
                /*
                //val inventory = CostInventory(stackArray.map { stack -> stack.copy() }.toMutableList())
                if(recipe is CraftingRecipe) {
                    val inventory = CraftingInventory(CostScreenHandler, 3, 3)
                    for(i in stackArray.indices) {
                        inventory.setStack(i, stackArray[i])
                    }
                    val remaining = recipe.getRemainingStacks(inventory)
                    val stackList = ArrayList<Pair<Cost, Int>>()
                    for (stack in remaining) {
                        if (stack.item != Items.AIR) {
                            val cost = costs[SimpleStack(stack.item, stack.tag)]
                            stackList.add(Pair(cost, -stack.count))
                        }
                    }
                    if(stackList.isNotEmpty()) {
                        ingredients.add(stackList)
                    }
                } else {
                    //SmeltingRecipe and CuttingRecipe are simple Recipe<Inventory>
                    val inventory = CostInventory(stackArray.map { stack -> stack.copy() }.toMutableList())
                    @Suppress("UNCHECKED_CAST")
                    val remaining = (recipe as Recipe<Inventory>).getRemainingStacks(inventory)
                    val stackList = ArrayList<Pair<Cost, Int>>()
                    for (stack in remaining) {
                        if (stack.item != Items.AIR) {
                            val cost = costs[SimpleStack(stack.item, stack.tag)]
                            stackList.add(Pair(cost, -stack.count))
                        }
                    }
                    if(stackList.isNotEmpty()) {
                        ingredients.add(stackList)
                    }
                }
                 */

                if (recipe is AbstractCookingRecipe) {
                    ingredients.add(
                        listOf(
                            Pair(
                                Cost(
                                    Elements("energy", recipe.cookTime.toLong())
                                ), 1
                            )
                        )
                    )
                }
                outputCost.addRecipe(CostRecipe(recipe.id.toString(), ingredients, output.count))
            }
        }
    }
    class CostInventory(val stacks: MutableList<ItemStack>) : Inventory {
        override fun clear() {
            for(i in stacks.indices) {
                stacks[i] = ItemStack.EMPTY
            }
        }

        override fun size(): Int = stacks.size

        override fun isEmpty(): Boolean {
            TODO("Not yet implemented")
        }

        /**
         * Fetches the stack currently stored at the given slot. If the slot is empty,
         * or is outside the bounds of this inventory, returns see [ItemStack.EMPTY].
         */
        override fun getStack(slot: Int): ItemStack = stacks[slot]

        /**
         * Removes a specific number of items from the given slot.
         *
         * @return the removed items as a stack
         */
        override fun removeStack(slot: Int, amount: Int): ItemStack {
            return Inventories.splitStack(stacks, slot, amount)
        }

        /**
         * Removes the stack currently stored at the indicated slot.
         *
         * @return the stack previously stored at the indicated slot.
         */
        override fun removeStack(slot: Int): ItemStack {
            return Inventories.removeStack(stacks, slot)
        }

        override fun setStack(slot: Int, stack: ItemStack?) {
            TODO("Not yet implemented")
        }

        override fun markDirty() {
            TODO("Not yet implemented")
        }

        override fun canPlayerUse(player: PlayerEntity?): Boolean {
            TODO("Not yet implemented")
        }

    }

    object CostScreenHandler : ScreenHandler(null, 0) {
        override fun canUse(player: PlayerEntity?): Boolean = false
    }
}
