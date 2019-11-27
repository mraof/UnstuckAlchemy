package com.mraof.unstuckalchemy.events

import com.mraof.unstuckalchemy.UnstuckAlchemy.logger
import net.minecraft.item.Item
import net.minecraft.item.PotionItem
import net.minecraft.potion.Potion

/**
 * Created by mraof on 2019 November 18 at 4:37 AM.
 * Doesn't use a callback because brewing recipes are registered before the mod's initialize function is even called
 */
object BrewingRecipeRegistryListener {
    val itemRecipes = HashMap<Pair<PotionItem, Item>, PotionItem>()
    val potionRecipes = HashMap<Pair<Potion, Item>, Potion>()
    fun onRegisterItemRecipe(item: Item, item2: Item, item3: Item) {
        if(item is PotionItem && item3 is PotionItem) {
            itemRecipes[Pair(item, item2)] = item3
        }
    }

    fun onRegisterPotionRecipe(potion: Potion, item: Item, potion2: Potion) {
        potionRecipes[Pair(potion, item)] = potion2
    }
}
