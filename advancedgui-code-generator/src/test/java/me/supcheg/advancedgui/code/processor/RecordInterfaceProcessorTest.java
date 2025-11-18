package me.supcheg.advancedgui.code.processor;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;

class RecordInterfaceProcessorTest {

    @MonotonicNonNull
    static Compilation compilation;

    @BeforeAll
    static void setup() {
        compilation = javac()
                .withProcessors(new CodeGeneratorProcessor())
                .compile(
                        JavaFileObjects.forSourceString(
                                "Template",
                                """
                                        import me.supcheg.advancedgui.code.RecordInterface;
                                        import org.jetbrains.annotations.Unmodifiable;
                                        import net.kyori.adventure.key.Key;
                                        import me.supcheg.advancedgui.api.builder.Buildable;
                                        import java.util.List;
                                        import java.util.function.Consumer;
                                        
                                        @RecordInterface
                                        public interface Template extends Buildable<Template, TemplateBuilder> {
                                        
                                            static TemplateBuilder template() {
                                                return new TemplateBuilderImpl();
                                            }
                                        
                                            static Template template(Consumer<TemplateBuilder> consumer) {
                                                return Buildable.configureAndBuild(template(), consumer);
                                            }
                                        
                                            Key key();
                                        
                                            @Unmodifiable
                                            List<Key> subkeys();
                                        
                                            int value();
                                        }
                                        """)
                );
    }

    @Test
    void whenCompiled_thenNoWarningsPresent() {
        assertThat(compilation)
                .succeededWithoutWarnings();
    }

    @Test
    void whenGettingBuilderInterface_thenExpectedContentPresent() {
        assertThat(compilation)
                .generatedSourceFile("TemplateBuilder")
                .contentsAsUtf8String()
                .isEqualTo("""
                        import java.util.List;
                        import java.util.Objects;
                        import me.supcheg.advancedgui.api.builder.AbstractBuilder;
                        import net.kyori.adventure.key.Key;
                        import org.checkerframework.checker.nullness.qual.NonNull;
                        import org.checkerframework.checker.nullness.qual.Nullable;
                        
                        public interface TemplateBuilder extends AbstractBuilder<Template> {
                          @NonNull
                          TemplateBuilder key(@NonNull Key key);
                        
                          @NonNull
                          TemplateBuilder subkeys(@NonNull List<Key> subkeys);
                        
                          @NonNull
                          default TemplateBuilder subkey(@NonNull Key subkey) {
                            Objects.requireNonNull(subkey, "subkey");
                            return subkeys(List.of(subkey));
                          }
                        
                          @NonNull
                          TemplateBuilder addSubkeys(@NonNull List<Key> subkeys);
                        
                          @NonNull
                          default TemplateBuilder addSubkey(@NonNull Key subkey) {
                            Objects.requireNonNull(subkey, "subkey");
                            return addSubkeys(List.of(subkey));
                          }
                        
                          @NonNull
                          TemplateBuilder value(int value);
                        
                          @Nullable
                          Key key();
                        
                          @NonNull
                          List<Key> subkeys();
                        
                          @Nullable
                          Integer value();
                        
                          @NonNull
                          @Override
                          Template build();
                        }
                        """);
    }

    @Test
    void whenGettingObjectImpl_thenExpectedContentPresent() {
        assertThat(compilation)
                .generatedSourceFile("TemplateImpl")
                .contentsAsUtf8String()
                .isEqualTo("""
                        import java.util.List;
                        import net.kyori.adventure.key.Key;
                        import org.checkerframework.checker.nullness.qual.NonNull;
                        import org.jetbrains.annotations.Unmodifiable;
                        
                        record TemplateImpl(@NonNull Key key, @Unmodifiable @NonNull List<Key> subkeys,
                            int value) implements Template {
                          @NonNull
                          @Override
                          public TemplateBuilderImpl toBuilder() {
                            return new TemplateBuilderImpl(this);
                          }
                        }
                        """);
    }

    @Test
    void whenGettingBuilderImpl_thenExpectedContentPresent() {
        assertThat(compilation)
                .generatedSourceFile("TemplateBuilderImpl")
                .contentsAsUtf8String()
                .isEqualTo("""
                        import java.util.ArrayList;
                        import java.util.List;
                        import java.util.Objects;
                        import net.kyori.adventure.key.Key;
                        import org.checkerframework.checker.nullness.qual.NonNull;
                        import org.checkerframework.checker.nullness.qual.Nullable;
                        
                        class TemplateBuilderImpl implements TemplateBuilder {
                          @Nullable
                          private Key key;
                        
                          @NonNull
                          private final List<Key> subkeys;
                        
                          @Nullable
                          private Integer value;
                        
                          TemplateBuilderImpl() {
                            this.subkeys = new ArrayList<>();
                          }
                        
                          TemplateBuilderImpl(@NonNull TemplateImpl impl) {
                            this.key = impl.key();
                            this.subkeys = new ArrayList<>(impl.subkeys());
                            this.value = impl.value();
                          }
                        
                          @NonNull
                          @Override
                          public TemplateBuilderImpl key(@NonNull Key key) {
                            Objects.requireNonNull(key, "key");
                            this.key = key;
                            return this;
                          }
                        
                          @NonNull
                          @Override
                          public TemplateBuilderImpl subkeys(@NonNull List<Key> subkeys) {
                            Objects.requireNonNull(subkeys, "subkeys");
                            this.subkeys.clear();
                            this.subkeys.addAll(subkeys);
                            return this;
                          }
                        
                          @NonNull
                          @Override
                          public TemplateBuilderImpl addSubkeys(@NonNull List<Key> subkeys) {
                            Objects.requireNonNull(subkeys, "subkeys");
                            this.subkeys.addAll(subkeys);
                            return this;
                          }
                        
                          @NonNull
                          @Override
                          public TemplateBuilderImpl value(int value) {
                            this.value = value;
                            return this;
                          }
                        
                          @Nullable
                          @Override
                          public Key key() {
                            return key;
                          }
                        
                          @NonNull
                          @Override
                          public List<Key> subkeys() {
                            return subkeys;
                          }
                        
                          @Nullable
                          @Override
                          public Integer value() {
                            return value;
                          }
                        
                          @NonNull
                          @Override
                          public TemplateImpl build() {
                            return new TemplateImpl(
                                Objects.requireNonNull(this.key, "key"),
                                List.copyOf(this.subkeys),
                                this.value
                            );
                          }
                        }
                        """);
    }
}
