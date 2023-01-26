package com.mraof.unstuckalchemy.item

import com.mraof.unstuckalchemy.UAUtil.addFuel
import com.mraof.unstuckalchemy.UnstuckAlchemy
import me.shedaniel.architectury.registry.DeferredRegister
import net.minecraft.item.Item
import net.minecraft.util.registry.Registry

/**
 * Created by mraof on 2019 November 16 at 11:31 PM.
 */
object UnstuckAlchemyItems {
    val ITEMS = DeferredRegister.create(UnstuckAlchemy.MOD_ID, Registry.ITEM_KEY)
    var URANIUM_NUGGET = Item(Item.Settings().group(UnstuckAlchemy.BLOCK_GROUP))
    var URANIUM_INGOT = Item(Item.Settings().group(UnstuckAlchemy.BLOCK_GROUP))
    var URANIUM_FUEL_ROD = UAItem(Item.Settings().group(UnstuckAlchemy.BLOCK_GROUP).maxCount(1))
    var DEPLETED_URANIUM_NUGGET = Item(Item.Settings().group(UnstuckAlchemy.BLOCK_GROUP))
    var DEPLETED_URANIUM_INGOT = Item(Item.Settings().group(UnstuckAlchemy.BLOCK_GROUP))
    var DEPLETED_URANIUM_FUEL_ROD = Item(Item.Settings().group(UnstuckAlchemy.BLOCK_GROUP).maxCount(1))
    fun register() {
        registerItem("uranium_nugget", URANIUM_NUGGET)
        registerItem("uranium_ingot", URANIUM_INGOT)
        registerItem("uranium_fuel_rod", URANIUM_FUEL_ROD)
        registerItem("depleted_uranium_nugget", DEPLETED_URANIUM_NUGGET)
        registerItem("depleted_uranium_ingot", DEPLETED_URANIUM_INGOT)
        registerItem("depleted_uranium_fuel_rod", DEPLETED_URANIUM_FUEL_ROD)
        addFuel(URANIUM_FUEL_ROD, 1000)

        ITEMS.register()
    }

    fun registerItem(id: String?, item: Item) {
        ITEMS.register(id) { item }
    }
}
