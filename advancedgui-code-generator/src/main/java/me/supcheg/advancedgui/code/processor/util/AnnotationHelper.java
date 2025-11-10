package me.supcheg.advancedgui.code.processor.util;

import com.palantir.javapoet.AnnotationSpec;
import com.palantir.javapoet.ClassName;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import static java.util.function.Predicate.isEqual;

public class AnnotationHelper {
    public List<AnnotationSpec> addIfNotPresent(List<AnnotationSpec> annotations, Class<? extends Annotation> type) {
        if (annotations.stream().map(AnnotationSpec::type).anyMatch(isEqual(ClassName.get(type)))) {
            return annotations;
        }

        var copy = new ArrayList<>(annotations);
        copy.add(AnnotationSpec.builder(type).build());
        return copy;
    }

    public List<AnnotationSpec> removeIfPresent(List<AnnotationSpec> annotations, Class<? extends Annotation> type) {
        ClassName annotationName = ClassName.get(type);
        if (annotations.stream().map(AnnotationSpec::type).noneMatch(isEqual(annotationName))) {
            return annotations;
        }

        var copy = new ArrayList<>(annotations);
        copy.removeIf((annotationSpec) -> annotationSpec.type().equals(annotationName));
        return copy;
    }
}
