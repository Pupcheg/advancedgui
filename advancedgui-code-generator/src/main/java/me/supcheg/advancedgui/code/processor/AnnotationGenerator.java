package me.supcheg.advancedgui.code.processor;

import com.palantir.javapoet.TypeSpec;
import lombok.RequiredArgsConstructor;

import javax.lang.model.element.TypeElement;
import java.util.List;

@RequiredArgsConstructor
class AnnotationGenerator {
    private final PropertyResolver propertyResolver;
    private final NamesResolver namesResolver;
    private final List<? extends TypeGenerator> typeGenerators;

    List<TypeSpec> generate(TypeElement typeElement) {
        var names = namesResolver.namesForObject(typeElement.asType());
        var properties = propertyResolver.listProperties(typeElement);

        return typeGenerators.stream()
                .map(generator -> generator.generate(names, properties))
                .toList();
    }
}
