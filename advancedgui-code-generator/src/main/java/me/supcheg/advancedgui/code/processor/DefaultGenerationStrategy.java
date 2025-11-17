package me.supcheg.advancedgui.code.processor;

import com.palantir.javapoet.TypeSpec;
import lombok.RequiredArgsConstructor;
import me.supcheg.advancedgui.code.processor.property.PropertyResolver;

import javax.lang.model.element.TypeElement;
import java.util.List;

@RequiredArgsConstructor
public class DefaultGenerationStrategy implements GenerationStrategy {
    private final PropertyResolver propertyResolver;

    private final ObjectImplTypeGenerator objectImplTypeGenerator;
    private final BuilderTypeGenerator builderTypeGenerator;

    @Override
    public List<TypeSpec> generate(TypeElement typeElement) {
        var properties = propertyResolver.listProperties(typeElement);

        var objectImplType = objectImplTypeGenerator.objectImplType(typeElement, properties);
        var builderType = builderTypeGenerator.builderTypeSpec(typeElement, properties);

        return List.of(
                objectImplType,
                builderType
        );
    }
}
