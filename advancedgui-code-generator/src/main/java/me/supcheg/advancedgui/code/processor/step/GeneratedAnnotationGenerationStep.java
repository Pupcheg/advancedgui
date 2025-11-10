package me.supcheg.advancedgui.code.processor.step;

import com.palantir.javapoet.AnnotationSpec;
import com.palantir.javapoet.ParameterSpec;
import com.palantir.javapoet.TypeSpec.Builder;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.processing.Generated;
import me.supcheg.advancedgui.code.processor.AdventureLikeProcessor;
import org.jetbrains.annotations.NotNull;

public class GeneratedAnnotationGenerationStep implements GenerationStep {
   private final List<ParameterSpec> parameters;

   public void generate(@NotNull Builder target) {
      target.addAnnotation(AnnotationSpec.builder(Generated.class).addMember("value", "$S", new Object[]{AdventureLikeProcessor.class.getName()}).addMember("date", "$S", new Object[]{OffsetDateTime.now().toString()}).addMember("comments", "$S", new Object[]{this.buildParametersComment()}).build());
   }

   private String buildParametersComment() {
      return this.parameters.isEmpty() ? "No parameters found" : (String)this.parameters.stream().map((param) -> {
         String var10000 = String.valueOf(param.type());
         return var10000 + " " + param.name();
      }).collect(Collectors.joining(", ", "Parameters: ", ""));
   }

   public GeneratedAnnotationGenerationStep(List<ParameterSpec> parameters) {
      this.parameters = parameters;
   }
}
