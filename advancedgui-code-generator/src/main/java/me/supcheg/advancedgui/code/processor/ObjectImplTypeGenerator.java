package me.supcheg.advancedgui.code.processor;

import com.palantir.javapoet.MethodSpec;
import com.palantir.javapoet.ParameterSpec;
import com.palantir.javapoet.TypeSpec;
import lombok.RequiredArgsConstructor;
import me.supcheg.advancedgui.code.processor.property.ObjectCollectionProperty;
import me.supcheg.advancedgui.code.processor.property.ObjectProperty;
import me.supcheg.advancedgui.code.processor.property.PrimitiveProperty;
import me.supcheg.advancedgui.code.processor.property.Property;

import javax.lang.model.element.TypeElement;
import java.util.List;

@RequiredArgsConstructor
public class ObjectImplTypeGenerator {
    private final Annotations annotations;

    public TypeSpec objectImplType(TypeElement subjectType, List<? extends Property> properties) {
        TypeSpec.Builder builder = TypeSpec.recordBuilder(subjectType.getSimpleName() + "Impl")
                .addSuperinterface(subjectType.asType());

        var constructorBuilder = MethodSpec.constructorBuilder();
        new PropertyAppender(constructorBuilder).scan(properties);
        builder.recordConstructor(constructorBuilder.build());

        return builder.build();
    }

    @RequiredArgsConstructor
    private final class PropertyAppender implements GenerationPropertyVisitor {
        private final MethodSpec.Builder constructor;

        @Override
        public void visitObject(ObjectProperty property) {
            constructor.addParameter(
                    ParameterSpec.builder(property.typename(), property.name())
                            .addAnnotations(annotations.nonNull())
                            .build()
            );
        }

        @Override
        public void visitPrimitive(PrimitiveProperty property) {
            constructor.addParameter(property.typename(), property.name());
        }

        @Override
        public void visitObjectCollection(ObjectCollectionProperty property) {
            constructor.addParameter(
                    ParameterSpec.builder(property.typename(), property.name())
                            .addAnnotations(annotations.nonNull())
                            .build()
            );
        }
    }
}
