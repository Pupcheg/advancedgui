package me.supcheg.advancedgui.code.processor.step.builder;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.ParameterSpec;
import com.palantir.javapoet.TypeSpec;
import com.palantir.javapoet.TypeSpec.Builder;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import me.supcheg.advancedgui.code.processor.step.GenerationStep;
import me.supcheg.advancedgui.code.processor.step.ToBuilderImplGenerationStep;
import me.supcheg.advancedgui.code.processor.step.builder.constructor.BuilderImplCollectionParameterInitializer;
import me.supcheg.advancedgui.code.processor.step.builder.constructor.BuilderImplNoArgsConstructorGenerationStep;
import me.supcheg.advancedgui.code.processor.step.builder.constructor.BuilderImplParameterInitializer;
import me.supcheg.advancedgui.code.processor.step.builder.constructor.BuilderImplTargetConstructorGenerationStep;
import me.supcheg.advancedgui.code.processor.step.builder.param.BuilderImplCollectionParameterGenerationStep;
import me.supcheg.advancedgui.code.processor.step.builder.param.BuilderImplParameterGenerationStep;
import me.supcheg.advancedgui.code.processor.step.builder.param.BuilderImplPrimitiveParameterGenerationStep;
import me.supcheg.advancedgui.code.processor.util.AnnotationHelper;
import me.supcheg.advancedgui.code.processor.util.MoreTypes;
import org.jetbrains.annotations.NotNull;

public class BuilderImplGenerationStep implements GenerationStep {
   private final AnnotationHelper annotationHelper;
   private final MoreTypes moreTypes;
   private final ClassName buildableImplClassName;
   private final ClassName builderImplClassName;
   private final Element element;
   private final List<ParameterSpec> parameters;

   public void generate(@NotNull Builder target) {
      Builder targetBuilder = TypeSpec.classBuilder(this.builderImplClassName).addSuperinterface(this.findBuilderElement(this.element).asType()).addModifiers(new Modifier[]{Modifier.STATIC});
      List<BuilderImplParameterInitializer> parameterInitializers = this.parameters.stream().map(this::parameterInitializer).toList();
      (new BuilderImplNoArgsConstructorGenerationStep(parameterInitializers)).generate(targetBuilder);
      (new BuilderImplTargetConstructorGenerationStep(this.buildableImplClassName, parameterInitializers)).generate(targetBuilder);
      Iterator var4 = this.parameters.iterator();

      while(var4.hasNext()) {
         ParameterSpec parameter = (ParameterSpec)var4.next();
         this.parameterGenerationStep(parameter).generate(targetBuilder);
      }

      (new BuilderImplBuildMethodGenerationStep(this.buildableImplClassName, parameterInitializers)).generate(targetBuilder);
      target.addType(targetBuilder.build());
      (new ToBuilderImplGenerationStep(this.builderImplClassName)).generate(target);
   }

   private TypeElement findBuilderElement(@NotNull Element element) {
      Iterator var2 = ElementFilter.typesIn(element.getEnclosedElements()).iterator();

      TypeElement candidate;
      do {
         if (!var2.hasNext()) {
            throw new IllegalArgumentException("No builder found for element " + String.valueOf(element));
         }

         candidate = (TypeElement)var2.next();
      } while(candidate.getKind() != ElementKind.INTERFACE || !candidate.getSimpleName().toString().equals("Builder"));

      return candidate;
   }

   private BuilderImplParameterInitializer parameterInitializer(@NotNull ParameterSpec parameter) {
      return (BuilderImplParameterInitializer)(this.moreTypes.isAccessible(Collection.class, parameter.type()) ? new BuilderImplCollectionParameterInitializer(this.moreTypes, parameter) : new BuilderImplParameterInitializer(parameter));
   }

   private BuilderImplParameterGenerationStep parameterGenerationStep(@NotNull ParameterSpec parameter) {
      if (parameter.type().isPrimitive()) {
         return new BuilderImplPrimitiveParameterGenerationStep(parameter, this.annotationHelper, this.builderImplClassName);
      } else {
         return (BuilderImplParameterGenerationStep)(this.moreTypes.isAccessible(Collection.class, parameter.type()) ? new BuilderImplCollectionParameterGenerationStep(parameter, this.annotationHelper, this.builderImplClassName) : new BuilderImplParameterGenerationStep(parameter, this.annotationHelper, this.builderImplClassName));
      }
   }

   public BuilderImplGenerationStep(AnnotationHelper annotationHelper, MoreTypes moreTypes, ClassName buildableImplClassName, ClassName builderImplClassName, Element element, List<ParameterSpec> parameters) {
      this.annotationHelper = annotationHelper;
      this.moreTypes = moreTypes;
      this.buildableImplClassName = buildableImplClassName;
      this.builderImplClassName = builderImplClassName;
      this.element = element;
      this.parameters = parameters;
   }
}
