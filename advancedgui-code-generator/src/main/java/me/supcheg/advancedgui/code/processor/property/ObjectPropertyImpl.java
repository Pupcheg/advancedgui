package me.supcheg.advancedgui.code.processor.property;

import javax.lang.model.type.ReferenceType;

public record ObjectPropertyImpl(
        ReferenceType type,
        String name
) implements ObjectProperty {
}
