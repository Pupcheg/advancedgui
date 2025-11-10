package me.supcheg.advancedgui.code.processor.step.builder.param;

import com.palantir.javapoet.AnnotationSpec;
import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.CodeBlock;
import com.palantir.javapoet.ParameterSpec;
import java.util.List;
import me.supcheg.advancedgui.code.processor.util.AnnotationHelper;

public class BuilderImplPrimitiveParameterGenerationStep extends BuilderImplParameterGenerationStep {
   public BuilderImplPrimitiveParameterGenerationStep(ParameterSpec parameter, AnnotationHelper annotationHelper, ClassName builderImplClass) {
      super(parameter, annotationHelper, builderImplClass);
   }

   protected CodeBlock setterCode() {
      String name = this.parameter.name();
      return CodeBlock.builder().add("this.$L = $L;\n", new Object[]{name, name}).add("return this;", new Object[0]).build();
   }

   protected List<AnnotationSpec> setterParameterAnnotations() {
      return this.annotationHelper.removeNullabilityAnnotations(this.parameter.annotations());
   }
}
