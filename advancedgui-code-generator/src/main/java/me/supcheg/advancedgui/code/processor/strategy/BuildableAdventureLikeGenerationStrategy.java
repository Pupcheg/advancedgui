package me.supcheg.advancedgui.code.processor.strategy;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.TypeSpec;
import lombok.RequiredArgsConstructor;
import me.supcheg.advancedgui.code.processor.parameter.ParameterSpecLookup;
import me.supcheg.advancedgui.code.processor.step.ConstructorGenerationStep;
import me.supcheg.advancedgui.code.processor.step.GeneratedAnnotationGenerationStep;
import me.supcheg.advancedgui.code.processor.step.builder.BuilderImplGenerationStep;
import me.supcheg.advancedgui.code.processor.util.AnnotationHelper;
import me.supcheg.advancedgui.code.processor.util.MoreTypes;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;

@RequiredArgsConstructor
public class BuildableAdventureLikeGenerationStrategy implements AdventureLikeGenerationStrategy {
    public static final String BUILDER_NAME = "Builder";
    public static final String IMPL_SUFFIX = "Impl";

    private final AnnotationHelper annotationHelper;
    private final MoreTypes moreTypes;
    private final ParameterSpecLookup parameterSpecLookup;

    public TypeSpec generate(Element element) {
        var targetName = ClassName.get(this.getPackageElement(element).getQualifiedName().toString(), element.getSimpleName() + IMPL_SUFFIX);
        var target = TypeSpec.recordBuilder(targetName).addSuperinterface(element.asType());
        var parameters = this.parameterSpecLookup.listRecordParametersForInterface(element);
        (new ConstructorGenerationStep(parameters)).generate(target);
        (new BuilderImplGenerationStep(this.annotationHelper, this.moreTypes, targetName, targetName.nestedClass(BUILDER_NAME + IMPL_SUFFIX), element, parameters)).generate(target);
        (new GeneratedAnnotationGenerationStep(parameters)).generate(target);
        return target.build();
    }

    private PackageElement getPackageElement(Element element) {
        while (element != null && element.getKind() != ElementKind.PACKAGE) {
            element = element.getEnclosingElement();
        }

        return (PackageElement) element;
    }
}
