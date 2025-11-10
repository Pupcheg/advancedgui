package me.supcheg.advancedgui.code.processor.step.builder.constructor;

import com.palantir.javapoet.CodeBlock;
import com.palantir.javapoet.MethodSpec;
import com.palantir.javapoet.TypeSpec.Builder;
import me.supcheg.advancedgui.code.processor.step.GenerationStep;

import java.util.List;

public class BuilderImplNoArgsConstructorGenerationStep implements GenerationStep {
    private final List<BuilderImplParameterInitializer> initializers;

    public void generate(Builder target) {
        target.addMethod(MethodSpec.constructorBuilder().addCode(this.codeBlock()).build());
    }

    private CodeBlock codeBlock() {
        var builder = CodeBlock.builder();

        for (BuilderImplParameterInitializer initializer : this.initializers) {
            builder.add(initializer.newValueInitializer());
        }

        return builder.build();
    }

    public BuilderImplNoArgsConstructorGenerationStep(List<BuilderImplParameterInitializer> initializers) {
        this.initializers = initializers;
    }
}
