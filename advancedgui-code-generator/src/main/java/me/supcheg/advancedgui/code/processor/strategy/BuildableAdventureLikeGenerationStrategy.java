package me.supcheg.advancedgui.code.processor.strategy;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.ParameterSpec;
import com.palantir.javapoet.TypeSpec;
import com.palantir.javapoet.TypeSpec.Builder;
import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import me.supcheg.advancedgui.code.processor.parameter.ParameterSpecLookup;
import me.supcheg.advancedgui.code.processor.step.ConstructorGenerationStep;
import me.supcheg.advancedgui.code.processor.step.GeneratedAnnotationGenerationStep;
import me.supcheg.advancedgui.code.processor.step.builder.BuilderImplGenerationStep;
import me.supcheg.advancedgui.code.processor.util.AnnotationHelper;
import me.supcheg.advancedgui.code.processor.util.MoreTypes;
import org.jetbrains.annotations.NotNull;

public class BuildableAdventureLikeGenerationStrategy implements AdventureLikeGenerationStrategy {
   public static final String BUILDER_NAME = "Builder";
   public static final String IMPL_SUFFIX = "Impl";
   private final AnnotationHelper annotationHelper;
   private final MoreTypes moreTypes;
   private final ParameterSpecLookup parameterSpecLookup;

   @NotNull
   public TypeSpec generate(@NotNull Element element) {
      ClassName targetName = ClassName.get(this.getPackageElement(element).getQualifiedName().toString(), String.valueOf(element.getSimpleName()) + "Impl", new String[0]);
      Builder target = TypeSpec.recordBuilder(targetName).addSuperinterface(element.asType());
      List<ParameterSpec> parameters = this.parameterSpecLookup.listRecordParametersForInterface(element);
      (new ConstructorGenerationStep(parameters)).generate(target);
      (new BuilderImplGenerationStep(this.annotationHelper, this.moreTypes, targetName, targetName.nestedClass("BuilderImpl"), element, parameters)).generate(target);
      (new GeneratedAnnotationGenerationStep(parameters)).generate(target);
      return target.build();
   }

   private PackageElement getPackageElement(Element element) {
      while(element != null && element.getKind() != ElementKind.PACKAGE) {
         element = element.getEnclosingElement();
      }

      return (PackageElement)element;
   }

   public BuildableAdventureLikeGenerationStrategy(AnnotationHelper annotationHelper, MoreTypes moreTypes, ParameterSpecLookup parameterSpecLookup) {
      this.annotationHelper = annotationHelper;
      this.moreTypes = moreTypes;
      this.parameterSpecLookup = parameterSpecLookup;
   }
}
