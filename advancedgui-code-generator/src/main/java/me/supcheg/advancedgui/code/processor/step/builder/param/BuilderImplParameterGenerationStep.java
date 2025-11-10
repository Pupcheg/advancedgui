package me.supcheg.advancedgui.code.processor.step.builder.param;

import com.palantir.javapoet.AnnotationSpec;
import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.CodeBlock;
import com.palantir.javapoet.FieldSpec;
import com.palantir.javapoet.MethodSpec;
import com.palantir.javapoet.ParameterSpec;
import com.palantir.javapoet.TypeSpec.Builder;
import java.util.List;
import java.util.Objects;
import javax.lang.model.element.Modifier;
import me.supcheg.advancedgui.code.processor.step.GenerationStep;
import me.supcheg.advancedgui.code.processor.util.AnnotationHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BuilderImplParameterGenerationStep implements GenerationStep {
   protected final ParameterSpec parameter;
   protected final AnnotationHelper annotationHelper;
   protected final ClassName builderImplClass;

   public void generate(@NotNull Builder target) {
      target.addField(this.fieldSpec().build());
      target.addMethod(this.setter().build());
      target.addMethod(this.getter().build());
   }

   protected com.palantir.javapoet.FieldSpec.Builder fieldSpec() {
      return FieldSpec.builder(this.parameter.type().box(), this.parameter.name(), new Modifier[0]).addModifiers(new Modifier[]{Modifier.PRIVATE}).addAnnotations(this.fieldAnnotations());
   }

   protected List<AnnotationSpec> fieldAnnotations() {
      return this.annotationHelper.putNullabilityAnnotation(this.parameter.annotations(), NotNull.class);
   }

   protected com.palantir.javapoet.MethodSpec.Builder setter() {
      return MethodSpec.methodBuilder(this.parameter.name()).addAnnotation(NotNull.class).addAnnotation(Override.class).addModifiers(new Modifier[]{Modifier.PUBLIC}).addParameter(ParameterSpec.builder(this.parameter.type(), this.parameter.name(), new Modifier[0]).addAnnotations(this.setterParameterAnnotations()).build()).returns(this.builderImplClass).addCode(this.setterCode());
   }

   protected List<AnnotationSpec> setterParameterAnnotations() {
      return this.annotationHelper.putNullabilityAnnotation(this.parameter.annotations(), NotNull.class);
   }

   protected CodeBlock setterCode() {
      String name = this.parameter.name();
      return CodeBlock.builder().add("$T.requireNonNull($L, $S);\n", new Object[]{Objects.class, name, name}).add("this.$L = $L;\n", new Object[]{name, name}).add("return this;\n", new Object[0]).build();
   }

   protected com.palantir.javapoet.MethodSpec.Builder getter() {
      return MethodSpec.methodBuilder(this.parameter.name()).addAnnotations(this.getterAnnotations()).addModifiers(new Modifier[]{Modifier.PUBLIC}).returns(this.parameter.type().box()).addCode(this.getterCode());
   }

   protected List<AnnotationSpec> getterAnnotations() {
      return this.annotationHelper.addIfNotPresent(this.annotationHelper.putNullabilityAnnotation(this.parameter.annotations(), Nullable.class), Override.class);
   }

   protected CodeBlock getterCode() {
      return CodeBlock.of("return this.$L;\n", new Object[]{this.parameter.name()});
   }

   public BuilderImplParameterGenerationStep(ParameterSpec parameter, AnnotationHelper annotationHelper, ClassName builderImplClass) {
      this.parameter = parameter;
      this.annotationHelper = annotationHelper;
      this.builderImplClass = builderImplClass;
   }
}
