package me.supcheg.advancedgui.code.processor;

import me.supcheg.advancedgui.code.processor.CollectionMethods.ConstructorCopyFactory;
import me.supcheg.advancedgui.code.processor.CollectionMethods.ConstructorEmptyFactory;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ReferenceType;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.function.Predicate.not;

class CollectionMethodsResolver {
    private final Types types;

    private final Map<ReferenceType, CollectionMethods> methodsByType;

    CollectionMethodsResolver(Types types, Elements elements) {
        this.types = types;

        var listType = elements.getTypeElement(List.class.getName());
        this.methodsByType = Map.of(
                (ReferenceType) listType.asType(),
                new CollectionMethods(
                        (ReferenceType) types.erasure(listType.asType()),
                        new ConstructorEmptyFactory(
                                (ReferenceType) types.erasure(elements.getTypeElement(ArrayList.class.getName()).asType())
                        ),
                        findMethod(listType, "of", 1),
                        new ConstructorCopyFactory(
                                (ReferenceType) types.erasure(elements.getTypeElement(ArrayList.class.getName()).asType())
                        ),
                        new CollectionMethods.MethodCopyFactory(
                                (ReferenceType) types.erasure(listType.asType()),
                                findMethod(listType, "copyOf", 1)
                        )
                )
        );
    }

    private Name findMethod(TypeElement type, String methodName, int argsAmount) {
        return ElementFilter.methodsIn(type.getEnclosedElements())
                .stream()
                .filter(not(ExecutableElement::isVarArgs))
                .filter(method -> method.getSimpleName().contentEquals(methodName))
                .filter(method -> method.getParameters().size() == argsAmount)
                .findFirst()
                .orElseThrow()
                .getSimpleName();
    }

    public CollectionMethods methodsFor(ReferenceType collectionType) {
        return methodsByType.entrySet().stream()
                .filter(entry -> types.isAssignable(types.erasure(collectionType), entry.getKey()))
                .findFirst()
                .map(Map.Entry::getValue)
                .orElseThrow();
    }
}
