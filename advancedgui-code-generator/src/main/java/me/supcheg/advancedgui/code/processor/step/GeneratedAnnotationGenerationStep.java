package me.supcheg.advancedgui.code.processor.step;

import com.palantir.javapoet.AnnotationSpec;
import com.palantir.javapoet.ParameterSpec;
import com.palantir.javapoet.TypeSpec.Builder;
import lombok.RequiredArgsConstructor;
import me.supcheg.advancedgui.code.processor.AdventureLikeProcessor;

import javax.annotation.processing.Generated;
import java.time.OffsetDateTime;
import java.util.List;

import static java.util.stream.Collectors.joining;

@RequiredArgsConstructor
public class GeneratedAnnotationGenerationStep implements GenerationStep {
    private final List<ParameterSpec> parameters;

    public void generate(Builder target) {
        target.addAnnotation(
                AnnotationSpec.builder(Generated.class)
                        .addMember("value", "$S", AdventureLikeProcessor.class.getName())
                        .addMember("date", "$S", OffsetDateTime.now())
                        .addMember("comments", "$S", buildParametersComment())
                        .build()
        );
    }

    private String buildParametersComment() {
        return parameters.isEmpty() ?
                "No parameters found" :
                parameters.stream()
                        .map(param -> param.type() + " " + param.name())
                        .collect(joining(", ", "Parameters: ", ""));
    }
}
