package me.supcheg.advancedgui.code.processor;

import com.palantir.javapoet.CodeBlock;
import me.supcheg.advancedgui.code.processor.property.Property;
import me.supcheg.advancedgui.code.processor.property.PropertyVisitor;

import java.util.Objects;

public interface GenerationPropertyVisitor extends PropertyVisitor {
    default CodeBlock requireNonNull(Property property) {
        return CodeBlock.of("$T.requireNonNull($N, $S);\n", Objects.class, property.name(), property.name());
    }
}
