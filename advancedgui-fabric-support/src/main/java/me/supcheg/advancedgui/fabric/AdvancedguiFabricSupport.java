package me.supcheg.advancedgui.fabric;

import me.supcheg.advancedgui.api.loader.base64.Base64GuiLoader;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

import java.awt.Color;
import java.util.List;

import static me.supcheg.advancedgui.api.loader.yaml.YamlGuiLoader.yamlGuiLoader;

public class AdvancedguiFabricSupport implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        Base64GuiLoader.jsonDownstreamBase64GuiLoader(); // warmup

        PayloadTypeRegistry.playS2C().register(DebugInfoCustomPayload.TYPE, DebugInfoCustomPayload.STREAM_CODEC);
        ClientPlayNetworking.registerGlobalReceiver(DebugInfoCustomPayload.TYPE, this::handlePluginMessage);
    }

    private void handlePluginMessage(DebugInfoCustomPayload payload, ClientPlayNetworking.Context ctx) {
        var screen = ctx.client().screen;
        if (screen != null) {
            ScreenEvents.afterRender(screen).register(appendWithDebugInfo(payload));
        }
    }

    private ScreenEvents.AfterRender appendWithDebugInfo(DebugInfoCustomPayload payload) {
        List<String> lines = List.of(yamlGuiLoader().writeString(payload.template()).split("\n"));

        return (screen, drawContext, mouseX, mouseY, tickDelta) -> {
            int y = 10;
            for (String line : lines) {
                drawContext.drawString(screen.getFont(), line, 10, y, Color.WHITE.getRGB());
                y += 9;
            }
        };
    }
}
