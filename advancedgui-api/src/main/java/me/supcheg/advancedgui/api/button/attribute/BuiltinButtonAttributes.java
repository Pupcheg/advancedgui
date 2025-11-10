package me.supcheg.advancedgui.api.button.attribute;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.supcheg.advancedgui.api.Advancedgui;

import static me.supcheg.advancedgui.api.button.attribute.ButtonAttribute.buttonAttribute;
import static net.kyori.adventure.key.Key.key;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class BuiltinButtonAttributes {
    static final ButtonAttribute<Boolean> GLOWING = buttonAttribute(key(Advancedgui.NAMESPACE, "glowing"), true);
}
