package com.mraof.unstuckalchemy.mixin;

import com.mraof.unstuckalchemy.fabric.UnstuckAlchemyFabric;
import net.minecraft.resource.ReloadableResourceManager;
import net.minecraft.resource.ServerResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Created by mraof on 2019 November 17 at 2:56 PM.
 */
@Mixin(ServerResourceManager.class)
public class ServerResourceManagerMixin {
    @Inject(at = @At("RETURN"), method = "<init>")
    private void afterInit(CallbackInfo info) {
        UnstuckAlchemyFabric.registerServerResources((ReloadableResourceManager) ((ServerResourceManager) (Object) this).getResourceManager());
    }
}
