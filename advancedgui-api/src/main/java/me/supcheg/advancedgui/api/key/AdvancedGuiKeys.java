package me.supcheg.advancedgui.api.key;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.supcheg.advancedgui.api.AdvancedGuiApi;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("PatternValidation")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AdvancedGuiKeys {
    @NotNull
    public static Key advancedguiKey(@NotNull String raw) {
        int index = raw.indexOf(Key.DEFAULT_SEPARATOR);
        String namespace = index >= 1 ? raw.substring(0, index) : AdvancedGuiApi.NAMESPACE;
        String value = index >= 0 ? raw.substring(index + 1) : raw;
        return Key.key(namespace, value);
    }
}
