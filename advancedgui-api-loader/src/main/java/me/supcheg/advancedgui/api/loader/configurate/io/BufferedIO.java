package me.supcheg.advancedgui.api.loader.configurate.io;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.concurrent.Callable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BufferedIO {

    @Nullable
    public static Callable<BufferedWriter> lazyBufferedOrNull(@Nullable Writer writer) {
        if (writer == null) {
            return null;
        }
        return () -> writer instanceof BufferedWriter buf ? buf : new BufferedWriter(writer);
    }

    @Nullable
    public static Callable<BufferedReader> lazyBufferedOrNull(@Nullable Reader reader) {
        if (reader == null) {
            return null;
        }
        return () -> reader instanceof BufferedReader buf ? buf : new BufferedReader(reader);
    }
}
