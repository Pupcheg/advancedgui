package me.supcheg.advancedgui.code.processor.step.builder.constructor;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.CodeBlock;
import com.palantir.javapoet.MethodSpec;
import com.palantir.javapoet.TypeSpec.Builder;
import java.util.Iterator;
import java.util.List;
import javax.lang.model.element.Modifier;
import me.supcheg.advancedgui.code.processor.step.GenerationStep;
import org.jetbrains.annotations.NotNull;

public class BuilderImplTargetConstructorGenerationStep implements GenerationStep {
   public static final String PARAMETER_NAME = "impl";
   private final ClassName implClassName;
   private final List<BuilderImplParameterInitializer> initializers;

   public void generate(@NotNull Builder target) {
      target.addMethod(MethodSpec.constructorBuilder().addParameter(this.implClassName, "impl", new Modifier[0]).addCode(this.code()).build());
   }

   private CodeBlock code() {
      com.palantir.javapoet.CodeBlock.Builder builder = CodeBlock.builder();
      Iterator var2 = this.initializers.iterator();

      while(var2.hasNext()) {
         BuilderImplParameterInitializer initializer = (BuilderImplParameterInitializer)var2.next();
         builder.add(initializer.copyingInitializer("impl"));
      }

      return builder.build();
   }

   public BuilderImplTargetConstructorGenerationStep(ClassName implClassName, List<BuilderImplParameterInitializer> initializers) {
      this.implClassName = implClassName;
      this.initializers = initializers;
   }
}
