package me.supcheg.advancedgui.code.processor;

import com.palantir.javapoet.TypeName;

public record Names(
        TypeName object,
        TypeName objectImpl,
        TypeName builder,
        TypeName builderImpl
) {
}
