package me.supcheg.advancedgui.code.processor;

import com.palantir.javapoet.AnnotationSpec;
import com.palantir.javapoet.ClassName;

import javax.lang.model.element.TypeElement;
import java.util.List;

class Annotations {
    private final Iterable<AnnotationSpec> nullable;
    private final Iterable<AnnotationSpec> nonNull;
    private final Iterable<AnnotationSpec> unmodifiable;

    Annotations(TypeElement nullable, TypeElement nonNull, TypeElement unmodifiable) {
        this.nullable = iterable(nullable);
        this.nonNull = iterable(nonNull);
        this.unmodifiable = iterable(unmodifiable);
    }

    private static Iterable<AnnotationSpec> iterable(TypeElement annotationType) {
        return List.of(
                AnnotationSpec.builder(ClassName.get(annotationType))
                        .build()
        );
    }

    Iterable<AnnotationSpec> nullable() {
        return nullable;
    }

    Iterable<AnnotationSpec> nonNull() {
        return nonNull;
    }

    Iterable<AnnotationSpec> unmodifiable() {
        return unmodifiable;
    }
}
