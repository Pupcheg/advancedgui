package me.supcheg.advancedgui.code.processor.property;

import javax.lang.model.type.ReferenceType;

public interface ObjectCollectionProperty extends Property {
    ReferenceType type();

    Property element();

    @Override
    default PropertyKind kind() {
        return PropertyKind.OBJECT_COLLECTION;
    }

    @Override
    default void accept(PropertyVisitor visitor) {
        Property.super.accept(visitor);
        visitor.visitObjectCollection(this);
    }
}
