package me.supcheg.advancedgui.code.processor.step.builder.param;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.CodeBlock;
import com.palantir.javapoet.ParameterSpec;
import me.supcheg.advancedgui.code.processor.util.AnnotationHelper;

public class BuilderImplPrimitiveParameterGenerationStep extends BuilderImplParameterGenerationStep {
    public BuilderImplPrimitiveParameterGenerationStep(ParameterSpec parameter, AnnotationHelper annotationHelper, ClassName builderImplClass) {
        super(parameter, annotationHelper, builderImplClass);
    }

    protected CodeBlock setterCode() {
        var name = parameter.name();
        return CodeBlock.builder().add("this.$L = $L;\n", name, name).add("return this;").build();
    }
}
