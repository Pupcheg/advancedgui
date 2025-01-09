package me.supcheg.advancedgui.api.button.attribute;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.supcheg.advancedgui.api.AdvancedGuiApi;

import static me.supcheg.advancedgui.api.button.attribute.ButtonAttribute.buttonAttribute;
import static net.kyori.adventure.key.Key.key;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class BuiltinButtonAttributes {
    static final ButtonAttribute GLOWING = buttonAttribute(key(AdvancedGuiApi.NAMESPACE, "glowing"));
    static final ButtonAttribute DISABLED = buttonAttribute(key(AdvancedGuiApi.NAMESPACE, "disabled"));
    static final ButtonAttribute HIDDEN = buttonAttribute(key(AdvancedGuiApi.NAMESPACE, "hidden"));
}
