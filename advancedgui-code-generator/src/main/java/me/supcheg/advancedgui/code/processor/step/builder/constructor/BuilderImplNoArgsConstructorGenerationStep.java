package me.supcheg.advancedgui.code.processor.step.builder.constructor;

import com.palantir.javapoet.CodeBlock;
import com.palantir.javapoet.MethodSpec;
import com.palantir.javapoet.TypeSpec.Builder;
import java.util.Iterator;
import java.util.List;
import me.supcheg.advancedgui.code.processor.step.GenerationStep;
import org.jetbrains.annotations.NotNull;

public class BuilderImplNoArgsConstructorGenerationStep implements GenerationStep {
   private final List<BuilderImplParameterInitializer> initializers;

   public void generate(@NotNull Builder target) {
      target.addMethod(MethodSpec.constructorBuilder().addCode(this.codeBlock()).build());
   }

   @NotNull
   private CodeBlock codeBlock() {
      com.palantir.javapoet.CodeBlock.Builder builder = CodeBlock.builder();
      Iterator var2 = this.initializers.iterator();

      while(var2.hasNext()) {
         BuilderImplParameterInitializer initializer = (BuilderImplParameterInitializer)var2.next();
         builder.add(initializer.newValueInitializer());
      }

      return builder.build();
   }

   public BuilderImplNoArgsConstructorGenerationStep(List<BuilderImplParameterInitializer> initializers) {
      this.initializers = initializers;
   }
}
