package me.supcheg.advancedgui.code.processor.parameter;

import com.palantir.javapoet.AnnotationSpec;
import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.ParameterSpec;
import com.palantir.javapoet.TypeName;
import com.palantir.javapoet.ParameterSpec.Builder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import org.jetbrains.annotations.NotNull;

public class ParameterSpecLookup {
   @NotNull
   public List<ParameterSpec> listRecordParametersForInterface(@NotNull Element element) {
      List<ParameterSpec> parameters = new ArrayList();
      Iterator var3 = ElementFilter.methodsIn(element.getEnclosedElements()).iterator();

      while(var3.hasNext()) {
         ExecutableElement method = (ExecutableElement)var3.next();
         if (this.isValueMethod(method)) {
            parameters.add(this.makeParameter(method));
         }
      }

      return parameters;
   }

   private boolean isValueMethod(@NotNull ExecutableElement method) {
      return !method.isDefault() && !method.getModifiers().contains(Modifier.STATIC) && method.getReturnType().getKind() != TypeKind.VOID && method.getParameters().isEmpty();
   }

   @NotNull
   private ParameterSpec makeParameter(@NotNull ExecutableElement method) {
      TypeMirror returnType = method.getReturnType();
      Builder var10000 = ParameterSpec.builder(TypeName.get(returnType), method.getSimpleName().toString(), new Modifier[0]);
      Stream var10001 = returnType.getAnnotationMirrors().stream().map(this::convertAnnotationMirrorToAnnotationSpec);
      Objects.requireNonNull(var10001);
      return var10000.addAnnotations(var10001::iterator).build();
   }

   @NotNull
   private AnnotationSpec convertAnnotationMirrorToAnnotationSpec(@NotNull AnnotationMirror annotationMirror) {
      DeclaredType annotationType = annotationMirror.getAnnotationType();
      com.palantir.javapoet.AnnotationSpec.Builder builder = AnnotationSpec.builder((ClassName)ClassName.get(annotationType));
      annotationMirror.getElementValues().forEach((element, value) -> {
         builder.addMember(element.getSimpleName().toString(), "$L", new Object[]{value.getValue()});
      });
      return builder.build();
   }
}
