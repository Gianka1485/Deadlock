package com.adrianka.deadlock.mixin;

import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin {
    @Inject(method = "resetWeatherCycle", at = @At("HEAD"), cancellable = true)
    private void deadlock$preventWeatherReset(CallbackInfo callbackInfo) {
        ServerLevel level = (ServerLevel) (Object) this;

        //if (DeadlockDataState.getInstance(level.getServer()).isDeadWeatherActive()) {
        //    if (DeadlockConfig.DEATH_WEATHER_SKIPEABLE.get())
        //        DeadlockDataState.getInstance(level.getServer()).disableDeadWeather();
        //    else callbackInfo.cancel();
        //}
    }
}