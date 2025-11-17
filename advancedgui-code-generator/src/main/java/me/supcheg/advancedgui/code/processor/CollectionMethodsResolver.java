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
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.function.Predicate.not;
import static me.supcheg.advancedgui.code.processor.CollectionMethods.MethodCopyFactory;
import static me.supcheg.advancedgui.code.processor.CollectionMethods.SingletonFactory;

class CollectionMethodsResolver {
    private final Types types;

    private final Map<ReferenceType, CollectionMethods> methodsByType;

    CollectionMethodsResolver(Types types, Elements elements) {
        this.types = types;

        var sequencedSortedSetElement = elements.getTypeElement("me.supcheg.advancedgui.api.sequence.collection.SequencedSortedSet");
        var sequencedSortedSetsElement = elements.getTypeElement("me.supcheg.advancedgui.api.sequence.collection.SequencedSortedSets");
        var sequencedSortedSetType = (ReferenceType) types.erasure(sequencedSortedSetElement.asType());
        var sequencedSortedSetsType = (ReferenceType) types.erasure(sequencedSortedSetsElement.asType());
        var sequencedSortedSetMethods = new CollectionMethods(
                new MethodEmptyFactory(sequencedSortedSetsType, findMethod(sequencedSortedSetsElement, "create", 0)),
                new SingletonFactory(sequencedSortedSetsType, findMethod(sequencedSortedSetsElement, "of", 1)),
                new MethodCopyFactory(sequencedSortedSetsType, findMethod(sequencedSortedSetsElement, "createCopy", 1)),
                new MethodCopyFactory(sequencedSortedSetsType, findMethod(sequencedSortedSetsElement, "copyOf", 1))
        );

        var listTypeElement = elements.getTypeElement(List.class.getName());
        var listType = (ReferenceType) types.erasure(listTypeElement.asType());
        var arrayListType = (ReferenceType) types.erasure(elements.getTypeElement(ArrayList.class.getName()).asType());
        var listMethods = new CollectionMethods(
                new ConstructorEmptyFactory(arrayListType),
                new SingletonFactory(listType, findMethod(listTypeElement, "of", 1)),
                new ConstructorCopyFactory(arrayListType),
                new MethodCopyFactory(listType, findMethod(listTypeElement, "copyOf", 1))
        );

        var setTypeElement = elements.getTypeElement(Set.class.getName());
        var setType = (ReferenceType) types.erasure(setTypeElement.asType());
        var hashSetType = (ReferenceType) types.erasure(elements.getTypeElement(HashSet.class.getName()).asType());
        var setMethods = new CollectionMethods(
                new ConstructorEmptyFactory(hashSetType),
                new SingletonFactory(setType, findMethod(setTypeElement, "of", 1)),
                new ConstructorCopyFactory(hashSetType),
                new MethodCopyFactory(setType, findMethod(setTypeElement, "copyOf", 1))
        );

        var methodsByType = new LinkedHashMap<ReferenceType, CollectionMethods>();
        methodsByType.put(sequencedSortedSetType, sequencedSortedSetMethods);
        methodsByType.put(listType, listMethods);
        methodsByType.put(setType, setMethods);
        this.methodsByType = Collections.unmodifiableMap(methodsByType);
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
                .orElseThrow(() -> new IllegalStateException("Collection methods not found for " + collectionType));
    }
}
