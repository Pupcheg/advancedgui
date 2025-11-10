package me.supcheg.advancedgui.code.processor.strategy;

import com.palantir.javapoet.TypeSpec;

import javax.lang.model.element.Element;

public interface AdventureLikeGenerationStrategy {
    TypeSpec generate(Element element);
}
