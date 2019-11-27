package com.mraof.unstuckalchemy.item;

import com.mraof.unstuckalchemy.UnstuckAlchemy;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * Created by mraof on 2019 November 16 at 11:31 PM.
 */
public class UnstuckAlchemyItems {
    public static Item URANIUM_NUGGET = new Item(new Item.Settings().group(UnstuckAlchemy.BLOCK_GROUP));
    public static Item URANIUM_INGOT = new Item(new Item.Settings().group(UnstuckAlchemy.BLOCK_GROUP));
    public static Item URANIUM_FUEL_ROD = new Item(new Item.Settings().group(UnstuckAlchemy.BLOCK_GROUP).maxCount(1));
    public static Item DEPLETED_URANIUM_NUGGET = new Item(new Item.Settings().group(UnstuckAlchemy.BLOCK_GROUP));
    public static Item DEPLETED_URANIUM_INGOT = new Item(new Item.Settings().group(UnstuckAlchemy.BLOCK_GROUP));
    public static Item DEPLETED_URANIUM_FUEL_ROD = new Item(new Item.Settings().group(UnstuckAlchemy.BLOCK_GROUP).maxCount(1));

    public static void register() {
        Registry.register(Registry.ITEM, new Identifier("unstuck_alchemy","uranium_nugget"), URANIUM_NUGGET);
        Registry.register(Registry.ITEM, new Identifier("unstuck_alchemy","uranium_ingot"), URANIUM_INGOT);
        Registry.register(Registry.ITEM, new Identifier("unstuck_alchemy","uranium_fuel_rod"), URANIUM_FUEL_ROD);
        Registry.register(Registry.ITEM, new Identifier("unstuck_alchemy","depleted_uranium_nugget"), DEPLETED_URANIUM_NUGGET);
        Registry.register(Registry.ITEM, new Identifier("unstuck_alchemy","depleted_uranium_ingot"), DEPLETED_URANIUM_INGOT);
        Registry.register(Registry.ITEM, new Identifier("unstuck_alchemy","depleted_uranium_fuel_rod"), DEPLETED_URANIUM_FUEL_ROD);

        FuelRegistry.INSTANCE.add(URANIUM_FUEL_ROD, 1000);
    }

}
