package me.supcheg.advancedgui.code.processor.strategy;

import com.palantir.javapoet.TypeSpec;
import javax.lang.model.element.Element;
import org.jetbrains.annotations.NotNull;

public interface AdventureLikeGenerationStrategy {
   @NotNull
   TypeSpec generate(@NotNull Element var1);
}
