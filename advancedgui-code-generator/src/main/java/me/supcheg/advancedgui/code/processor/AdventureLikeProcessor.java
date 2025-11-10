package me.supcheg.advancedgui.code.processor;

import com.palantir.javapoet.JavaFile;
import me.supcheg.advancedgui.code.processor.parameter.ParameterSpecLookup;
import me.supcheg.advancedgui.code.processor.strategy.AdventureLikeGenerationStrategy;
import me.supcheg.advancedgui.code.processor.strategy.BuildableAdventureLikeGenerationStrategy;
import me.supcheg.advancedgui.code.processor.strategy.SimpleAdventureLikeGenerationStrategy;
import me.supcheg.advancedgui.code.processor.util.AnnotationHelper;
import me.supcheg.advancedgui.code.processor.util.MoreTypes;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.util.Set;

@SupportedAnnotationTypes({"me.supcheg.advancedgui.code.AdventureLike"})
@SupportedSourceVersion(SourceVersion.RELEASE_21)
public class AdventureLikeProcessor extends AbstractProcessor {
    private AdventureLikeGenerationStrategy defaultStrategy;
    private AdventureLikeGenerationStrategy buildableStrategy;
    private MoreTypes moreTypes;

    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.moreTypes = new MoreTypes(processingEnv);
        this.defaultStrategy = new SimpleAdventureLikeGenerationStrategy(new ParameterSpecLookup());
        this.buildableStrategy = new BuildableAdventureLikeGenerationStrategy(new AnnotationHelper(), this.moreTypes, new ParameterSpecLookup());
    }

    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            for (Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
                try {
                    this.processElement(element);
                } catch (Exception var8) {
                    this.processingEnv.getMessager().printError(var8.toString(), element);
                }
            }
        }

        return true;
    }

    private void processElement(Element element) throws IOException {
        if (element.getKind() != ElementKind.INTERFACE) {
            throw new RuntimeException("Element is not an interface");
        } else {
            var interfaceClassPackage = ((PackageElement) element.getEnclosingElement()).getQualifiedName().toString();
            var typeSpec = this.strategyFor(element).generate(element);
            var javaFile = JavaFile.builder(interfaceClassPackage, typeSpec).skipJavaLangImports(true).indent("    ").build();
            javaFile.writeTo(this.processingEnv.getFiler());
        }
    }

    private AdventureLikeGenerationStrategy strategyFor(Element element) {
        return this.isBuildable(element) ? this.buildableStrategy : this.defaultStrategy;
    }

    private boolean isBuildable(Element element) {
        return this.moreTypes.isAccessible("net.kyori.adventure.util.Buildable", element.asType());
    }
}
