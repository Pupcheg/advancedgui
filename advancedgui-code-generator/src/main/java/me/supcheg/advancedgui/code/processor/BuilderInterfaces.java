package me.supcheg.advancedgui.code.processor;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.ParameterizedTypeName;
import com.palantir.javapoet.TypeName;

import javax.lang.model.element.TypeElement;

record BuilderInterfaces(
        TypeElement buildableType,
        TypeElement builderType
) {

    ParameterizedTypeName buildableTypename(TypeName selfTypename) {
        return ParameterizedTypeName.get(ClassName.get(buildableType), selfTypename);
    }

    ParameterizedTypeName builderTypename(TypeName buildableTypename) {
        return ParameterizedTypeName.get(ClassName.get(builderType), buildableTypename);
    }
}
