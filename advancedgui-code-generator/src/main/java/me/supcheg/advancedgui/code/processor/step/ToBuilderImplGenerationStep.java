package me.supcheg.advancedgui.code.processor.step;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.CodeBlock;
import com.palantir.javapoet.MethodSpec;
import com.palantir.javapoet.TypeSpec.Builder;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.lang.model.element.Modifier;

@RequiredArgsConstructor
public class ToBuilderImplGenerationStep implements GenerationStep {
    private final ClassName builderImplClassName;

    public void generate(Builder target) {
        target.addMethod(MethodSpec.methodBuilder("toBuilder").addModifiers(Modifier.PUBLIC).addAnnotation(NotNull.class).addAnnotation(Override.class).returns(this.builderImplClassName).addCode(this.code()).build());
    }

    private CodeBlock code() {
        return CodeBlock.of("return new $T(this);\n", this.builderImplClassName);
    }
}
