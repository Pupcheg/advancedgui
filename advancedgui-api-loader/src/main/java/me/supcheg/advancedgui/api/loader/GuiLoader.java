package me.supcheg.advancedgui.api.loader;

import me.supcheg.advancedgui.api.gui.template.GuiTemplate;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

public interface GuiLoader {
    @NotNull
    GuiTemplate loadResource(@NotNull Reader in) throws IOException;

    @NotNull
    default GuiTemplate loadString(@NotNull String raw) throws IOException {
        try (Reader reader = new StringReader(raw)) {
            return loadResource(reader);
        }
    }

    void saveResource(@NotNull GuiTemplate template, @NotNull Writer writer) throws IOException;

    @NotNull
    default String saveString(@NotNull GuiTemplate template) throws IOException {
        StringWriter writer = new StringWriter();
        try (writer) {
            saveResource(template, writer);
        }
        return writer.toString();
    }
}
