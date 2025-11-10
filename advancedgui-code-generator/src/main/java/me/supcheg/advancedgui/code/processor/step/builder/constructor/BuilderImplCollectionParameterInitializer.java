package me.supcheg.advancedgui.code.processor.step.builder.constructor;

import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.CodeBlock;
import com.palantir.javapoet.ParameterSpec;
import com.palantir.javapoet.TypeName;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import me.supcheg.advancedgui.code.processor.util.MoreTypes;
import org.jetbrains.annotations.NotNull;

public class BuilderImplCollectionParameterInitializer extends BuilderImplParameterInitializer {
   private final MoreTypes moreTypes;
   private final BuilderImplCollectionParameterInitializer.CollectionParameter collectionParameter;

   public BuilderImplCollectionParameterInitializer(MoreTypes moreTypes, ParameterSpec parameter) {
      super(parameter);
      this.moreTypes = moreTypes;
      this.collectionParameter = this.implementationClass();
   }

   public CodeBlock newValueInitializer() {
      return CodeBlock.of("this.$L = new $T<>();\n", new Object[]{this.parameter.name(), this.collectionParameter.implClass()});
   }

   public CodeBlock copyingInitializer(String from) {
      return CodeBlock.of("this.$L = new $T<>($L.$L);\n", new Object[]{this.parameter.name(), this.collectionParameter.implClass(), from, this.parameter.name()});
   }

   public CodeBlock finalizer() {
      return (CodeBlock)this.collectionParameter.finalizer().apply(this.parameter);
   }

   @NotNull
   private BuilderImplCollectionParameterInitializer.CollectionParameter implementationClass() {
      TypeName type = this.parameter.type();
      if (this.moreTypes.isAccessible(List.class, type)) {
         return new BuilderImplCollectionParameterInitializer.CollectionParameter(ClassName.get(ArrayList.class), (param) -> {
            return CodeBlock.of("$T.copyOf($L)", new Object[]{List.class, param.name()});
         });
      } else if (this.moreTypes.isAccessible(Set.class, type)) {
         return new BuilderImplCollectionParameterInitializer.CollectionParameter(ClassName.get(LinkedHashSet.class), (param) -> {
            return CodeBlock.of("$T.copyOf($L)", new Object[]{Set.class, param.name()});
         });
      } else if (this.moreTypes.isAccessible(Collection.class, type)) {
         return new BuilderImplCollectionParameterInitializer.CollectionParameter(ClassName.get(ArrayList.class), (param) -> {
            return CodeBlock.of("$T.copyOf($L)", new Object[]{List.class, param.name()});
         });
      } else {
         throw new IllegalArgumentException("Unsupported type: " + String.valueOf(type));
      }
   }

   static record CollectionParameter(ClassName implClass, Function<ParameterSpec, CodeBlock> finalizer) {
      CollectionParameter(ClassName implClass, Function<ParameterSpec, CodeBlock> finalizer) {
         this.implClass = implClass;
         this.finalizer = finalizer;
      }

      public ClassName implClass() {
         return this.implClass;
      }

      public Function<ParameterSpec, CodeBlock> finalizer() {
         return this.finalizer;
      }
   }
}
