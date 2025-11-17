package me.supcheg.advancedgui.code.processor.property;

import com.palantir.javapoet.TypeName;

import javax.lang.model.type.TypeMirror;

public interface Property {
    TypeMirror type();

    default TypeName typename() {
        return TypeName.get(type());
    }

    String name();

    PropertyKind kind();

    void accept(PropertyVisitor visitor);
}
