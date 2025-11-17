package me.supcheg.advancedgui.code.processor.property;

public interface PropertyVisitor {
    default void scan(Iterable<? extends Property> properties) {
        properties.forEach(this::scan);
    }

    default void scan(Property property) {
        property.accept(this);
    }

    default void visit(Property property) {
    }

    default void visitPrimitive(PrimitiveProperty property) {
    }

    default void visitObjectCollection(ObjectCollectionProperty property) {
    }

    default void visitObject(ObjectProperty property) {
    }
}
