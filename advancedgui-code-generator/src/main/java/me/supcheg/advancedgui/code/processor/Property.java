package me.supcheg.advancedgui.code.processor;

import com.palantir.javapoet.TypeName;

import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.ReferenceType;
import javax.lang.model.type.TypeMirror;

sealed interface Property {
    TypeMirror type();

    default TypeName typename() {
        return TypeName.get(type());
    }

    String name();

    void accept(PropertyVisitor visitor);

    record Primitive(
            PrimitiveType type,
            String name
    ) implements Property {
        @Override
        public void accept(PropertyVisitor visitor) {
            visitor.visitPrimitive(this);
        }
    }

    record Object(
            ReferenceType type,
            String name
    ) implements Property {
        @Override
        public void accept(PropertyVisitor visitor) {
            visitor.visitObject(this);
        }
    }

    record ObjectCollection(
            ReferenceType type,
            String name,
            Property element
    ) implements Property {
        @Override
        public void accept(PropertyVisitor visitor) {
            visitor.visitObjectCollection(this);
        }
    }

    record ObjectObjectMap(
            ReferenceType type,
            String name,
            Property key,
            Property value
    ) implements Property {
        @Override
        public void accept(PropertyVisitor visitor) {
            visitor.visitObjectObjectMap(this);
        }
    }
}
