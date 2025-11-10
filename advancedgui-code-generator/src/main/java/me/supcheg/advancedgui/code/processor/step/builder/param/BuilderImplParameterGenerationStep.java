package me.supcheg.advancedgui.code.processor.step.builder.param;

import com.palantir.javapoet.AnnotationSpec;
import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.CodeBlock;
import com.palantir.javapoet.FieldSpec;
import com.palantir.javapoet.ParameterSpec;
import com.palantir.javapoet.TypeSpec.Builder;
import lombok.RequiredArgsConstructor;
import me.supcheg.advancedgui.code.processor.step.GenerationStep;
import me.supcheg.advancedgui.code.processor.util.AnnotationHelper;
import org.jetbrains.annotations.NotNull;

import javax.lang.model.element.Modifier;
import java.util.List;
import java.util.Objects;

import static com.palantir.javapoet.MethodSpec.methodBuilder;

@RequiredArgsConstructor
public class BuilderImplParameterGenerationStep implements GenerationStep {
    protected final ParameterSpec parameter;
    protected final AnnotationHelper annotationHelper;
    protected final ClassName builderImplClass;

    public void generate(Builder target) {
        target.addField(fieldSpec().build());
        target.addMethod(setter().build());
        target.addMethod(getter().build());
    }

    protected FieldSpec.Builder fieldSpec() {
        return FieldSpec.builder(parameter.type().box(), parameter.name())
                .addModifiers(Modifier.PRIVATE)
                .addAnnotations(fieldAnnotations());
    }

    protected List<AnnotationSpec> fieldAnnotations() {
        return parameter.annotations();
    }

    protected com.palantir.javapoet.MethodSpec.Builder setter() {
        return methodBuilder(parameter.name())
                .addAnnotation(NotNull.class)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(
                        ParameterSpec.builder(parameter.type(), parameter.name())
                                .addAnnotations(setterParameterAnnotations())
                                .build()
                )
                .returns(builderImplClass)
                .addCode(setterCode());
    }

    protected List<AnnotationSpec> setterParameterAnnotations() {
        return parameter.annotations();
    }

    protected CodeBlock setterCode() {
        var name = parameter.name();
        return CodeBlock.builder()
                .add("$T.requireNonNull($L, $S);\n", Objects.class, name, name)
                .add("$L = $L;\n", name, name)
                .add("return this;\n")
                .build();
    }

    protected com.palantir.javapoet.MethodSpec.Builder getter() {
        return methodBuilder(parameter.name())
                .addAnnotations(getterAnnotations())
                .addModifiers(Modifier.PUBLIC)
                .returns(parameter.type().box())
                .addCode(getterCode());
    }

    protected List<AnnotationSpec> getterAnnotations() {
        return annotationHelper.addIfNotPresent(parameter.annotations(), Override.class);
    }

    protected CodeBlock getterCode() {
        return CodeBlock.of("return $L;\n", parameter.name());
    }
}
