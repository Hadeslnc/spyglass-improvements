package me.juancarloscp52.spyglass_improvements.mixin;

import me.juancarloscp52.spyglass_improvements.config.SpyglassImprovementsConfig;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Gui.class)
public class InGameHudMixin {

    // Set the spyglass overlay depending on the selected one.
    @ModifyArg(method = "renderSpyglassOverlay",at = @At(value = "INVOKE",target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIFFIIII)V"),index = 0)
    public ResourceLocation setTexture(ResourceLocation resourceLocation){
        return switch (SpyglassImprovementsConfig.overlay.get()) {
            case Clear -> ResourceLocation.fromNamespaceAndPath("spyglass_improvements", "textures/spyglass_scope_clear.png");
            case Circle -> ResourceLocation.fromNamespaceAndPath("spyglass_improvements", "textures/spyglass_scope_circle.png");
            default -> resourceLocation;
        };
    }
    // toggle renderCrosshair depending on settings
    @Inject(method = "renderCrosshair", at = @At("HEAD"), cancellable = true)
    public void renderCrosshair(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci){
        if(!SpyglassImprovementsConfig.showCrosshair.get() && Minecraft.getInstance().player!=null && Minecraft.getInstance().player.isScoping())
            ci.cancel();
    }

    // Toggle overlay.
    @Inject(method = "renderSpyglassOverlay", at = @At("HEAD"), cancellable = true)
    public void noRender(GuiGraphics guiGraphics, float f, CallbackInfo ci){ // No overlay.
        if(SpyglassImprovementsConfig.overlay.get() == SpyglassImprovementsConfig.Overlays.None)
            ci.cancel();
    }

}
