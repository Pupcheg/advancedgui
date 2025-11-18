package me.supcheg.advancedgui.code.processor;

import com.palantir.javapoet.MethodSpec;
import com.palantir.javapoet.ParameterSpec;
import com.palantir.javapoet.TypeSpec;
import lombok.RequiredArgsConstructor;

import javax.lang.model.element.Modifier;
import java.util.List;

import static com.palantir.javapoet.MethodSpec.methodBuilder;

@RequiredArgsConstructor
class ObjectImplTypeGenerator extends TypeGenerator {
    private final Annotations annotations;

    @Override
    TypeSpec generate(Names names, List<? extends Property> properties) {
        var genericTypes = TypeNames.genericTypes(names.objectImpl());
        var builder = TypeSpec.recordBuilder(genericTypes.raw())
                .addTypeVariables(genericTypes.generics())
                .addSuperinterface(names.object());

        var constructorBuilder = MethodSpec.constructorBuilder();
        new PropertyAppender(constructorBuilder).scan(properties);
        builder.recordConstructor(constructorBuilder.build());

        builder.addMethod(
                methodBuilder("toBuilder")
                        .addAnnotations(annotations.nonNull())
                        .addAnnotation(Override.class)
                        .addModifiers(Modifier.PUBLIC)
                        .addCode("return new $T(this);", names.builderImpl())
                        .returns(names.builderImpl())
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
