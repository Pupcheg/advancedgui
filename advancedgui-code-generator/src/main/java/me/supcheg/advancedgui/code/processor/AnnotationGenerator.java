package me.supcheg.advancedgui.code.processor;

import com.palantir.javapoet.TypeSpec;

import javax.lang.model.element.TypeElement;
import java.util.List;

abstract class AnnotationGenerator {
    abstract List<TypeSpec> generate(TypeElement typeElement);
}
