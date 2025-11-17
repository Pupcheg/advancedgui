package me.supcheg.advancedgui.code.processor.collection;

import javax.lang.model.element.Name;
import javax.lang.model.type.ReferenceType;

public record CollectionMethods(
        ReferenceType erasedType,
        Name emptyFactory,
        Name singletonFactory,
        Name varargsFactory
) {
}
