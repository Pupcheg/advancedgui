package me.supcheg.advancedgui.code.processor.step.builder.constructor;

import com.palantir.javapoet.CodeBlock;
import com.palantir.javapoet.ParameterSpec;
import java.util.Objects;

public class BuilderImplParameterInitializer {
   protected final ParameterSpec parameter;

   public CodeBlock newValueInitializer() {
      return CodeBlock.of("", new Object[0]);
   }

   public CodeBlock copyingInitializer(String from) {
      return CodeBlock.of("this.$L = $L.$L;\n", new Object[]{this.parameter.name(), from, this.parameter.name()});
   }

   public CodeBlock finalizer() {
      return CodeBlock.of("$T.requireNonNull($L, $S)", new Object[]{Objects.class, this.parameter.name(), this.parameter.name()});
   }

   public BuilderImplParameterInitializer(ParameterSpec parameter) {
      this.parameter = parameter;
   }
}
