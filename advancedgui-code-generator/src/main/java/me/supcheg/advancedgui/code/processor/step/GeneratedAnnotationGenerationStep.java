package me.supcheg.advancedgui.code.processor.step;

import com.palantir.javapoet.AnnotationSpec;
import com.palantir.javapoet.ParameterSpec;
import com.palantir.javapoet.TypeSpec.Builder;
import lombok.RequiredArgsConstructor;
import me.supcheg.advancedgui.code.processor.AdventureLikeProcessor;
import org.jetbrains.annotations.NotNull;

import javax.annotation.processing.Generated;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class GeneratedAnnotationGenerationStep implements GenerationStep {
    private final List<ParameterSpec> parameters;

    public void generate(Builder target) {
        target.addAnnotation(AnnotationSpec.builder(Generated.class).addMember("value", "$S", AdventureLikeProcessor.class.getName()).addMember("date", "$S", OffsetDateTime.now().toString()).addMember("comments", "$S", new Object[]{this.buildParametersComment()}).build());
    }

    private String buildParametersComment() {
        return this.parameters.isEmpty() ? "No parameters found" : this.parameters.stream().map((param) -> param.type() + " " + param.name()).collect(Collectors.joining(", ", "Parameters: ", ""));
    }
}
