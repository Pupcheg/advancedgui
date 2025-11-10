package me.supcheg.advancedgui.code.processor.step;

import com.palantir.javapoet.TypeSpec.Builder;

public interface GenerationStep {
    void generate(Builder target);
}
