package me.supcheg.advancedgui.code.processor.strategy;

import com.palantir.javapoet.TypeSpec;
import lombok.RequiredArgsConstructor;
import me.supcheg.advancedgui.code.processor.parameter.ParameterSpecLookup;
import me.supcheg.advancedgui.code.processor.step.ConstructorGenerationStep;

import javax.lang.model.element.Element;

import static com.palantir.javapoet.TypeSpec.recordBuilder;

@RequiredArgsConstructor
public class SimpleAdventureLikeGenerationStrategy implements AdventureLikeGenerationStrategy {
    private final ParameterSpecLookup parameterSpecLookup;

    public TypeSpec generate(Element element) {
        var builder = recordBuilder(element.getSimpleName() + "Impl")
                .addSuperinterface(element.asType());
        var parameters = parameterSpecLookup.listRecordParametersForInterface(element);
        new ConstructorGenerationStep(parameters).generate(builder);
        return builder.build();
    }
}
