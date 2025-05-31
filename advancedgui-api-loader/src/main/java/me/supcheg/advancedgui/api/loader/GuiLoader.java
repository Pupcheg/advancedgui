package me.supcheg.advancedgui.api.loader;

import lombok.SneakyThrows;
import me.supcheg.advancedgui.api.gui.template.GuiTemplate;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public interface GuiLoader {
    default GuiTemplate readString(String raw) {
        return readString(raw, StandardCharsets.UTF_8);
    }

    @SneakyThrows
    default GuiTemplate readString(String value, Charset charset) {
        return read(new ByteArrayInputStream(value.getBytes(charset)));
    }

    GuiTemplate read(InputStream in) throws IOException;

    default String writeString(GuiTemplate template) {
        return writeString(template, StandardCharsets.UTF_8);
    }

    @SneakyThrows
    default String writeString(GuiTemplate template, Charset charset) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        write(template, out);
        return out.toString(charset);
    }

    void write(GuiTemplate template, OutputStream out) throws IOException;
}
