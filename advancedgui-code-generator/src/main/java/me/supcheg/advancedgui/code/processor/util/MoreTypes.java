package me.supcheg.advancedgui.code.processor.util;

import com.palantir.javapoet.TypeName;
import org.jetbrains.annotations.NotNull;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.util.Iterator;
import java.util.List;

public class MoreTypes {
    private final ProcessingEnvironment env;
    private final String rawObject = Object.class.getName();

    public boolean isAccessible(@NotNull Class<?> superType, @NotNull TypeName type) {
        return type.isPrimitive() ? false : this.isAccessible(superType, this.getTypeMirror(type));
    }

    public boolean isAccessible(@NotNull Class<?> superType, @NotNull TypeMirror type) {
        return this.containsSubtype(type, superType.getName());
    }

    public boolean isAccessible(@NotNull String superType, @NotNull TypeMirror type) {
        return this.containsSubtype(type, superType);
    }

    private boolean containsSubtype(@NotNull TypeMirror type, @NotNull String looking) {
        String typeName = toNoGenericString(type.toString());
        if (typeName.equals(looking)) {
            return true;
        } else if (typeName.equals(this.rawObject)) {
            return false;
        } else {
            List<? extends TypeMirror> subtypes = this.env.getTypeUtils().directSupertypes(type);
            Iterator var5 = subtypes.iterator();

            TypeMirror mirror;
            do {
                if (!var5.hasNext()) {
                    return false;
                }

                mirror = (TypeMirror) var5.next();
            } while (!this.containsSubtype(mirror, looking));

            return true;
        }
    }

    @NotNull
    private static String toNoGenericString(@NotNull String type) {
        int genericInfoStartIndex = type.indexOf("<");
        return genericInfoStartIndex == -1 ? type : type.substring(0, genericInfoStartIndex);
    }

    @NotNull
    public TypeMirror getTypeMirror(@NotNull TypeName name) {
        TypeElement typeElement = this.env.getElementUtils().getTypeElement(toNoGenericString(name.toString()));
        if (typeElement == null) {
            throw new NullPointerException("Cannot get type mirror for name " + String.valueOf(name));
        } else {
            return typeElement.asType();
        }
    }

    public MoreTypes(ProcessingEnvironment env) {
        this.env = env;
    }
}
