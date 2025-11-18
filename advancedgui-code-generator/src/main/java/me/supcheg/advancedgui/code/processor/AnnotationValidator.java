package me.supcheg.advancedgui.code.processor;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.ParameterizedTypeName;
import com.palantir.javapoet.TypeName;
import lombok.RequiredArgsConstructor;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.util.function.Consumer;

import static javax.lang.model.util.ElementFilter.methodsIn;
import static me.supcheg.advancedgui.code.processor.StringUtil.decapitalize;
import static me.supcheg.advancedgui.code.processor.TypeNames.isSameType;
import static me.supcheg.advancedgui.code.processor.TypeNames.simpleName;

@RequiredArgsConstructor
class AnnotationValidator {
    private final Messager messager;
    private final NamesResolver namesResolver;

    void validate(TypeElement typeElement) {
        var names = namesResolver.namesForObject(typeElement.asType());

        var objectTypename = names.object();
        var builderTypename = names.builder();
        var builderConsumerTypename = ParameterizedTypeName.get(ClassName.get(Consumer.class), builderTypename);

        assertHasPublicStaticWithArgument(typeElement, decapitalize(simpleName(names.object())), objectTypename, builderConsumerTypename);
        assertHasPublicStaticWithArgument(typeElement, decapitalize(simpleName(names.object())), builderTypename);
    }

    private void assertHasPublicStaticWithArgument(TypeElement typeElement, String name, TypeName returnType, TypeName argumentType) {
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
            messager.printError("'public static %s %s(%s)' method not present in %s".formatted(returnType, name, argumentType, typeElement), typeElement);
        }
    }

    private void assertHasPublicStaticWithArgument(TypeElement typeElement, String name, TypeName returnType) {
        if (
                methodsIn(typeElement.getEnclosedElements()).stream()
                        .filter(method -> method.getModifiers().contains(Modifier.PUBLIC))
                        .filter(method -> method.getModifiers().contains(Modifier.STATIC))
                        .filter(method -> isSameType(method.getReturnType(), returnType))
                        .findAny()
                        .isEmpty()
        ) {
            messager.printError("'public static %s %s()' method not present in %s".formatted(returnType, name, typeElement), typeElement);
        }
    }
}
