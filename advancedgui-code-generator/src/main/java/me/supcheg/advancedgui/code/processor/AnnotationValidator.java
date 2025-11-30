package me.supcheg.advancedgui.code.processor;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.ParameterizedTypeName;
import com.palantir.javapoet.TypeName;
import com.palantir.javapoet.TypeVariableName;
import lombok.RequiredArgsConstructor;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static javax.lang.model.util.ElementFilter.methodsIn;
import static me.supcheg.advancedgui.code.processor.StringUtil.decapitalize;
import static me.supcheg.advancedgui.code.processor.TypeNames.isSameType;
import static me.supcheg.advancedgui.code.processor.TypeNames.rename;
import static me.supcheg.advancedgui.code.processor.TypeNames.simpleName;
import static me.supcheg.advancedgui.code.processor.TypeNames.withoutPackage;

@RequiredArgsConstructor
class AnnotationValidator {
    private final Messager messager;
    private final NamesResolver namesResolver;

    void validate(TypeElement typeElement) {
        var names = namesResolver.namesForObject(typeElement.asType());

        var objectTypename = names.object();
        var builderTypename = names.builder();
        var builderConsumerTypename = ParameterizedTypeName.get(ClassName.get(Consumer.class), builderTypename);

        var methodname = decapitalize(simpleName(names.object()));

        assertHasConsumerFactory(typeElement, methodname, objectTypename, builderConsumerTypename);
        assertHasFactory(typeElement, methodname, builderTypename);
    }

    private void assertHasConsumerFactory(TypeElement typeElement, String name, TypeName returnType, TypeName argumentType) {
        if (
                methodsIn(typeElement.getEnclosedElements()).stream()
                        .filter(method -> method.getModifiers().contains(Modifier.PUBLIC))
                        .filter(method -> method.getModifiers().contains(Modifier.STATIC))
                        .filter(method -> method.getParameters().size() == 1)
                        .filter(method -> isSameType(method.getParameters().getFirst().asType(), argumentType))
                        .filter(method -> isSameType(method.getReturnType(), returnType))
                        .findAny()
                        .isEmpty()
        ) {
            messager.printError(
                    "'public static %s %s(%s)' method not present in %s.%n%s"
                            .formatted(returnType, name, argumentType, typeElement, consumerFactoryAdvice(returnType, name)),
                    typeElement
            );
        }
    }

    private String consumerFactoryAdvice(TypeName returnType, String factoryName) {
        return """
                static %s %s %s(Consumer<%s> consumer) {
                    return Buildable.configureAndBuild(%3$s(), consumer);
                }
                """.formatted(genericTypes(returnType), withoutPackage(returnType),factoryName, withoutPackage(rename(returnType, name -> name + "Builder")));
    }

    private void assertHasFactory(TypeElement typeElement, String name, TypeName returnType) {
        if (
                methodsIn(typeElement.getEnclosedElements()).stream()
                        .filter(method -> method.getModifiers().contains(Modifier.PUBLIC))
                        .filter(method -> method.getModifiers().contains(Modifier.STATIC))
                        .filter(method -> method.getParameters().isEmpty())
                        .filter(method -> isSameType(method.getReturnType(), returnType))
                        .findAny()
                        .isEmpty()
        ) {
            messager.printError(
                    "'public static %s %s()' method not present in %s.%n%s"
                            .formatted(returnType, name, typeElement, factoryAdvice(returnType, name)),
                    typeElement
            );
        }
    }

    private String factoryAdvice(TypeName returnType, String factoryName) {
        return """
                static %s %s %s() {
                    return new %s();
                }
                """.formatted(genericTypes(returnType), withoutPackage(returnType), factoryName, withoutPackage(rename(returnType, name -> name + "Impl")));
    }

    private String genericTypes(TypeName type) {
        if (!(type instanceof ParameterizedTypeName parametrized)) {
            return "";
        }

        return parametrized.typeArguments().stream()
                .filter(TypeVariableName.class::isInstance)
                .map(TypeVariableName.class::cast)
                .map(String::valueOf)
                .collect(Collectors.joining(", ", "<", ">"));
    }
}
