package me.supcheg.advancedgui.code.processor;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.ParameterizedTypeName;
import com.palantir.javapoet.TypeName;

import javax.lang.model.element.TypeElement;

record BuilderInterfaces(
        ClassName buildableTypename,
        ClassName builderTypename
) {
    BuilderInterfaces(
            TypeElement buildableType,
            TypeElement builderType
    ) {
        this(
                ClassName.get(buildableType),
                ClassName.get(builderType)
        );
    }

    ParameterizedTypeName buildableTypename(TypeName selfTypename) {
        return ParameterizedTypeName.get(buildableTypename, selfTypename);
    }

    ParameterizedTypeName builderTypename(TypeName buildableTypename) {
        return ParameterizedTypeName.get(builderTypename, buildableTypename);
    }
}
