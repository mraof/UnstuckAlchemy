package com.mraof.unstuckalchemy.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import java.util.List;

/**
 * Created by mraof on 2019 November 17 at 3:31 PM.
 */
public interface ItemStackTooltipCallback {
    Event<ItemStackTooltipCallback> EVENT = EventFactory.createArrayBacked(ItemStackTooltipCallback.class, (listeners) -> (stack, playerEntity, tooltipContext, returnValue) -> {
        for(ItemStackTooltipCallback event : listeners) {
            event.onTooltip(stack, playerEntity, tooltipContext, returnValue);
        }
    });

    void onTooltip(ItemStack stack, PlayerEntity playerEntity, TooltipContext tooltipContext, List<Text> returnValue);
}
