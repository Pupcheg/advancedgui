package me.supcheg.advancedgui.code.processor.property;

import javax.lang.model.type.PrimitiveType;

public interface PrimitiveProperty extends Property {

    @Override
    PrimitiveType type();

    @Override
    default PropertyKind kind() {
        return PropertyKind.PRIMITIVE;
    }

    @Override
    default void accept(PropertyVisitor visitor) {
        visitor.visitPrimitive(this);
    }
}
