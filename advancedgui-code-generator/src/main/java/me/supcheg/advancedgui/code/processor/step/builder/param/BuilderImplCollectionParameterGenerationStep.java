package me.supcheg.advancedgui.code.processor.step.builder.param;

import com.palantir.javapoet.AnnotationSpec;
import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.CodeBlock;
import com.palantir.javapoet.FieldSpec.Builder;
import com.palantir.javapoet.ParameterSpec;
import me.supcheg.advancedgui.code.processor.util.AnnotationHelper;
import org.jetbrains.annotations.Unmodifiable;

import javax.lang.model.element.Modifier;
import java.util.List;
import java.util.Objects;

public class BuilderImplCollectionParameterGenerationStep extends BuilderImplParameterGenerationStep {
    public BuilderImplCollectionParameterGenerationStep(ParameterSpec parameter, AnnotationHelper annotationHelper, ClassName builderImplClass) {
        super(parameter, annotationHelper, builderImplClass);
    }

    protected Builder fieldSpec() {
        return super.fieldSpec().addModifiers(Modifier.FINAL);
    }

    protected List<AnnotationSpec> fieldAnnotations() {
        return annotationHelper.removeIfPresent(super.fieldAnnotations(), Unmodifiable.class);
    }

    protected CodeBlock setterCode() {
        var name = parameter.name();
        return CodeBlock.builder()
                .add("$T.requireNonNull($L, $S);\n", Objects.class, name, name)
                .add("this.$L.clear();\n", name).add("this.$L.addAll($L);\n", name, name)
                .add("return this;\n")
                .build();
    }

}
