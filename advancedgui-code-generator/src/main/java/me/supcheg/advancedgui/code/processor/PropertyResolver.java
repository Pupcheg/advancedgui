package me.supcheg.advancedgui.code.processor;

import lombok.RequiredArgsConstructor;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.ReferenceType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleTypeVisitor14;
import javax.lang.model.util.Types;
import java.util.List;

import static javax.lang.model.util.ElementFilter.methodsIn;

@RequiredArgsConstructor
class PropertyResolver {
    private final Types types;
    private final ReferenceType collectionTypeMirror;

    public List<? extends Property> listProperties(TypeElement element) {
        return methodsIn(element.getEnclosedElements()).stream()
                .filter(this::isPropertyGetter)
                .map(this::asProperty)
                .toList();
    }

    private boolean isPropertyGetter(ExecutableElement method) {
        return method.getReturnType().getKind() != TypeKind.VOID
               && method.getParameters().isEmpty()
                && method.getModifiers().contains(Modifier.ABSTRACT);
    }

    private Property asProperty(ExecutableElement method) {
        return asProperty(method.getReturnType(), method.getSimpleName().toString());
    }

    private Property asProperty(TypeMirror type, String name) {
        return switch (propertyKind(type)) {
            case OBJECT -> new Property.Object(
                    (ReferenceType) type,
                    name
            );
            case PRIMITIVE -> new Property.Primitive(
                    (PrimitiveType) type,
                    name
            );
            case OBJECT_COLLECTION -> new Property.ObjectCollection(
                    (ReferenceType) type,
                    name,
                    asProperty(collectionElementType(type), collectionElementName(name))
            );
        };
    }

    private TypeMirror collectionElementType(TypeMirror collectionType) {
        return new SimpleTypeVisitor14<TypeMirror, Void>() {
            @Override
            public TypeMirror visitDeclared(DeclaredType t, Void unused) {
                return t.getTypeArguments().getFirst();
            }

            @Override
            protected TypeMirror defaultAction(TypeMirror t, Void unused) {
                throw new IllegalArgumentException();
            }
        }.visit(collectionType);
    }

    private String collectionElementName(String collectionName) {
        return collectionName.endsWith("s") ?
                collectionName.substring(0, collectionName.length() - 1) :
                collectionName;
    }

    private PropertyKind propertyKind(TypeMirror typeMirror) {
        if (typeMirror.getKind().isPrimitive()) {
            return PropertyKind.PRIMITIVE;
        }

        if (types.isAssignable(types.erasure(typeMirror), collectionTypeMirror)) {
            return PropertyKind.OBJECT_COLLECTION;
        }

        return PropertyKind.OBJECT;
    }

    private enum PropertyKind {
        PRIMITIVE,
        OBJECT,
        OBJECT_COLLECTION,
    }
}
