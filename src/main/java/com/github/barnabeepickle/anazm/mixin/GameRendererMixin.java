package com.github.barnabeepickle.anazm.mixin;

import com.github.barnabeepickle.anazm.ANAZM;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.render.GameRenderer;

@Environment(EnvType.CLIENT)
@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Inject(method = "getFov", at = @At("RETURN"), cancellable = true)
    public void getZoomLevel(CallbackInfoReturnable<Double> callbackInfo) {
        if(ANAZM.isZooming()) {
            double fov = callbackInfo.getReturnValue();
            callbackInfo.setReturnValue(fov * ANAZM.zoomLevel);
        }

        ANAZM.toggleSmoothCamera();
    }
}
