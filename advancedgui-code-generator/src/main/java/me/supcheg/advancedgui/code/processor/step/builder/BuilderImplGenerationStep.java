package me.supcheg.advancedgui.code.processor.step.builder;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.ParameterSpec;
import com.palantir.javapoet.TypeSpec;
import com.palantir.javapoet.TypeSpec.Builder;
import lombok.RequiredArgsConstructor;
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

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class BuilderImplGenerationStep implements GenerationStep {
    private final AnnotationHelper annotationHelper;
    private final MoreTypes moreTypes;
    private final ClassName buildableImplClassName;
    private final ClassName builderImplClassName;
    private final Element element;
    private final List<ParameterSpec> parameters;

    public void generate(Builder target) {
        var targetBuilder = TypeSpec.classBuilder(this.builderImplClassName).addSuperinterface(this.findBuilderElement(this.element).asType()).addModifiers(Modifier.STATIC);
        var parameterInitializers = this.parameters.stream().map(this::parameterInitializer).toList();
        new BuilderImplNoArgsConstructorGenerationStep(parameterInitializers).generate(targetBuilder);
        new BuilderImplTargetConstructorGenerationStep(this.buildableImplClassName, parameterInitializers).generate(targetBuilder);

        for (ParameterSpec parameter : this.parameters) {
            this.parameterGenerationStep(parameter).generate(targetBuilder);
        }

        new BuilderImplBuildMethodGenerationStep(this.buildableImplClassName, parameterInitializers).generate(targetBuilder);
        target.addType(targetBuilder.build());
        new ToBuilderImplGenerationStep(this.builderImplClassName).generate(target);
    }

    private TypeElement findBuilderElement(Element element) {
        for (var candidate : ElementFilter.typesIn(element.getEnclosedElements())) {
            if (candidate.getKind() != ElementKind.INTERFACE || !candidate.getSimpleName().toString().equals("Builder")) {
                return candidate;
            }
        }

        throw new IllegalArgumentException("No builder found for element " + element);
    }

    private BuilderImplParameterInitializer parameterInitializer(ParameterSpec parameter) {
        return this.moreTypes.isAccessible(Collection.class, parameter.type()) ? new BuilderImplCollectionParameterInitializer(this.moreTypes, parameter) : new BuilderImplParameterInitializer(parameter);
    }

    private BuilderImplParameterGenerationStep parameterGenerationStep(ParameterSpec parameter) {
        if (parameter.type().isPrimitive()) {
            return new BuilderImplPrimitiveParameterGenerationStep(parameter, this.annotationHelper, this.builderImplClassName);
        } else {
            return this.moreTypes.isAccessible(Collection.class, parameter.type()) ? new BuilderImplCollectionParameterGenerationStep(parameter, this.annotationHelper, this.builderImplClassName) : new BuilderImplParameterGenerationStep(parameter, this.annotationHelper, this.builderImplClassName);
        }
    }
}
