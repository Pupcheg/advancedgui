package me.supcheg.advancedgui.code.processor.step.builder;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.CodeBlock;
import com.palantir.javapoet.TypeSpec.Builder;
import lombok.RequiredArgsConstructor;
import me.supcheg.advancedgui.code.processor.step.GenerationStep;
import me.supcheg.advancedgui.code.processor.step.builder.constructor.BuilderImplParameterInitializer;
import org.jetbrains.annotations.NotNull;

import javax.lang.model.element.Modifier;
import java.util.List;

import static com.palantir.javapoet.MethodSpec.methodBuilder;

@RequiredArgsConstructor
public class BuilderImplBuildMethodGenerationStep implements GenerationStep {
    private final ClassName buildableImplClassName;
    private final List<BuilderImplParameterInitializer> parameters;

    public void generate(Builder target) {
        target.addMethod(
                methodBuilder("build")
                        .addModifiers(Modifier.PUBLIC)
                        .addAnnotation(NotNull.class)
                        .addAnnotation(Override.class)
                        .returns(buildableImplClassName)
                        .addCode(code())
                        .build()
        );
    }

    private CodeBlock code() {
        var builder = CodeBlock.builder();
        builder.add("return new $T(\n", buildableImplClassName);
        builder.indent();

        for (var it = parameters.iterator(); it.hasNext(); ) {
            var param = it.next();
            builder.add(param.finalizer());
            if (it.hasNext()) {
                builder.add(",");
            }
            builder.add("\n");
        }

        builder.unindent();
        builder.add(");\n");
        return builder.build();
    }
}
