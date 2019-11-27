package com.mraof.unstuckalchemy.mixin;

import com.mraof.unstuckalchemy.UnstuckAlchemy;
import com.mraof.unstuckalchemy.events.DataPackReloadCallback;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Created by mraof on 2019 November 17 at 2:56 PM.
 */
@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @Inject(at = @At("RETURN"), method = "<init>")
    private void afterInit(CallbackInfo info) {
        UnstuckAlchemy.onServerInit((MinecraftServer) (Object) this);
    }

    @Inject(at = @At("RETURN"), method = "reloadDataPacks")
    private void afterReload(CallbackInfo info) {
        DataPackReloadCallback.EVENT.invoker().onReload((MinecraftServer) (Object) this);
    }
}
