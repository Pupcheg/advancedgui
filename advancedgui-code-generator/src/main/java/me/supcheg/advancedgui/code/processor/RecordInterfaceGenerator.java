package me.supcheg.advancedgui.code.processor;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.TypeSpec;
import lombok.RequiredArgsConstructor;

import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.util.List;

@RequiredArgsConstructor
class RecordInterfaceGenerator extends AnnotationGenerator {
    private final Elements elements;
    private final PropertyResolver propertyResolver;

    private final ObjectImplTypeGenerator objectImplTypeGenerator;
    private final BuilderTypeGenerator builderTypeGenerator;
    private final BuilderImplTypeGenerator builderImplTypeGenerator;

    @Override
    List<TypeSpec> generate(TypeElement typeElement) {
        var packageName = new PackageName(elements.getPackageOf(typeElement));

        var properties = propertyResolver.listProperties(typeElement);

        var objectImplType = objectImplTypeGenerator.objectImplType(packageName, typeElement, properties);
        var builderType = builderTypeGenerator.builderTypeSpec(packageName, typeElement, properties);

        var objectImplTypename = ClassName.get(packageName.toString(), objectImplType.name());

        var builderImplType = builderImplTypeGenerator.builderImplType(packageName, builderType, objectImplTypename, properties);

        return List.of(
                objectImplType,
                builderType,
                builderImplType
        );
    }
}
