package me.supcheg.advancedgui.code.processor.collection;

import javax.lang.model.element.Name;
import javax.lang.model.type.ReferenceType;

public record CollectionMethods(
        ReferenceType erasedType,
        EmptyFactory emptyMutableFactory,
        Name singletonImmutableFactory,
        CopyFactory copyMutableFactory,
        CopyFactory copyImmutableFactory
) {
    public sealed interface EmptyFactory {}
    public record ConstructorEmptyFactory(ReferenceType implementationErasedType) implements EmptyFactory {}
    public record MethodEmptyFactory(ReferenceType containingErasedType, Name methodname) implements EmptyFactory {}

    public sealed interface CopyFactory {}
    public record ConstructorCopyFactory(ReferenceType implementationErasedType) implements CopyFactory {}
    public record MethodCopyFactory(ReferenceType containingErasedType, Name methodname) implements CopyFactory {}
}
