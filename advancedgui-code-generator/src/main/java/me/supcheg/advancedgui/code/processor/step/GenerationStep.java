package me.supcheg.advancedgui.code.processor.step;

import com.palantir.javapoet.TypeSpec.Builder;
import org.jetbrains.annotations.NotNull;

public interface GenerationStep {
   void generate(@NotNull Builder var1);
}
