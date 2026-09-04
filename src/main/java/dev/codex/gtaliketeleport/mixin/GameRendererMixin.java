package dev.codex.gtaliketeleport.mixin;

import dev.codex.gtaliketeleport.client.SatelliteCameraEffectManager;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Inject(
            method = "render(Lnet/minecraft/client/DeltaTracker;Z)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/PostChain;process(F)V"
            )
    )
    private void gtalikeTeleport$updateSatelliteUniforms(DeltaTracker deltaTracker, boolean renderLevel, CallbackInfo ci) {
        SatelliteCameraEffectManager.updateUniforms(
                Minecraft.getInstance(),
                deltaTracker.getGameTimeDeltaPartialTick(false)
        );
    }
}