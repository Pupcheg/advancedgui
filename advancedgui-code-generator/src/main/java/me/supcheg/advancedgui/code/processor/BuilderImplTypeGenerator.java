package me.supcheg.advancedgui.code.processor;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.CodeBlock;
import com.palantir.javapoet.FieldSpec;
import com.palantir.javapoet.MethodSpec;
import com.palantir.javapoet.ParameterSpec;
import com.palantir.javapoet.TypeName;
import com.palantir.javapoet.TypeSpec;
import lombok.RequiredArgsConstructor;
import me.supcheg.advancedgui.code.PackageName;
import me.supcheg.advancedgui.code.processor.collection.CollectionMethods.ConstructorCopyFactory;
import me.supcheg.advancedgui.code.processor.collection.CollectionMethods.ConstructorEmptyFactory;
import me.supcheg.advancedgui.code.processor.collection.CollectionMethods.MethodCopyFactory;
import me.supcheg.advancedgui.code.processor.collection.CollectionMethods.MethodEmptyFactory;
import me.supcheg.advancedgui.code.processor.collection.CollectionMethodsResolver;
import me.supcheg.advancedgui.code.processor.property.ObjectCollectionProperty;
import me.supcheg.advancedgui.code.processor.property.ObjectProperty;
import me.supcheg.advancedgui.code.processor.property.PrimitiveProperty;
import me.supcheg.advancedgui.code.processor.property.Property;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.type.ReferenceType;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.palantir.javapoet.CodeBlock.joining;
import static com.palantir.javapoet.MethodSpec.methodBuilder;
import static me.supcheg.advancedgui.code.processor.StringUtil.capitalize;

@RequiredArgsConstructor
public class BuilderImplTypeGenerator {
    private final CollectionMethodsResolver collectionResolver;
    private final Annotations annotations;

    public TypeSpec builderImplType(PackageName packageName, TypeSpec builderType, TypeName subjectImplType, List<? extends Property> properties) {
        var builderImplTypename = ClassName.get(packageName.toString(), builderType.name() + "Impl");

        var builder = TypeSpec.classBuilder(builderImplTypename)
                .addSuperinterface(ClassName.get(packageName.toString(), builderType.name()));

        new FieldGenerator(builder).scan(properties);

        var noArgsConstructorBuilder = MethodSpec.constructorBuilder();
        new NoArgsConstructorGenerator(noArgsConstructorBuilder).scan(properties);
        builder.addMethod(noArgsConstructorBuilder.build());

        var objectCopyConstructorBuilder = MethodSpec.constructorBuilder()
                .addParameter(
                        ParameterSpec.builder(subjectImplType, "impl")
                                .addAnnotations(annotations.nonNull())
                                .build()
                );
        new ObjectCopyConstructorGenerator(objectCopyConstructorBuilder).scan(properties);
        builder.addMethod(objectCopyConstructorBuilder.build());

        new SetterMethodGenerator(builder, builderImplTypename).scan(properties);
        new GetterMethodGenerator(builder).scan(properties);

        var buildMethodBuilder = MethodSpec.methodBuilder("build")
                .addAnnotations(annotations.nonNull())
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(subjectImplType);

        var buildMethodCodeBuilder = CodeBlock.builder();
        buildMethodBuilder.addCode("return new $T($[\n", subjectImplType);

        var statements = new ArrayList<CodeBlock>();
        new BuildMethodCodeGeneratorGenerator(statements).scan(properties);
        buildMethodBuilder.addCode(statements.stream().collect(joining(",\n")));

        buildMethodCodeBuilder.add("\n$]);");
        buildMethodBuilder.addCode(buildMethodCodeBuilder.build());

        builder.addMethod(buildMethodBuilder.build());

        return builder.build();
    }

    @RequiredArgsConstructor
    private final class FieldGenerator implements GenerationPropertyVisitor {
        private final TypeSpec.Builder builder;

        @Override
        public void visitObject(ObjectProperty property) {
            builder.addField(
                    FieldSpec.builder(property.typename(), property.name())
                            .addModifiers(Modifier.PRIVATE)
                            .addAnnotations(annotations.nullable())
                            .build()
            );
        }

        @Override
        public void visitPrimitive(PrimitiveProperty property) {
            builder.addField(
                    FieldSpec.builder(property.typename().box(), property.name())
                            .addModifiers(Modifier.PRIVATE)
                            .addAnnotations(annotations.nullable())
                            .build()
            );
        }

        @Override
        public void visitObjectCollection(ObjectCollectionProperty property) {
            builder.addField(
                    FieldSpec.builder(property.typename(), property.name())
                            .addModifiers(Modifier.PRIVATE, Modifier.FINAL)
                            .addAnnotations(annotations.nonNull())
                            .build()
            );
        }
    }

    @RequiredArgsConstructor
    private final class NoArgsConstructorGenerator implements GenerationPropertyVisitor {
        private final MethodSpec.Builder builder;

        @Override
        public void visitObjectCollection(ObjectCollectionProperty property) {
            var collection = collectionResolver.methodsFor(property.type());

            switch (collection.emptyMutableFactory()) {
                case ConstructorEmptyFactory(ReferenceType implementationErasedType) ->
                        builder.addCode("this.$L = new $T<>();", property.name(), implementationErasedType);
                case MethodEmptyFactory(ReferenceType containingErasedType, Name methodname) ->
                        builder.addCode("this.$L = $T.$L();", property.name(), containingErasedType, methodname);
            }
        }
    }

    @RequiredArgsConstructor
    private final class ObjectCopyConstructorGenerator implements GenerationPropertyVisitor {
        private final MethodSpec.Builder builder;

        @Override
        public void visitObject(ObjectProperty property) {
            builder.addCode("this.$L = impl.$L();\n", property.name(), property.name());
        }

        @Override
        public void visitPrimitive(PrimitiveProperty property) {
            builder.addCode("this.$L = impl.$L();\n", property.name(), property.name());
        }

        @Override
        public void visitObjectCollection(ObjectCollectionProperty property) {
            var collection = collectionResolver.methodsFor(property.type());

            switch (collection.copyMutableFactory()) {
                case ConstructorCopyFactory(ReferenceType implementationErasedType) ->
                        builder.addCode("this.$L = new $T<>(impl.$L());\n", property.name(), implementationErasedType, property.name());
                case MethodCopyFactory(ReferenceType containingErasedType, Name methodname) ->
                        builder.addCode("this.$L = $T.$L(impl.$L);\n", property.name(), containingErasedType, methodname, property.name());
            }
        }
    }

    @RequiredArgsConstructor
    private final class SetterMethodGenerator implements GenerationPropertyVisitor {
        private final TypeSpec.Builder builder;
        private final TypeName builderType;

        @Override
        public void visitObject(ObjectProperty property) {
            builder.addMethod(
                    methodBuilder(property.name())
                            .addAnnotations(annotations.nonNull())
                            .addAnnotation(Override.class)
                            .addModifiers(Modifier.PUBLIC)
                            .addParameter(
                                    ParameterSpec.builder(property.typename(), property.name())
                                            .addAnnotations(annotations.nonNull())
                                            .build()
                            )
                            .addCode(requireNonNull(property))
                            .addCode("this.$L = $L;\n", property.name(), property.name())
                            .addCode("return this;")
                            .returns(builderType)
                            .build()
            );
        }

        @Override
        public void visitPrimitive(PrimitiveProperty property) {
            builder.addMethod(
                    methodBuilder(property.name())
                            .addAnnotations(annotations.nonNull())
                            .addAnnotation(Override.class)
                            .addModifiers(Modifier.PUBLIC)
                            .addParameter(property.typename(), property.name())
                            .addCode("this.$L = $L;\n", property.name(), property.name())
                            .addCode("return this;")
                            .returns(builderType)
                            .build()
            );
        }

        @Override
        public void visitObjectCollection(ObjectCollectionProperty property) {
            builder.addMethod(
                    methodBuilder(property.name())
                            .addAnnotations(annotations.nonNull())
                            .addAnnotation(Override.class)
                            .addModifiers(Modifier.PUBLIC)
                            .addParameter(
                                    ParameterSpec.builder(property.typename(), property.name())
                                            .addAnnotations(annotations.nonNull())
                                            .build()
                            )
                            .addCode(requireNonNull(property))
                            .addCode("this.$L.clear();\n", property.name())
                            .addCode("this.$L.addAll($L);\n", property.name(), property.name())
                            .addCode("return this;")
                            .returns(builderType)
                            .build()
            );

            builder.addMethod(
                    methodBuilder("add" + capitalize(property.name()))
                            .addAnnotations(annotations.nonNull())
                            .addAnnotation(Override.class)
                            .addModifiers(Modifier.PUBLIC)
                            .addParameter(
                                    ParameterSpec.builder(property.typename(), property.name())
                                            .addAnnotations(annotations.nonNull())
                                            .build()
                            )
                            .addCode(requireNonNull(property))
                            .addCode("this.$L.addAll($L);\n", property.name(), property.name())
                            .addCode("return this;")
                            .returns(builderType)
                            .build()
            );
        }
    }

    @RequiredArgsConstructor
    private class GetterMethodGenerator implements GenerationPropertyVisitor {
        private final TypeSpec.Builder builder;

        @Override
        public void visitObject(ObjectProperty property) {
            builder.addMethod(
                    methodBuilder(property.name())
                            .addAnnotations(annotations.nullable())
                            .addAnnotation(Override.class)
                            .addModifiers(Modifier.PUBLIC)
                            .addCode("return $L;", property.name())
                            .returns(property.typename())
                            .build()
            );
        }

        @Override
        public void visitPrimitive(PrimitiveProperty property) {
            builder.addMethod(
                    methodBuilder(property.name())
                            .addAnnotations(annotations.nullable())
                            .addAnnotation(Override.class)
                            .addModifiers(Modifier.PUBLIC)
                            .addCode("return $L;", property.name())
                            .returns(property.typename().box())
                            .build()
            );
        }

        @Override
        public void visitObjectCollection(ObjectCollectionProperty property) {
            builder.addMethod(
                    methodBuilder(property.name())
                            .addAnnotations(annotations.nonNull())
                            .addAnnotation(Override.class)
                            .addModifiers(Modifier.PUBLIC)
                            .addCode("return $L;", property.name())
                            .returns(property.typename())
                            .build()
            );
        }
    }

    @RequiredArgsConstructor
    private final class BuildMethodCodeGeneratorGenerator implements GenerationPropertyVisitor {
        private final List<CodeBlock> blocks;

        @Override
        public void visitObject(ObjectProperty property) {
            blocks.add(CodeBlock.of("$T.requireNonNull(this.$N, $S)", Objects.class, property.name(), property.name()));
        }

        @Override
        public void visitPrimitive(PrimitiveProperty property) {
            blocks.add(CodeBlock.of("this.$N", property.name()));
        }

        @Override
        public void visitObjectCollection(ObjectCollectionProperty property) {
            var collection = collectionResolver.methodsFor(property.type());

            switch (collection.copyImmutableFactory()) {
                case ConstructorCopyFactory(ReferenceType implementationErasedType) ->
                        blocks.add(CodeBlock.of("new $T<>(this.$L)", implementationErasedType, property.name()));
                case MethodCopyFactory(ReferenceType containingErasedType, Name methodname) ->
                        blocks.add(CodeBlock.of("$T.$L(this.$L)", containingErasedType, methodname, property.name()));
            }
        }
    }
}
