package me.supcheg.advancedgui.code.processor;

import com.palantir.javapoet.TypeSpec;
import lombok.RequiredArgsConstructor;

import javax.lang.model.element.TypeElement;
import java.util.List;

@RequiredArgsConstructor
public class BuilderInterfaceGenerator extends AnnotationGenerator {
    private final PropertyResolver propertyResolver;
    private final BuilderTypeGenerator builderTypeGenerator;

    @Override
    List<TypeSpec> generate(TypeElement typeElement) {
        return List.of(builderTypeGenerator.builderTypeSpec(typeElement, propertyResolver.listProperties(typeElement)).spec());
    }
}
