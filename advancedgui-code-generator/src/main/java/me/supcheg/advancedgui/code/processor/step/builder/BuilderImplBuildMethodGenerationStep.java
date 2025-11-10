package me.supcheg.advancedgui.code.processor.step.builder;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.CodeBlock;
import com.palantir.javapoet.MethodSpec;
import com.palantir.javapoet.TypeSpec.Builder;
import java.util.Iterator;
import java.util.List;
import javax.lang.model.element.Modifier;
import me.supcheg.advancedgui.code.processor.step.GenerationStep;
import me.supcheg.advancedgui.code.processor.step.builder.constructor.BuilderImplParameterInitializer;
import org.jetbrains.annotations.NotNull;

public class BuilderImplBuildMethodGenerationStep implements GenerationStep {
   private final ClassName buildableImplClassName;
   private final List<BuilderImplParameterInitializer> parameters;

   public void generate(@NotNull Builder target) {
      target.addMethod(MethodSpec.methodBuilder("build").addModifiers(new Modifier[]{Modifier.PUBLIC}).addAnnotation(NotNull.class).addAnnotation(Override.class).returns(this.buildableImplClassName).addCode(this.code()).build());
   }

   private CodeBlock code() {
      com.palantir.javapoet.CodeBlock.Builder builder = CodeBlock.builder();
      builder.add("return new $T(\n", new Object[]{this.buildableImplClassName});
      builder.indent();

      for(Iterator it = this.parameters.iterator(); it.hasNext(); builder.add("\n", new Object[0])) {
         BuilderImplParameterInitializer param = (BuilderImplParameterInitializer)it.next();
         builder.add(param.finalizer());
         if (it.hasNext()) {
            builder.add(",", new Object[0]);
         }
      }

      builder.unindent();
      builder.add(");\n", new Object[0]);
      return builder.build();
   }

   public BuilderImplBuildMethodGenerationStep(ClassName buildableImplClassName, List<BuilderImplParameterInitializer> parameters) {
      this.buildableImplClassName = buildableImplClassName;
      this.parameters = parameters;
   }
}
