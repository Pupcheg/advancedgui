package me.supcheg.advancedgui.code.processor.property;

import javax.lang.model.type.ReferenceType;

public interface ObjectProperty extends Property {
    @Override
    ReferenceType type();

    @Override
    default PropertyKind kind() {
        return PropertyKind.OBJECT;
    }

    @Override
    default void accept(PropertyVisitor visitor) {
        visitor.visitObject(this);
    }
}
