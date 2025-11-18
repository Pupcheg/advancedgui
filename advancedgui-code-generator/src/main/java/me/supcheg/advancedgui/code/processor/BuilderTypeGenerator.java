package me.supcheg.advancedgui.code.processor;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.ParameterSpec;
import com.palantir.javapoet.ParameterizedTypeName;
import com.palantir.javapoet.TypeName;
import com.palantir.javapoet.TypeSpec;
import lombok.RequiredArgsConstructor;
import me.supcheg.advancedgui.code.RecordInterface;

import javax.lang.model.element.Modifier;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.util.Types;
import java.util.List;
import java.util.function.Consumer;

import static com.palantir.javapoet.MethodSpec.methodBuilder;
import static com.palantir.javapoet.TypeSpec.interfaceBuilder;
import static me.supcheg.advancedgui.code.processor.StringUtil.capitalize;
import static me.supcheg.advancedgui.code.processor.StringUtil.decapitalize;
import static me.supcheg.advancedgui.code.processor.TypeNames.genericTypes;
import static me.supcheg.advancedgui.code.processor.TypeNames.simpleName;

@RequiredArgsConstructor
class BuilderTypeGenerator extends TypeGenerator {
    private static final String BUILD_METHOD_NAME = "build";
    private static final String ADD_PREFIX = "add";
    private static final String PUT_PREFIX = "put";

    private final Types types;
    private final Annotations annotations;
    private final NamesResolver namesResolver;
    private final BuilderInterfaces builderInterfaces;
    private final CollectionMethodsResolver collectionResolver;

    @Override
    TypeSpec generate(Names names, List<? extends Property> properties) {
        var genericTypes = genericTypes(names.builder());
        var builder = interfaceBuilder(genericTypes.raw())
                .addTypeVariables(genericTypes.generics())
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(builderInterfaces.builderTypename(names.object()));

        new SetterMethodGenerator(builder, names.builder()).scan(properties);
        new GetterMethodGenerator(builder).scan(properties);

        builder.addMethod(
                methodBuilder(BUILD_METHOD_NAME)
                        .addAnnotations(annotations.nonNull())
                        .addAnnotation(Override.class)
                        .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                        .returns(names.object())
                        .build()
        );

        return builder.build();
    }

    @RequiredArgsConstructor
    private class SetterMethodGenerator extends GenerationPropertyVisitor {
        private final TypeSpec.Builder builder;
        private final TypeName builderType;

        @Override
        public void visitObject(Property.Object property) {
            var setterMethodName = property.name();
            builder.addMethod(
                    methodBuilder(setterMethodName)
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

            if (isRecordInterface(property)) {
                addConsumerMethod(property, setterMethodName);
            }
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

            var addSingleMethodName = ADD_PREFIX + capitalize(element.name());
            builder.addMethod(
                    methodBuilder(addSingleMethodName)
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

            if (isRecordInterface(element)) {
                addConsumerMethod(element, addSingleMethodName);
            }
        }

        @Override
        void visitObjectObjectMap(Property.ObjectObjectMap property) {
            var key = property.key();
            var value = property.value();

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

            builder.addMethod(
                    methodBuilder(PUT_PREFIX + capitalize(value.name()))
                            .addAnnotations(annotations.nonNull())
                            .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                            .addParameter(
                                    ParameterSpec.builder(key.typename(), key.name())
                                            .addAnnotations(annotations.nonNull())
                                            .build()
                            )
                            .addParameter(
                                    ParameterSpec.builder(value.typename(), value.name())
                                            .addAnnotations(annotations.nonNull())
                                            .build()
                            )
                            .returns(builderType)
                            .build()
            );
        }

        private void addConsumerMethod(Property property, String originalMethodName) {
            var propertyNames = namesResolver.namesForObject(property.type());
            var consumerParameterName = property.name();

            builder.addMethod(
                    methodBuilder(originalMethodName)
                            .addAnnotations(annotations.nonNull())
                            .addModifiers(Modifier.PUBLIC, Modifier.DEFAULT)
                            .addParameter(
                                    ParameterSpec.builder(ParameterizedTypeName.get(ClassName.get(Consumer.class), propertyNames.builder()), consumerParameterName)
                                            .addAnnotations(annotations.nonNull())
                                            .build()
                            )
                            .addCode("return $L($T.$L($L));", originalMethodName, types.erasure(property.type()), decapitalize(simpleName(property.typename())), consumerParameterName)
                            .returns(builderType)
                            .build()
            );
        }

        private boolean isRecordInterface(Property property) {
            return property.type() instanceof DeclaredType declaredType
                   && declaredType.asElement().getAnnotationsByType(RecordInterface.class).length > 0;
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

        @Override
        void visitObjectObjectMap(Property.ObjectObjectMap property) {
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
