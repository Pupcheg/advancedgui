package me.supcheg.advancedgui.platform.paper.render;

import io.papermc.paper.adventure.PaperAdventure;
import lombok.RequiredArgsConstructor;
import me.supcheg.advancedgui.api.Advancedgui;
import me.supcheg.advancedgui.api.gui.background.Background;
import me.supcheg.advancedgui.platform.paper.resourcepack.BackgroundImageMeta;
import me.supcheg.advancedgui.platform.paper.resourcepack.BackgroundImageMetaLookup;
import net.kyori.adventure.key.Key;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.contents.PlainTextContents;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;
import java.util.List;

import static net.minecraft.resources.ResourceLocation.fromNamespaceAndPath;

@RequiredArgsConstructor
public class DefaultBackgroundComponentRenderer implements Renderer<Background, Component> {
    private static final Component MOVE_COMPONENT_TO_START =
            Component.literal("\u0008")
                    .setStyle(createStyle(fromNamespaceAndPath(Advancedgui.NAMESPACE, "negative_spaces")));
    private final BackgroundImageMetaLookup backgroundImageMetaLookup;

    @NotNull
    @Override
    public Component render(@NotNull Background input) {
        var result = MutableComponent.create(PlainTextContents.EMPTY);


        List<Key> locations = input.locations();
        for (int i = 0; i < locations.size(); i++) {
            BackgroundImageMeta meta = backgroundImageMetaLookup.findBackgroundImageMeta(locations.get(i));
            for (int j = 0; j < i; j++) {
                result.append(MOVE_COMPONENT_TO_START);
            }

            result.append(
                    Component.literal(meta.data())
                            .setStyle(createWhiteStyle(PaperAdventure.asVanilla(meta.font())))
            );
        }

        return result;
    }

    private static Style createStyle(ResourceLocation font) {
        return Style.EMPTY
                .withFont(font);
    }

    private static Style createWhiteStyle(ResourceLocation font) {
        return Style.EMPTY
                .withFont(font)
                .withColor(Color.WHITE.getRGB());
    }
}
