package me.supcheg.advancedgui.code.processor.step;

import com.palantir.javapoet.MethodSpec;
import com.palantir.javapoet.ParameterSpec;
import com.palantir.javapoet.TypeSpec.Builder;
import org.jetbrains.annotations.NotNull;

public class ConstructorGenerationStep implements GenerationStep {
   private final Iterable<ParameterSpec> parameters;

   public void generate(@NotNull Builder target) {
      target.recordConstructor(MethodSpec.constructorBuilder().addParameters(this.parameters).build());
   }

   public ConstructorGenerationStep(Iterable<ParameterSpec> parameters) {
      this.parameters = parameters;
   }
}
