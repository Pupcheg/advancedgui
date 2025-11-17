package me.supcheg.advancedgui.code.processor;

import com.palantir.javapoet.TypeSpec;

import javax.lang.model.element.TypeElement;
import java.util.List;

public interface GenerationStrategy {
    List<TypeSpec> generate(TypeElement typeElement);
}
