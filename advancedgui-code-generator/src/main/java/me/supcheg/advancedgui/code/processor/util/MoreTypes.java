package me.supcheg.advancedgui.code.processor.util;

import com.palantir.javapoet.TypeName;
import lombok.RequiredArgsConstructor;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.util.Iterator;
import java.util.List;

@RequiredArgsConstructor
public class MoreTypes {
    private final ProcessingEnvironment env;
    private final String rawObject = Object.class.getName();

    public boolean isAccessible(Class<?> superType, TypeName type) {
        return !type.isPrimitive() && this.isAccessible(superType, this.getTypeMirror(type));
    }

    public boolean isAccessible(Class<?> superType, TypeMirror type) {
        return this.containsSubtype(type, superType.getName());
    }

    public boolean isAccessible(String superType, TypeMirror type) {
        return this.containsSubtype(type, superType);
    }

    private boolean containsSubtype(TypeMirror type, String looking) {
        String typeName = toNoGenericString(type.toString());
        if (typeName.equals(looking)) {
            return true;
        } else if (typeName.equals(this.rawObject)) {
            return false;
        } else {
            List<? extends TypeMirror> subtypes = this.env.getTypeUtils().directSupertypes(type);
            Iterator<? extends TypeMirror> var5 = subtypes.iterator();

            TypeMirror mirror;
            do {
                if (!var5.hasNext()) {
                    return false;
                }

                mirror = var5.next();
            } while (!this.containsSubtype(mirror, looking));

            return true;
        }
    }

    private static String toNoGenericString(String type) {
        int genericInfoStartIndex = type.indexOf("<");
        return genericInfoStartIndex == -1 ? type : type.substring(0, genericInfoStartIndex);
    }

    public TypeMirror getTypeMirror(TypeName name) {
        TypeElement typeElement = this.env.getElementUtils().getTypeElement(toNoGenericString(name.toString()));
        if (typeElement == null) {
            throw new NullPointerException("Cannot get type mirror for name " + name);
        } else {
            return typeElement.asType();
        }
    }
}
