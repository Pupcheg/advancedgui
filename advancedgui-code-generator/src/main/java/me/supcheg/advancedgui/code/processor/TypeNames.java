package me.supcheg.advancedgui.code.processor;

import com.palantir.javapoet.ArrayTypeName;
import com.palantir.javapoet.ClassName;
import com.palantir.javapoet.ParameterizedTypeName;
import com.palantir.javapoet.TypeName;
import com.palantir.javapoet.TypeVariableName;
import com.palantir.javapoet.WildcardTypeName;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ErrorType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleTypeVisitor14;
import java.util.List;
import java.util.function.UnaryOperator;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class TypeNames {
    static TypeName rename(TypeMirror typeMirror, UnaryOperator<String> nameModifier) {
        return rename(TypeName.get(typeMirror), nameModifier);
    }

    static TypeName rename(TypeName source, UnaryOperator<String> nameModifier) {
        return switch (source) {
            case ClassName className -> rename(className, nameModifier);
            case ParameterizedTypeName parameterizedTypeName -> rename(parameterizedTypeName, nameModifier);
            case TypeVariableName typeVariableName -> rename(typeVariableName, nameModifier);
            case ArrayTypeName arrayTypeName -> rename(arrayTypeName, nameModifier);
            case WildcardTypeName wildcardTypeName -> rename(wildcardTypeName, nameModifier);
            default -> throw new IllegalStateException("Unexpected value: " + source);
        };
    }

    static ClassName rename(ClassName source, UnaryOperator<String> nameModifier) {
        return ClassName.get(source.packageName(), nameModifier.apply(source.simpleName()));
    }

    static ParameterizedTypeName rename(ParameterizedTypeName source, UnaryOperator<String> nameModifier) {
        return ParameterizedTypeName.get(rename(source.rawType(), nameModifier), source.typeArguments().toArray(TypeName[]::new));
    }

    static TypeVariableName rename(TypeVariableName source, UnaryOperator<String> nameModifier) {
        return TypeVariableName.get(nameModifier.apply(source.name()), source.bounds().toArray(TypeName[]::new));
    }

    @SuppressWarnings("DeprecatedIsStillUsed")
    @Deprecated
    static WildcardTypeName rename(WildcardTypeName source, UnaryOperator<String> nameModifier) {
        return source;
    }

    @SuppressWarnings("DeprecatedIsStillUsed")
    @Deprecated
    static ArrayTypeName rename(ArrayTypeName source, UnaryOperator<String> nameModifier) {
        return source;
    }

    static GenericTypes genericTypes(TypeName source) {
        return switch (source) {
            case ClassName clazz -> new GenericTypes(clazz, List.of());
            case ParameterizedTypeName param -> new GenericTypes(
                    param.rawType(),
                    param.typeArguments().stream()
                            .filter(TypeVariableName.class::isInstance)
                            .map(TypeVariableName.class::cast)
                            .toList()
            );
            default -> throw new IllegalStateException("Unexpected value: " + source);
        };
    }

    record GenericTypes(
            ClassName raw,
            List<TypeVariableName> generics
    ) {
    }

    static String simpleName(TypeName typename) {
        return switch (typename) {
            case ClassName className -> className.simpleName();
            case ParameterizedTypeName parameterizedTypeName -> parameterizedTypeName.rawType().simpleName();
            default -> throw new IllegalStateException("Unexpected value: " + typename);
        };
    }

    static boolean isSameType(TypeMirror typeMirror, TypeName typename) {
        return IsSameType.INSTANCE.visit(typeMirror, typename);
    }

    private static class IsSameType extends SimpleTypeVisitor14<Boolean, TypeName> {
        private static final IsSameType INSTANCE = new IsSameType();

        private IsSameType() {
            super(true);
        }

        @Override
        public Boolean visitDeclared(DeclaredType type, TypeName typename) {
            return areSimpleNamesEqual(type, erasure(typename))
                   && (
                           !(typename instanceof ParameterizedTypeName parameterizedTypename)
                           || areEqual(type.getTypeArguments(), parameterizedTypename.typeArguments())
                   );
        }

        private boolean areEqual(List<? extends TypeMirror> types, List<? extends TypeName> typenames) {
            if (types.size() != typenames.size()) {
                return false;
            }

            for (int i = 0; i < types.size(); i++) {
                var type = types.get(i);
                var typename = typenames.get(i);
                if (!visit(type, typename)) {
                    return false;
                }
            }

            return true;
        }

        @Override
        public Boolean visitError(ErrorType type, TypeName typename) {
            return areSimpleNamesEqual(type, erasure(typename));
        }

        private static boolean areSimpleNamesEqual(DeclaredType type, ClassName classname) {
            var typeSimplename = type.asElement().getSimpleName();
            return typeSimplename.contentEquals("<any>")
                   || typeSimplename.contentEquals(classname.simpleName());
        }

        private static ClassName erasure(TypeName typename) {
            return switch (typename) {
                case ClassName classname -> classname;
                case ParameterizedTypeName parameterizedTypename -> parameterizedTypename.rawType();
                default -> throw new IllegalStateException("Unexpected value: " + typename);
            };
        }
    }
}
