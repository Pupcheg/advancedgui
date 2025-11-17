package me.supcheg.advancedgui.code.processor;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.MethodSpec;
import com.palantir.javapoet.ParameterSpec;
import com.palantir.javapoet.TypeSpec;
import lombok.RequiredArgsConstructor;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.util.List;

import static com.palantir.javapoet.MethodSpec.methodBuilder;

@RequiredArgsConstructor
class ObjectImplTypeGenerator {
    private final Annotations annotations;

    TypeSpec objectImplType(PackageName packageName, TypeElement subjectType, List<? extends Property> properties) {
        TypeSpec.Builder builder = TypeSpec.recordBuilder(subjectType.getSimpleName() + "Impl")
                .addSuperinterface(subjectType.asType());

        var constructorBuilder = MethodSpec.constructorBuilder();
        new PropertyAppender(constructorBuilder).scan(properties);
        builder.recordConstructor(constructorBuilder.build());

        var builderTypename = ClassName.get(packageName.toString(), subjectType.getSimpleName() + "BuilderImpl");

        builder.addMethod(
                methodBuilder("toBuilder")
                        .addAnnotations(annotations.nonNull())
                        .addAnnotation(Override.class)
                        .addModifiers(Modifier.PUBLIC)
                        .addCode("return new $T(this);", builderTypename)
                        .returns(builderTypename)
                        .build()
        );

        return builder.build();
    }

    @RequiredArgsConstructor
    private class PropertyAppender extends GenerationPropertyVisitor {
        private final MethodSpec.Builder constructor;

        @Override
        public void visitObject(Property.Object property) {
            constructor.addParameter(
                    ParameterSpec.builder(property.typename(), property.name())
                            .addAnnotations(annotations.nonNull())
                            .build()
            );
        }

        @Override
        public void visitPrimitive(Property.Primitive property) {
            constructor.addParameter(property.typename(), property.name());
        }

        @Override
        public void visitObjectCollection(Property.ObjectCollection property) {
            constructor.addParameter(
                    ParameterSpec.builder(property.typename(), property.name())
                            .addAnnotations(annotations.unmodifiable())
                            .addAnnotations(annotations.nonNull())
                            .build()
            );
        }
    }
}
