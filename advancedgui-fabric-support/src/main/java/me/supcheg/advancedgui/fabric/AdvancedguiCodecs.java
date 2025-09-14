package me.supcheg.advancedgui.fabric;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.supcheg.advancedgui.api.gui.template.GuiTemplate;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

import static me.supcheg.advancedgui.api.loader.base64.Base64GuiLoader.jsonDownstreamBase64GuiLoader;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AdvancedguiCodecs {
    public static final StreamCodec<FriendlyByteBuf, GuiTemplate> GUI_TEMPLATE = new StreamCodec<>() {
        @Override
        public void encode(FriendlyByteBuf buf, GuiTemplate template) {
            buf.writeUtf(jsonDownstreamBase64GuiLoader().writeString(template), Integer.MAX_VALUE);
        }

        @Override
        public GuiTemplate decode(FriendlyByteBuf buf) {
            return jsonDownstreamBase64GuiLoader().readString(buf.readUtf(Integer.MAX_VALUE));
        }
    };
}
