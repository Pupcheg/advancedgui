package me.supcheg.advancedgui.code.processor;

import com.palantir.javapoet.TypeSpec;
import lombok.RequiredArgsConstructor;

import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.util.List;

@RequiredArgsConstructor
public class BuilderInterfaceGenerator extends AnnotationGenerator {
    private final Elements elements;
    private final PropertyResolver propertyResolver;

    private final BuilderTypeGenerator builderTypeGenerator;

    @Override
    List<TypeSpec> generate(TypeElement typeElement) {
        var packageName = new PackageName(elements.getPackageOf(typeElement));
        var properties = propertyResolver.listProperties(typeElement);

        var builderType = builderTypeGenerator.builderTypeSpec(packageName, typeElement, properties);

        return List.of(builderType);
    }
}
