package me.supcheg.advancedgui.code.processor;

import com.palantir.javapoet.TypeName;
import com.palantir.javapoet.TypeSpec;

public record GenerationResult(
        TypeSpec spec,
        TypeName name
) {
}
