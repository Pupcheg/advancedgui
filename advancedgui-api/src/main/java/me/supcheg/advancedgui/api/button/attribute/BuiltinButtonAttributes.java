package me.supcheg.advancedgui.api.button.attribute;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.supcheg.advancedgui.api.Advancedgui;

import static me.supcheg.advancedgui.api.button.attribute.ButtonAttribute.buttonAttribute;
import static net.kyori.adventure.key.Key.key;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class BuiltinButtonAttributes {
    static final ButtonAttribute GLOWING = buttonAttribute(key(Advancedgui.NAMESPACE, "glowing"));
    static final ButtonAttribute DISABLED = buttonAttribute(key(Advancedgui.NAMESPACE, "disabled"));
    static final ButtonAttribute HIDDEN = buttonAttribute(key(Advancedgui.NAMESPACE, "hidden"));
}
