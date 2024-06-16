package me.juancarloscp52.spyglass_improvements.fabric.client;

import me.juancarloscp52.spyglass_improvements.client.SpyglassImprovementsClient;
import me.juancarloscp52.spyglass_improvements.client.integrations.IEquipmentIntegration;
import me.juancarloscp52.spyglass_improvements.fabric.client.integrations.TrinketsIntegration;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.loader.api.FabricLoader;

public final class SpyglassImprovementsFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        KeyBindingHelper.registerKeyBinding(SpyglassImprovementsClient.useSpyglass);
        IEquipmentIntegration trinkets = null;
        if(FabricLoader.getInstance().isModLoaded("trinkets")){
            trinkets = new TrinketsIntegration();
            trinkets.registerRenderer();
        }
        SpyglassImprovementsClient.getInstance().init(trinkets);

        ClientTickEvents.END_CLIENT_TICK.register(client -> SpyglassImprovementsClient.getInstance().onClientTick(client));
    }
}
