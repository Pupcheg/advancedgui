package me.supcheg.advancedgui.code.processor;

import me.supcheg.advancedgui.code.processor.CollectionMethods.ConstructorCopyFactory;
import me.supcheg.advancedgui.code.processor.CollectionMethods.ConstructorEmptyFactory;
import me.supcheg.advancedgui.code.processor.CollectionMethods.MethodEmptyFactory;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ReferenceType;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.util.function.Predicate.not;
import static me.supcheg.advancedgui.code.processor.CollectionMethods.MethodCopyFactory;
import static me.supcheg.advancedgui.code.processor.CollectionMethods.SingletonFactory;

class CollectionMethodsResolver {
    private final Types types;
    private final Elements elements;

    private final Map<ReferenceType, CollectionMethods> methodsByType = new LinkedHashMap<>();

    CollectionMethodsResolver(Types types, Elements elements) {
        this.types = types;
        this.elements = elements;

        sequencedSortedSet().ifPresent(this::add);
        add(builtinCollection(List.class, ArrayList.class));
        add(builtinCollection(Set.class, HashSet.class));
    }

    private void add(CollectionMethods collectionMethods) {
        methodsByType.put(collectionMethods.interfaceType(), collectionMethods);
    }

    private Optional<CollectionMethods> sequencedSortedSet() {
        var collectionTypeElement = elements.getTypeElement("me.supcheg.advancedgui.api.sequence.collection.SequencedSortedSet");

        if (collectionTypeElement == null) {
            return Optional.empty();
        }

        var collectionType = (ReferenceType) types.erasure(collectionTypeElement.asType());

        var utilsTypeElement = elements.getTypeElement("me.supcheg.advancedgui.api.sequence.collection.SequencedSortedSets");
        var utilsType = (ReferenceType) types.erasure(utilsTypeElement.asType());

        return Optional.of(new CollectionMethods(
                collectionType,
                new MethodEmptyFactory(utilsType, findMethod(utilsTypeElement, "create", 0)),
                new SingletonFactory(utilsType, findMethod(utilsTypeElement, "of", 1)),
                new MethodCopyFactory(utilsType, findMethod(utilsTypeElement, "createCopy", 1)),
                new MethodCopyFactory(utilsType, findMethod(utilsTypeElement, "copyOf", 1))
        ));
    }

    private <T extends Collection<?>> CollectionMethods builtinCollection(Class<T> interfaceClazz, Class<? extends T> implementationClazz) {
        var interfaceTypeElement = elements.getTypeElement(interfaceClazz.getName());
        var interfaceType = (ReferenceType) types.erasure(interfaceTypeElement.asType());
        var implementationType = (ReferenceType) types.erasure(elements.getTypeElement(implementationClazz.getName()).asType());
        return new CollectionMethods(
                interfaceType,
                new ConstructorEmptyFactory(implementationType),
                new SingletonFactory(interfaceType, findMethod(interfaceTypeElement, "of", 1)),
                new ConstructorCopyFactory(implementationType),
                new MethodCopyFactory(interfaceType, findMethod(interfaceTypeElement, "copyOf", 1))
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
                .orElseThrow(() -> new IllegalStateException("Collection methods not found for " + collectionType + " in " + methodsByType.keySet()));
    }
}
