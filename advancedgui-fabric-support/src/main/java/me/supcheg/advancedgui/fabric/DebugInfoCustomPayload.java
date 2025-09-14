package me.supcheg.advancedgui.fabric;

import me.supcheg.advancedgui.api.Advancedgui;
import me.supcheg.advancedgui.api.gui.template.GuiTemplate;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record DebugInfoCustomPayload(
        GuiTemplate template
) implements CustomPacketPayload {

    public static final Type<DebugInfoCustomPayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(Advancedgui.NAMESPACE, "debug"));

    public static final StreamCodec<FriendlyByteBuf, DebugInfoCustomPayload> STREAM_CODEC =
            StreamCodec.composite(
                    AdvancedguiCodecs.GUI_TEMPLATE, DebugInfoCustomPayload::template,
                    DebugInfoCustomPayload::new
            );

    @Override
    public Type<DebugInfoCustomPayload> type() {
        return TYPE;
    }

}
