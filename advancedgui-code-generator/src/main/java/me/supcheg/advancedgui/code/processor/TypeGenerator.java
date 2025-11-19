package me.supcheg.advancedgui.code.processor;

import com.palantir.javapoet.TypeSpec;

import java.util.List;

abstract class TypeGenerator {
    abstract TypeSpec generate(Names names, List<? extends Property> properties);
}
