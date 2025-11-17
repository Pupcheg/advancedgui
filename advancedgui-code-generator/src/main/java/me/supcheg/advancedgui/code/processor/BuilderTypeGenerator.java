package me.supcheg.advancedgui.code.processor;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.ParameterSpec;
import com.palantir.javapoet.TypeName;
import com.palantir.javapoet.TypeSpec;
import lombok.RequiredArgsConstructor;
import me.supcheg.advancedgui.code.PackageName;
import me.supcheg.advancedgui.code.processor.collection.CollectionMethodsResolver;
import me.supcheg.advancedgui.code.processor.property.ObjectCollectionProperty;
import me.supcheg.advancedgui.code.processor.property.ObjectProperty;
import me.supcheg.advancedgui.code.processor.property.PrimitiveProperty;
import me.supcheg.advancedgui.code.processor.property.Property;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.util.List;
import java.util.function.UnaryOperator;

import static com.palantir.javapoet.MethodSpec.methodBuilder;
import static com.palantir.javapoet.TypeSpec.interfaceBuilder;
import static me.supcheg.advancedgui.code.processor.StringUtil.capitalize;

@RequiredArgsConstructor
public class BuilderTypeGenerator {
    private static final String BUILDER_SUFFIX = "Builder";
    private static final String BUILD_METHOD_NAME = "build";
    private static final String ADD_PREFIX = "add";

    private final Annotations annotations;
    private final CollectionMethodsResolver collectionResolver;
    private final List<UnaryOperator<TypeMirror>> superInterfaces;

    public TypeSpec builderTypeSpec(PackageName packageName, TypeElement subjectType, List<? extends Property> properties) {
        var builderType = ClassName.get(packageName.toString(), subjectType.getSimpleName() + BUILDER_SUFFIX);

        var builder = interfaceBuilder(builderType)
                .addModifiers(Modifier.PUBLIC);

        for (var superInterface : superInterfaces) {
            builder.addSuperinterface(superInterface.apply(subjectType.asType()));
        }

        new PropertyAppender(builder, builderType).scan(properties);

        builder.addMethod(
                methodBuilder(BUILD_METHOD_NAME)
                        .addAnnotations(annotations.nonNull())
                        .addAnnotation(Override.class)
                        .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                        .returns(TypeName.get(subjectType.asType()))
                        .build()
        );

        return builder.build();
    }

    @RequiredArgsConstructor
    private class PropertyAppender implements GenerationPropertyVisitor {
        private final TypeSpec.Builder builder;
        private final TypeName builderType;

        @Override
        public void visitObject(ObjectProperty property) {
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
        public void visitPrimitive(PrimitiveProperty property) {
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
        public void visitObjectCollection(ObjectCollectionProperty property) {

            var collection = collectionResolver.methodsFor(property.type());
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
                            .addCode("return $L($T.$L($L));", setCollectionMethodName, collection.erasedType(), collection.singletonImmutableFactory(), element.name())
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
                            .addCode("return $L($T.$L($L));", addMultiMethodName, collection.erasedType(), collection.singletonImmutableFactory(), element.name())
                            .build()
            );
        }
    }
}
