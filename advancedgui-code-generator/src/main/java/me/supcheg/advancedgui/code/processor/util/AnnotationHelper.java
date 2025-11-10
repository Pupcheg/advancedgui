package me.supcheg.advancedgui.code.processor.util;

import com.palantir.javapoet.AnnotationSpec;
import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.TypeName;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AnnotationHelper {
   private final Set<TypeName> nullabilityAnnotations = Set.of(TypeName.get(Nullable.class), TypeName.get(NotNull.class));

   @NotNull
   public List<AnnotationSpec> putNullabilityAnnotation(@NotNull List<AnnotationSpec> annotations, @NotNull Class<? extends Annotation> type) {
      if (!this.isNullabilityAnnotation(TypeName.get(type))) {
         throw new IllegalArgumentException("Unsupported nullability annotation: " + String.valueOf(type));
      } else {
         List<AnnotationSpec> copy = new ArrayList(annotations);
         int i = this.indexOfNullabilityAnnotation(annotations);
         if (i != -1) {
            copy.set(i, annotationSpec(type));
         } else {
            copy.addFirst(annotationSpec(type));
         }

         return copy;
      }
   }

   @NotNull
   public List<AnnotationSpec> removeNullabilityAnnotations(@NotNull List<AnnotationSpec> annotations) {
      List<AnnotationSpec> copy = new ArrayList(annotations);
      copy.removeIf(this::isNullabilityAnnotation);
      return copy;
   }

   @NotNull
   public List<AnnotationSpec> addIfNotPresent(@NotNull List<AnnotationSpec> annotations, @NotNull Class<? extends Annotation> type) {
      if (annotations.stream().map(AnnotationSpec::type).anyMatch(Predicate.isEqual(ClassName.get(type)))) {
         return annotations;
      } else {
         List<AnnotationSpec> copy = new ArrayList(annotations);
         copy.add(annotationSpec(type));
         return copy;
      }
   }

   @NotNull
   public List<AnnotationSpec> removeIfPresent(@NotNull List<AnnotationSpec> annotations, @NotNull Class<? extends Annotation> type) {
      ClassName annotationName = ClassName.get(type);
      if (annotations.stream().map(AnnotationSpec::type).noneMatch(Predicate.isEqual(annotationName))) {
         return annotations;
      } else {
         List<AnnotationSpec> copy = new ArrayList(annotations);
         copy.removeIf((annotationSpec) -> {
            return annotationSpec.type().equals(annotationName);
         });
         return copy;
      }
   }

   @NotNull
   @Contract("_ -> new")
   private static AnnotationSpec annotationSpec(Class<? extends Annotation> type) {
      return AnnotationSpec.builder(type).build();
   }

   private int indexOfNullabilityAnnotation(@NotNull List<AnnotationSpec> annotations) {
      for(int i = 0; i < annotations.size(); ++i) {
         if (this.isNullabilityAnnotation((AnnotationSpec)annotations.get(i))) {
            return i;
         }
      }

      return -1;
   }

   public boolean isNullabilityAnnotation(@NotNull AnnotationSpec spec) {
      return this.isNullabilityAnnotation(spec.type());
   }

   public boolean isNullabilityAnnotation(@NotNull TypeName type) {
      return this.nullabilityAnnotations.contains(type);
   }
}
