package me.supcheg.advancedgui.code.processor;

abstract class PropertyVisitor {
    void scan(Iterable<? extends Property> properties) {
        properties.forEach(this::scan);
    }

    void scan(Property property) {
        property.accept(this);
    }

    abstract void visitPrimitive(Property.Primitive property);

    abstract void visitObjectCollection(Property.ObjectCollection property);

    abstract void visitObject(Property.Object property);
}
