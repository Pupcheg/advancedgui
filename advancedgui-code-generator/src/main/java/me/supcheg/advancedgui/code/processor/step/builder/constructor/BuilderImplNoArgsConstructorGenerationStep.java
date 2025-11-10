package me.supcheg.advancedgui.code.processor.step.builder.constructor;

import com.palantir.javapoet.CodeBlock;
import com.palantir.javapoet.TypeSpec.Builder;
import lombok.RequiredArgsConstructor;
import me.supcheg.advancedgui.code.processor.step.GenerationStep;

import java.util.List;

import static com.palantir.javapoet.MethodSpec.constructorBuilder;

@RequiredArgsConstructor
public class BuilderImplNoArgsConstructorGenerationStep implements GenerationStep {
    private final List<BuilderImplParameterInitializer> initializers;

    public void generate(Builder target) {
        target.addMethod(
                constructorBuilder()
                        .addCode(this.codeBlock())
                        .build()
        );
    }

    private CodeBlock codeBlock() {
        var builder = CodeBlock.builder();

        for (var initializer : initializers) {
            builder.add(initializer.newValueInitializer());
        }

        return builder.build();
    }
}
