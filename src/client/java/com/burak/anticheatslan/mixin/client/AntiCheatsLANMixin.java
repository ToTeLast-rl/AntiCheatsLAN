package com.burak.anticheatslan.mixin.client;

import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.screens.ShareToLanScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Comparator;
import java.util.List;

@Mixin(ShareToLanScreen.class)
public class AntiCheatsLANMixin {
    @Inject(at = @At("TAIL"), method = "init")
    private void modifyButtons(CallbackInfo info) {
        ShareToLanScreen screen = (ShareToLanScreen)(Object)this;

        // Grab all buttons and sort them top-to-bottom, then left-to-right
        List<AbstractButton> buttons = screen.children().stream()
            .filter(child -> child instanceof AbstractButton)
            .map(child -> (AbstractButton) child)
            .sorted(Comparator.comparingInt(AbstractButton::getY).thenComparingInt(AbstractButton::getX))
            .toList();

        // Ensure we found the buttons before modifying
        if (buttons.size() >= 2) {
            AbstractButton gameModeButton = buttons.get(0); // Top Left
            AbstractButton cheatsButton = buttons.get(1);   // Top Right

            // Hide the Cheats button
            cheatsButton.visible = false;
            cheatsButton.active = false;

            // Center the Game Mode button
            int centeredX = (screen.width / 2) - (gameModeButton.getWidth() / 2);
            gameModeButton.setX(centeredX);
        }
    }
}
