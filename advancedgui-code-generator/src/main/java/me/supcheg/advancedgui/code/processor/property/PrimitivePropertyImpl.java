package me.supcheg.advancedgui.code.processor.property;

import javax.lang.model.type.PrimitiveType;

public record PrimitivePropertyImpl(
        PrimitiveType type,
        String name
) implements PrimitiveProperty {
}
