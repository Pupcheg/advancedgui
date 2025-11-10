package me.supcheg.advancedgui.code.processor.step;

import com.palantir.javapoet.ParameterSpec;
import com.palantir.javapoet.TypeSpec.Builder;
import lombok.RequiredArgsConstructor;

import static com.palantir.javapoet.MethodSpec.constructorBuilder;

@RequiredArgsConstructor
public class ConstructorGenerationStep implements GenerationStep {
    private final Iterable<ParameterSpec> parameters;

    public void generate(Builder target) {
        target.recordConstructor(
                constructorBuilder()
                        .addParameters(parameters)
                        .build()
        );
    }
}
