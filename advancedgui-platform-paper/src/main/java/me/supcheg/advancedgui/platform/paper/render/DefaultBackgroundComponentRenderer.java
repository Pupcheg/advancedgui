package me.supcheg.advancedgui.platform.paper.render;

import io.papermc.paper.adventure.PaperAdventure;
import me.supcheg.advancedgui.api.gui.background.Background;
import net.kyori.adventure.key.Key;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Collectors;

import static net.kyori.adventure.text.Component.text;

public class DefaultBackgroundComponentRenderer implements Renderer<Background, Component> {
    @NotNull
    @Override
    public Component render(@NotNull Background input) {
        return PaperAdventure.asVanilla(
                text(
                        input.locations().stream()
                                .map(Key::asString)
                                .collect(Collectors.joining("&"))
                )
        );
    }
}
