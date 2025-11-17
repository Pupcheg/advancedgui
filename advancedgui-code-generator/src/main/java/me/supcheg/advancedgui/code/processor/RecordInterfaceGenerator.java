package me.supcheg.advancedgui.code.processor;

import com.palantir.javapoet.TypeSpec;
import lombok.RequiredArgsConstructor;

import javax.lang.model.element.TypeElement;
import java.util.List;

@RequiredArgsConstructor
class RecordInterfaceGenerator extends AnnotationGenerator {
    private final PropertyResolver propertyResolver;

    private final ObjectImplTypeGenerator objectImplTypeGenerator;
    private final BuilderTypeGenerator builderTypeGenerator;
    private final BuilderImplTypeGenerator builderImplTypeGenerator;

    @Override
    List<TypeSpec> generate(TypeElement typeElement) {
        var properties = propertyResolver.listProperties(typeElement);

        var objectImplType = objectImplTypeGenerator.objectImplType(typeElement, properties);
        var builderType = builderTypeGenerator.builderTypeSpec(typeElement, properties);
        var builderImplType = builderImplTypeGenerator.builderImplType(builderType.name(), objectImplType.name(), properties);

        return List.of(
                objectImplType.spec(),
                builderType.spec(),
                builderImplType.spec()
        );
    }
}
