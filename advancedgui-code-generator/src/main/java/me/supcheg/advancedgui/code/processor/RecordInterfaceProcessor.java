package me.supcheg.advancedgui.code.processor;

import com.palantir.javapoet.JavaFile;
import me.supcheg.advancedgui.code.RecordInterface;
import me.supcheg.advancedgui.code.processor.collection.CollectionMethodsResolver;
import me.supcheg.advancedgui.code.processor.property.PropertyResolver;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ReferenceType;
import javax.lang.model.type.TypeMirror;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.UnaryOperator;

public class RecordInterfaceProcessor extends AbstractProcessor {
    @MonotonicNonNull
    private GenerationStrategy strategy;

    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        var elements = processingEnv.getElementUtils();
        var types = processingEnv.getTypeUtils();
        var annotations = new Annotations(
                elements.getTypeElement("org.checkerframework.checker.nullness.qual.Nullable"),
                elements.getTypeElement("org.checkerframework.checker.nullness.qual.NonNull"),
                elements.getTypeElement("org.jetbrains.annotations.Unmodifiable")
        );

        var collectionType = (ReferenceType) elements.getTypeElement(Collection.class.getName()).asType();
        var propertyResolver = new PropertyResolver(types, collectionType);

        var collectionResolver = new CollectionMethodsResolver(types, elements);

        var abstractBuilder = elements.getTypeElement("me.supcheg.advancedgui.api.builder.AbstractBuilder");
        UnaryOperator<TypeMirror> abstractBuilderAppender = type -> types.getDeclaredType(abstractBuilder, type);

        strategy = new DefaultGenerationStrategy(
                elements,
                propertyResolver,
                new ObjectImplTypeGenerator(annotations),
                new BuilderTypeGenerator(annotations, collectionResolver, List.of(abstractBuilderAppender)),
                new BuilderImplTypeGenerator(collectionResolver, annotations)
        );
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of(
                RecordInterface.class.getName()
        );
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_21;
    }

    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        annotations.stream()
                .map(roundEnv::getElementsAnnotatedWith)
                .flatMap(Collection::stream)
                .filter(TypeElement.class::isInstance)
                .map(TypeElement.class::cast)
                .forEach(this::processTypeSafely);
        return true;
    }

    private void processTypeSafely(TypeElement typeElement) {
        try {
            processType(typeElement);
        } catch (Exception exception) {
            processingEnv.getMessager().printError(String.valueOf(exception.getMessage()), typeElement);
        }
    }

    private void processType(TypeElement typeElement) throws IOException {
        var typePackage = processingEnv.getElementUtils().getPackageOf(typeElement).getQualifiedName().toString();
        var filer = processingEnv.getFiler();

        for (var type : strategy.generate(typeElement)) {
            JavaFile.builder(typePackage, type)
                    .build()
                    .writeTo(filer);
        }
    }
}
