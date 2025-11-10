package me.supcheg.advancedgui.code.processor.step;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.CodeBlock;
import com.palantir.javapoet.MethodSpec;
import com.palantir.javapoet.TypeSpec.Builder;
import javax.lang.model.element.Modifier;
import org.jetbrains.annotations.NotNull;

public class ToBuilderImplGenerationStep implements GenerationStep {
   private final ClassName builderImplClassName;

   public void generate(@NotNull Builder target) {
      target.addMethod(MethodSpec.methodBuilder("toBuilder").addModifiers(new Modifier[]{Modifier.PUBLIC}).addAnnotation(NotNull.class).addAnnotation(Override.class).returns(this.builderImplClassName).addCode(this.code()).build());
   }

   private CodeBlock code() {
      return CodeBlock.of("return new $T(this);\n", new Object[]{this.builderImplClassName});
   }

   public ToBuilderImplGenerationStep(ClassName builderImplClassName) {
      this.builderImplClassName = builderImplClassName;
   }
}
