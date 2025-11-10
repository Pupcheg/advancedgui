package me.supcheg.advancedgui.code.processor.util;

import com.palantir.javapoet.TypeName;
import lombok.RequiredArgsConstructor;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.type.TypeMirror;

@RequiredArgsConstructor
public class MoreTypes {
    private final ProcessingEnvironment env;
    private final String rawObject = Object.class.getName();

    public boolean isAccessible(Class<?> superType, TypeName type) {
        return !type.isPrimitive() && isAccessible(superType, getTypeMirror(type));
    }

    public boolean isAccessible(Class<?> superType, TypeMirror type) {
        return containsSubtype(type, superType.getName());
    }

    public boolean isAccessible(String superType, TypeMirror type) {
        return containsSubtype(type, superType);
    }

    private boolean containsSubtype(TypeMirror type, String looking) {
        var typeName = toNoGenericString(type.toString());

        if (typeName.equals(looking)) {
            return true;
        }

        if (typeName.equals(rawObject)) {
            return false;
        }

        for (var directSubtype : env.getTypeUtils().directSupertypes(type)) {
            if (containsSubtype(directSubtype, looking)) {
                return true;
            }
        }

        return false;
    }

    private static String toNoGenericString(String type) {
        int genericInfoStartIndex = type.indexOf("<");
        return genericInfoStartIndex == -1 ? type : type.substring(0, genericInfoStartIndex);
    }

    public TypeMirror getTypeMirror(TypeName name) {
        var typeElement = env.getElementUtils().getTypeElement(toNoGenericString(name.toString()));
        if (typeElement == null) {
            throw new NullPointerException("Cannot get type mirror for name " + name);
        } else {
            return typeElement.asType();
        }
    }
}
