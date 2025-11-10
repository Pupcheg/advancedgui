package me.supcheg.advancedgui.code.processor.step.builder.constructor;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.CodeBlock;
import com.palantir.javapoet.ParameterSpec;
import me.supcheg.advancedgui.code.processor.util.MoreTypes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class BuilderImplCollectionParameterInitializer extends BuilderImplParameterInitializer {
    private final MoreTypes moreTypes;
    private final BuilderImplCollectionParameterInitializer.CollectionParameter collectionParameter;

    public BuilderImplCollectionParameterInitializer(MoreTypes moreTypes, ParameterSpec parameter) {
        super(parameter);
        this.moreTypes = moreTypes;
        this.collectionParameter = this.implementationClass();
    }

    public CodeBlock newValueInitializer() {
        return CodeBlock.of("this.$L = new $T<>();\n", this.parameter.name(), this.collectionParameter.implClass());
    }

    public CodeBlock copyingInitializer(String from) {
        return CodeBlock.of("this.$L = new $T<>($L.$L);\n", this.parameter.name(), this.collectionParameter.implClass(), from, this.parameter.name());
    }

    public CodeBlock finalizer() {
        return this.collectionParameter.finalizer().apply(this.parameter);
    }

    private BuilderImplCollectionParameterInitializer.CollectionParameter implementationClass() {
        var type = this.parameter.type();
        if (this.moreTypes.isAccessible(List.class, type)) {
            return new CollectionParameter(ClassName.get(ArrayList.class), (param) -> CodeBlock.of("$T.copyOf($L)", List.class, param.name()));
        } else if (this.moreTypes.isAccessible(Set.class, type)) {
            return new CollectionParameter(ClassName.get(LinkedHashSet.class), (param) -> CodeBlock.of("$T.copyOf($L)", Set.class, param.name()));
        } else if (this.moreTypes.isAccessible(Collection.class, type)) {
            return new CollectionParameter(ClassName.get(ArrayList.class), (param) -> CodeBlock.of("$T.copyOf($L)", List.class, param.name()));
        } else {
            throw new IllegalArgumentException("Unsupported type: " + type);
        }
    }

    record CollectionParameter(ClassName implClass, Function<ParameterSpec, CodeBlock> finalizer) {
    }
}
