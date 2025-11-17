package me.supcheg.advancedgui.code.processor;

import javax.lang.model.element.Name;
import javax.lang.model.type.ReferenceType;

record CollectionMethods(
        ReferenceType erasedType,
        EmptyFactory emptyMutableFactory,
        Name singletonImmutableFactory,
        CopyFactory copyMutableFactory,
        CopyFactory copyImmutableFactory
) {
    sealed interface EmptyFactory {}
    record ConstructorEmptyFactory(ReferenceType implementationErasedType) implements EmptyFactory {}
    record MethodEmptyFactory(ReferenceType containingErasedType, Name methodname) implements EmptyFactory {}

    sealed interface CopyFactory {}
    record ConstructorCopyFactory(ReferenceType implementationErasedType) implements CopyFactory {}
    record MethodCopyFactory(ReferenceType containingErasedType, Name methodname) implements CopyFactory {}
}
