package me.supcheg.advancedgui.code.processor;

import com.palantir.javapoet.CodeBlock;

import java.util.Objects;

abstract class GenerationPropertyVisitor extends PropertyVisitor {
    CodeBlock requireNonNull(Property property) {
        return CodeBlock.of("$T.requireNonNull($N, $S);\n", Objects.class, property.name(), property.name());
    }
}
