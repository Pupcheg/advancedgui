package me.supcheg.advancedgui.code.processor;

import com.palantir.javapoet.MethodSpec;
import com.palantir.javapoet.TypeSpec;
import me.supcheg.advancedgui.code.processor.property.Property;

import javax.lang.model.element.TypeElement;
import java.util.List;

public class ObjectImplTypeGenerator {
    public TypeSpec objectImplType(TypeElement subjectType, List<? extends Property> properties) {
        TypeSpec.Builder builder = TypeSpec.recordBuilder(subjectType.getSimpleName() + "Impl")
                .addSuperinterface(subjectType.asType());

        var constructorBuilder = MethodSpec.constructorBuilder();
        new PropertyAppender(constructorBuilder).scan(properties);
        builder.recordConstructor(constructorBuilder.build());

        return builder.build();
    }

    private record PropertyAppender(MethodSpec.Builder constructor) implements GenerationPropertyVisitor {
        @Override
        public void visit(Property property) {
            constructor.addParameter(property.typename(), property.name());
        }
    }
}
