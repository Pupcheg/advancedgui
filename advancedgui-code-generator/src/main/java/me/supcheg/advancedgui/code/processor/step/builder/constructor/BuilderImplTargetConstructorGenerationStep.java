package me.supcheg.advancedgui.code.processor.step.builder.constructor;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.CodeBlock;
import com.palantir.javapoet.MethodSpec;
import com.palantir.javapoet.TypeSpec.Builder;
import lombok.RequiredArgsConstructor;
import me.supcheg.advancedgui.code.processor.step.GenerationStep;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@RequiredArgsConstructor
public class BuilderImplTargetConstructorGenerationStep implements GenerationStep {
    public static final String PARAMETER_NAME = "impl";
    private final ClassName implClassName;
    private final List<BuilderImplParameterInitializer> initializers;

    public void generate(Builder target) {
        target.addMethod(MethodSpec.constructorBuilder().addParameter(this.implClassName, PARAMETER_NAME).addCode(this.code()).build());
    }

    private CodeBlock code() {
        com.palantir.javapoet.CodeBlock.Builder builder = CodeBlock.builder();

        for (BuilderImplParameterInitializer initializer : this.initializers) {
            builder.add(initializer.copyingInitializer(PARAMETER_NAME));
        }

        return builder.build();
    }
}
