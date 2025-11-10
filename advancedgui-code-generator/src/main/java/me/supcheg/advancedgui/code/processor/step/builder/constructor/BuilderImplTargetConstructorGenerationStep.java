package me.supcheg.advancedgui.code.processor.step.builder.constructor;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.CodeBlock;
import com.palantir.javapoet.TypeSpec.Builder;
import lombok.RequiredArgsConstructor;
import me.supcheg.advancedgui.code.processor.step.GenerationStep;

import java.util.List;

import static com.palantir.javapoet.MethodSpec.constructorBuilder;

@RequiredArgsConstructor
public class BuilderImplTargetConstructorGenerationStep implements GenerationStep {
    public static final String PARAMETER_NAME = "impl";
    private final ClassName implClassName;
    private final List<BuilderImplParameterInitializer> initializers;

    public void generate(Builder target) {
        target.addMethod(
                constructorBuilder()
                        .addParameter(implClassName, PARAMETER_NAME)
                        .addCode(code())
                        .build()
        );
    }

    private CodeBlock code() {
        var builder = CodeBlock.builder();

        for (var initializer : initializers) {
            builder.add(initializer.copyingInitializer(PARAMETER_NAME));
        }

        return builder.build();
    }
}
