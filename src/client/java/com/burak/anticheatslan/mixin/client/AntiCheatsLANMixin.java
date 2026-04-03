package com.burak.anticheatslan.mixin.client;

import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.screens.ShareToLanScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ShareToLanScreen.class)
public class AntiCheatsLANMixin {
    @Inject(at = @At("TAIL"), method = "init")
    private void modifyButtons(CallbackInfo info) {
        ShareToLanScreen screen = (ShareToLanScreen)(Object)this;

        // Hide the Cheats button
        screen.children().stream()
            .filter(child -> child instanceof AbstractButton)
            .map(child -> (AbstractButton) child)
            .filter(button -> button.getMessage().getString().toLowerCase().contains("commands"))
            .forEach(button -> {
                button.visible = false;
                button.active = false;
            });

        // Find and center the Game Mode button
        screen.children().stream()
            .filter(child -> child instanceof AbstractButton)
            .map(child -> (AbstractButton) child)
            .filter(button -> button.getMessage().getString().toLowerCase().contains("mode"))
            .findFirst()
            .ifPresent(button -> {
                int centeredX = (screen.width / 2) - (button.getWidth() / 2);
                button.setX(centeredX);
            });
    }
}
