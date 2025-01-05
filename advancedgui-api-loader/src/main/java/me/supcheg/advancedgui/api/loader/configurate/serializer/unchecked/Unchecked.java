package me.supcheg.advancedgui.api.loader.configurate.serializer.unchecked;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Contract;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Unchecked {
    @SuppressWarnings("unchecked")
    @Contract(value = "_ -> param1", pure = true)
    public static <T> T uncheckedCast(Object o) {
        return (T) o;
    }
}
