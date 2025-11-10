package me.supcheg.advancedgui.code.processor.strategy;

import com.palantir.javapoet.ParameterSpec;
import com.palantir.javapoet.TypeSpec;
import com.palantir.javapoet.TypeSpec.Builder;
import java.util.List;
import javax.lang.model.element.Element;
import me.supcheg.advancedgui.code.processor.parameter.ParameterSpecLookup;
import me.supcheg.advancedgui.code.processor.step.ConstructorGenerationStep;
import org.jetbrains.annotations.NotNull;

public class SimpleAdventureLikeGenerationStrategy implements AdventureLikeGenerationStrategy {
   private final ParameterSpecLookup parameterSpecLookup;

   @NotNull
   public TypeSpec generate(@NotNull Element element) {
      Builder builder = TypeSpec.recordBuilder(String.valueOf(element.getSimpleName()) + "Impl").addSuperinterface(element.asType());
      List<ParameterSpec> parameters = this.parameterSpecLookup.listRecordParametersForInterface(element);
      (new ConstructorGenerationStep(parameters)).generate(builder);
      return builder.build();
   }

   public SimpleAdventureLikeGenerationStrategy(ParameterSpecLookup parameterSpecLookup) {
      this.parameterSpecLookup = parameterSpecLookup;
   }
}
