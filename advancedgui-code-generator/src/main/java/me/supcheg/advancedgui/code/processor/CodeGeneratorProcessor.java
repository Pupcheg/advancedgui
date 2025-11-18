package me.supcheg.advancedgui.code.processor;

import com.palantir.javapoet.JavaFile;
import me.supcheg.advancedgui.code.BuilderInterface;
import me.supcheg.advancedgui.code.RecordInterface;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ReferenceType;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class CodeGeneratorProcessor extends AbstractProcessor {
    @MonotonicNonNull
    private AnnotationGenerator builderInterfaceGenerator;
    @MonotonicNonNull
    private AnnotationGenerator recordInterfaceGenerator;

    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        var elements = processingEnv.getElementUtils();
        var types = processingEnv.getTypeUtils();
        var annotations = new Annotations(
                elements.getTypeElement("org.checkerframework.checker.nullness.qual.Nullable"),
                elements.getTypeElement("org.checkerframework.checker.nullness.qual.NonNull"),
                elements.getTypeElement("org.jetbrains.annotations.Unmodifiable")
        );
        var interfaces = new BuilderInterfaces(
                elements.getTypeElement("me.supcheg.advancedgui.api.builder.Buildable"),
                elements.getTypeElement("me.supcheg.advancedgui.api.builder.AbstractBuilder")
        );

        var collectionType = (ReferenceType) elements.getTypeElement(Collection.class.getName()).asType();

        var propertyResolver = new PropertyResolver(types, collectionType);
        var namesResolver = new NamesResolver();
        var collectionResolver = new CollectionMethodsResolver(types, elements);

        var objectImplTypeGenerator = new ObjectImplTypeGenerator(annotations);
        var builderTypeGenerator = new BuilderTypeGenerator(annotations, interfaces, collectionResolver);
        var builderImplTypeGenerator = new BuilderImplTypeGenerator(collectionResolver, annotations);

        builderInterfaceGenerator = new AnnotationGenerator(
                propertyResolver,
                namesResolver,
                List.of(
                        builderTypeGenerator
                )
        );

        recordInterfaceGenerator = new AnnotationGenerator(
                propertyResolver,
                namesResolver,
                List.of(
                        objectImplTypeGenerator,
                        builderTypeGenerator,
                        builderImplTypeGenerator
                )
        );
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of(
                RecordInterface.class.getName(),
                BuilderInterface.class.getName()
        );
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_21;
    }

    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        processEach(RecordInterface.class, recordInterfaceGenerator, roundEnv);
        processEach(BuilderInterface.class, builderInterfaceGenerator, roundEnv);
        return true;
    }

    private void processEach(Class<? extends Annotation> annotation, AnnotationGenerator annotationGenerator, RoundEnvironment roundEnv) {
        roundEnv.getElementsAnnotatedWith(annotation).stream()
                .filter(TypeElement.class::isInstance)
                .map(TypeElement.class::cast)
                .forEach(typeElement -> processTypeSafely(typeElement, annotationGenerator));
    }

    private void processTypeSafely(TypeElement typeElement, AnnotationGenerator annotationGenerator) {
        try {
            processType(typeElement, annotationGenerator);
        } catch (Exception exception) {
            processingEnv.getMessager().printError(printToString(exception), typeElement);
        }
    }

    private static String printToString(Throwable throwable) {
        var sw = new StringWriter();
        throwable.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    private void processType(TypeElement typeElement, AnnotationGenerator annotationGenerator) throws IOException {
        var typePackage = processingEnv.getElementUtils().getPackageOf(typeElement).getQualifiedName().toString();
        var filer = processingEnv.getFiler();

        for (var type : annotationGenerator.generate(typeElement)) {
            JavaFile.builder(typePackage, type)
                    .skipJavaLangImports(true)
                    .build()
                    .writeTo(filer);
        }
    }
}
