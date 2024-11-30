package me.supcheg.advancedgui.api.loader;

import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.api.gui.template.GuiTemplate;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public interface GuiLoader extends Buildable<GuiLoader, GuiLoader.Builder> {

    @NotNull
    GuiTemplate loadResource(@NotNull Reader in) throws IOException;

    @NotNull
    default GuiTemplate loadString(@NotNull String raw) throws IOException {
        try (Reader reader = new StringReader(raw)) {
            return loadResource(reader);
        }
    }

    interface Builder extends AbstractBuilder<GuiLoader> {
    }
}
