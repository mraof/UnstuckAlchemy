package com.mraof.unstuckalchemy.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.MinecraftServer;

/**
 * Created by mraof on 2019 November 17 at 3:31 PM.
 */
public interface DataPackReloadCallback {
    Event<DataPackReloadCallback> EVENT = EventFactory.createArrayBacked(DataPackReloadCallback.class, (listeners) -> (server) -> {
        for(DataPackReloadCallback event : listeners) {
            event.onReload(server);
        }
    });

    void onReload(MinecraftServer server);
}
