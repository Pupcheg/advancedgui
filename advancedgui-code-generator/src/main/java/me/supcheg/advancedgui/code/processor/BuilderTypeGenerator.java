package me.supcheg.advancedgui.code.processor;

import com.palantir.javapoet.ParameterSpec;
import com.palantir.javapoet.TypeName;
import com.palantir.javapoet.TypeSpec;
import lombok.RequiredArgsConstructor;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.util.List;
import java.util.function.UnaryOperator;

import static com.palantir.javapoet.MethodSpec.methodBuilder;
import static com.palantir.javapoet.TypeSpec.interfaceBuilder;
import static me.supcheg.advancedgui.code.processor.StringUtil.capitalize;
import static me.supcheg.advancedgui.code.processor.TypeNames.genericTypes;
import static me.supcheg.advancedgui.code.processor.TypeNames.rename;

@RequiredArgsConstructor
class BuilderTypeGenerator {
    private static final String BUILDER_SUFFIX = "Builder";
    private static final String BUILD_METHOD_NAME = "build";
    private static final String ADD_PREFIX = "add";

    private final Annotations annotations;
    private final CollectionMethodsResolver collectionResolver;
    private final List<UnaryOperator<TypeMirror>> superInterfaces;

    GenerationResult builderTypeSpec(TypeElement subjectType, List<? extends Property> properties) {
        var builderType = rename(subjectType.asType(), name -> name + BUILDER_SUFFIX);

        var genericTypes = genericTypes(builderType);
        var builder = interfaceBuilder(genericTypes.raw())
                .addTypeVariables(genericTypes.generics())
                .addModifiers(Modifier.PUBLIC);

        for (var superInterface : superInterfaces) {
            builder.addSuperinterface(superInterface.apply(subjectType.asType()));
        }

        new SetterMethodGenerator(builder, builderType).scan(properties);
        new GetterMethodGenerator(builder).scan(properties);

        builder.addMethod(
                methodBuilder(BUILD_METHOD_NAME)
                        .addAnnotations(annotations.nonNull())
                        .addAnnotation(Override.class)
                        .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                        .returns(TypeName.get(subjectType.asType()))
                        .build()
        );

        return new GenerationResult(builder.build(), builderType);
    }

    @RequiredArgsConstructor
    private class SetterMethodGenerator extends GenerationPropertyVisitor {
        private final TypeSpec.Builder builder;
        private final TypeName builderType;

        @Override
        public void visitObject(Property.Object property) {
            builder.addMethod(
                    methodBuilder(property.name())
                            .addAnnotations(annotations.nonNull())
                            .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                            .addParameter(
                                    ParameterSpec.builder(property.typename(), property.name())
                                            .addAnnotations(annotations.nonNull())
                                            .build()
                            )
                            .returns(builderType)
                            .build()
            );
        }

        @Override
        public void visitPrimitive(Property.Primitive property) {
            builder.addMethod(
                    methodBuilder(property.name())
                            .addAnnotations(annotations.nonNull())
                            .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                            .addParameter(property.typename(), property.name())
                            .returns(builderType)
                            .build()
            );
        }

        @Override
        public void visitObjectCollection(Property.ObjectCollection property) {

            var singletonFactory = collectionResolver.methodsFor(property.type()).singletonImmutableFactory();
            var element = property.element();

            var setCollectionMethodName = property.name();

            builder.addMethod(
                    methodBuilder(setCollectionMethodName)
                            .addAnnotations(annotations.nonNull())
                            .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                            .addParameter(
                                    ParameterSpec.builder(property.typename(), property.name())
                                            .addAnnotations(annotations.nonNull())
                                            .build()
                            )
                            .returns(builderType)
                            .build()
            );
            builder.addMethod(
                    methodBuilder(element.name())
                            .addAnnotations(annotations.nonNull())
                            .addModifiers(Modifier.PUBLIC, Modifier.DEFAULT)
                            .addParameter(
                                    ParameterSpec.builder(element.typename(), element.name())
                                            .addAnnotations(annotations.nonNull())
                                            .build()
                            )
                            .returns(builderType)
                            .addCode(requireNonNull(element))
                            .addCode("return $L($T.$L($L));", setCollectionMethodName, singletonFactory.containingErasedType(), singletonFactory.methodname(), element.name())
                            .build()
            );

            var addMultiMethodName = ADD_PREFIX + capitalize(property.name());

            builder.addMethod(
                    methodBuilder(addMultiMethodName)
                            .addAnnotations(annotations.nonNull())
                            .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                            .addParameter(
                                    ParameterSpec.builder(property.typename(), property.name())
                                            .addAnnotations(annotations.nonNull())
                                            .build()
                            )
                            .returns(builderType)
                            .build()
            );
            builder.addMethod(
                    methodBuilder(ADD_PREFIX + capitalize(element.name()))
                            .addAnnotations(annotations.nonNull())
                            .addModifiers(Modifier.PUBLIC, Modifier.DEFAULT)
                            .addParameter(
                                    ParameterSpec.builder(element.typename(), element.name())
                                            .addAnnotations(annotations.nonNull())
                                            .build()
                            )
                            .returns(builderType)
                            .addCode(requireNonNull(element))
                            .addCode("return $L($T.$L($L));", addMultiMethodName, singletonFactory.containingErasedType(), singletonFactory.methodname(), element.name())
                            .build()
            );
        }
    }

    @RequiredArgsConstructor
    private class GetterMethodGenerator extends GenerationPropertyVisitor {
        private final TypeSpec.Builder builder;

        @Override
        public void visitObject(Property.Object property) {
            builder.addMethod(
                    methodBuilder(property.name())
                            .addAnnotations(annotations.nullable())
                            .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                            .returns(property.typename())
                            .build()
            );
        }

        @Override
        public void visitPrimitive(Property.Primitive property) {
            builder.addMethod(
                    methodBuilder(property.name())
                            .addAnnotations(annotations.nullable())
                            .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                            .returns(property.typename().box())
                            .build()
            );
        }

        @Override
        public void visitObjectCollection(Property.ObjectCollection property) {
            builder.addMethod(
                    methodBuilder(property.name())
                            .addAnnotations(annotations.nonNull())
                            .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                            .returns(property.typename())
                            .build()
            );
        }
    }
}
