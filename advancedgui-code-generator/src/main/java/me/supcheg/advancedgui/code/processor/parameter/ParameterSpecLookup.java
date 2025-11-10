package me.supcheg.advancedgui.code.processor.parameter;

import com.palantir.javapoet.AnnotationSpec;
import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.ParameterSpec;
import com.palantir.javapoet.TypeName;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeKind;
import javax.lang.model.util.ElementFilter;
import java.util.ArrayList;
import java.util.List;

public class ParameterSpecLookup {
    public List<ParameterSpec> listRecordParametersForInterface(Element element) {
        var parameters = new ArrayList<ParameterSpec>();

        for (ExecutableElement method : ElementFilter.methodsIn(element.getEnclosedElements())) {
            if (this.isValueMethod(method)) {
                parameters.add(this.makeParameter(method));
            }
        }

        return parameters;
    }

    private boolean isValueMethod(ExecutableElement method) {
        return !method.isDefault() && !method.getModifiers().contains(Modifier.STATIC) && method.getReturnType().getKind() != TypeKind.VOID && method.getParameters().isEmpty();
    }

    private ParameterSpec makeParameter(ExecutableElement method) {
        var returnType = method.getReturnType();
        return ParameterSpec.builder(TypeName.get(returnType), method.getSimpleName().toString())
                .addAnnotations(returnType.getAnnotationMirrors().stream().map(this::convertAnnotationMirrorToAnnotationSpec)::iterator)
                .build();
    }

    private AnnotationSpec convertAnnotationMirrorToAnnotationSpec(AnnotationMirror annotationMirror) {
        var annotationType = annotationMirror.getAnnotationType();
        var builder = AnnotationSpec.builder((ClassName) ClassName.get(annotationType));
        annotationMirror.getElementValues().forEach((element, value) -> builder.addMember(element.getSimpleName().toString(), "$L", value.getValue()));
        return builder.build();
    }
}
