package me.supcheg.advancedgui.code.processor.util;

import com.palantir.javapoet.AnnotationSpec;
import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.TypeName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class AnnotationHelper {
    private final Set<TypeName> nullabilityAnnotations = Set.of(TypeName.get(Nullable.class), TypeName.get(NotNull.class));

    public List<AnnotationSpec> putNullabilityAnnotation(List<AnnotationSpec> annotations, Class<? extends Annotation> type) {
        if (!isNullabilityAnnotation(TypeName.get(type))) {
            throw new IllegalArgumentException("Unsupported nullability annotation: " + type);
        } else {
            List<AnnotationSpec> copy = new ArrayList<>(annotations);
            int i = indexOfNullabilityAnnotation(annotations);
            if (i != -1) {
                copy.set(i, annotationSpec(type));
            } else {
                copy.addFirst(annotationSpec(type));
            }

            return copy;
        }
    }

    public List<AnnotationSpec> removeNullabilityAnnotations(List<AnnotationSpec> annotations) {
        List<AnnotationSpec> copy = new ArrayList<>(annotations);
        copy.removeIf(this::isNullabilityAnnotation);
        return copy;
    }

    public List<AnnotationSpec> addIfNotPresent(List<AnnotationSpec> annotations, Class<? extends Annotation> type) {
        if (annotations.stream().map(AnnotationSpec::type).anyMatch(Predicate.isEqual(ClassName.get(type)))) {
            return annotations;
        } else {
            List<AnnotationSpec> copy = new ArrayList<>(annotations);
            copy.add(annotationSpec(type));
            return copy;
        }
    }

    public List<AnnotationSpec> removeIfPresent(List<AnnotationSpec> annotations, Class<? extends Annotation> type) {
        ClassName annotationName = ClassName.get(type);
        if (annotations.stream().map(AnnotationSpec::type).noneMatch(Predicate.isEqual(annotationName))) {
            return annotations;
        } else {
            List<AnnotationSpec> copy = new ArrayList<>(annotations);
            copy.removeIf((annotationSpec) -> annotationSpec.type().equals(annotationName));
            return copy;
        }
    }

    private static AnnotationSpec annotationSpec(Class<? extends Annotation> type) {
        return AnnotationSpec.builder(type).build();
    }

    private int indexOfNullabilityAnnotation(List<AnnotationSpec> annotations) {
        for (int i = 0; i < annotations.size(); ++i) {
            if (isNullabilityAnnotation(annotations.get(i))) {
                return i;
            }
        }

        return -1;
    }

    public boolean isNullabilityAnnotation(AnnotationSpec spec) {
        return isNullabilityAnnotation(spec.type());
    }

    public boolean isNullabilityAnnotation(TypeName type) {
        return nullabilityAnnotations.contains(type);
    }
}
