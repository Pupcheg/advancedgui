package me.supcheg.advancedgui.code.processor.property;

import javax.lang.model.type.ReferenceType;

public record ObjectCollectionPropertyImpl(
        ReferenceType type,
        String name,
        Property element
) implements ObjectCollectionProperty {
}
