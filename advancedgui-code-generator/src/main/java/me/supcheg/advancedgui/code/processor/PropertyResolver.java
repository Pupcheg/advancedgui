package me.supcheg.advancedgui.code.processor;

import com.palantir.javapoet.TypeName;
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
import static me.supcheg.advancedgui.code.processor.StringUtil.decapitalize;
import static me.supcheg.advancedgui.code.processor.TypeNames.simpleName;

@RequiredArgsConstructor
class PropertyResolver {
    private final Types types;
    private final ReferenceType collectionTypeMirror;
    private final ReferenceType mapTypeMirror;

    public List<? extends Property> listProperties(TypeElement element) {
        return methodsIn(element.getEnclosedElements()).stream()
                .filter(this::isPropertyGetter)
                .peek(this::assertIsNotErrorReturnType)
                .map(this::asProperty)
                .toList();
    }

    private boolean isPropertyGetter(ExecutableElement method) {
        return method.getReturnType().getKind() != TypeKind.VOID
               && method.getParameters().isEmpty()
               && method.getModifiers().contains(Modifier.ABSTRACT);
    }

    private void assertIsNotErrorReturnType(ExecutableElement method) {
        if (method.getReturnType().getKind() == TypeKind.ERROR) {
            throw new AssertionError(method + " has error return type");
        }
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
            case OBJECT_OBJECT_MAP -> {
                var mapTypes = mapElementTypes(type);
                var mapNames = mapElementNames(mapTypes, name);

                yield new Property.ObjectObjectMap(
                        (ReferenceType) type,
                        name,
                        asProperty(mapTypes.left(), mapNames.left()),
                        asProperty(mapTypes.right(), mapNames.right())
                );
            }
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
                throw new IllegalArgumentException("Illegal type: " + t);
            }
        }.visit(collectionType);
    }

    private TypeMirrorPair mapElementTypes(TypeMirror mapType) {
        return new SimpleTypeVisitor14<TypeMirrorPair, Void>() {
            @Override
            public TypeMirrorPair visitDeclared(DeclaredType t, Void unused) {
                var typeArguments = t.getTypeArguments();
                return new TypeMirrorPair(typeArguments.get(0), typeArguments.get(1));
            }

            @Override
            protected TypeMirrorPair defaultAction(TypeMirror t, Void unused) {
                throw new IllegalArgumentException("Illegal type: " + t);
            }
        }.visit(mapType);
    }

    private record TypeMirrorPair(TypeMirror left, TypeMirror right) {}

    private String collectionElementName(String collectionName) {
        return collectionName.endsWith("s") ?
                collectionName.substring(0, collectionName.length() - 1) :
                collectionName;
    }

    private StringPair mapElementNames(TypeMirrorPair mapTypes, String mapName) {
        return new StringPair(
                decapitalize(simpleName(TypeName.get(mapTypes.left()))),
                isCollectionType(mapTypes.right()) ? mapName : collectionElementName(mapName)
        );
    }

    private record StringPair(String left, String right) {}

    private PropertyKind propertyKind(TypeMirror typeMirror) {
        if (typeMirror.getKind().isPrimitive()) {
            return PropertyKind.PRIMITIVE;
        }

        if (isCollectionType(typeMirror)) {
            return PropertyKind.OBJECT_COLLECTION;
        }

        if (isMapType(typeMirror)) {
            return PropertyKind.OBJECT_OBJECT_MAP;
        }

        return PropertyKind.OBJECT;
    }

    private enum PropertyKind {
        PRIMITIVE,
        OBJECT,
        OBJECT_COLLECTION,
        OBJECT_OBJECT_MAP,
    }

    private boolean isCollectionType(TypeMirror type) {
        return types.isAssignable(types.erasure(type), collectionTypeMirror);
    }

    private boolean isMapType(TypeMirror type) {
        return types.isAssignable(types.erasure(type), mapTypeMirror);
    }
}
