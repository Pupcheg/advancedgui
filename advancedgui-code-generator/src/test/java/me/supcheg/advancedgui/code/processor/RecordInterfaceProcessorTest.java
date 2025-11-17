package me.supcheg.advancedgui.code.processor;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;

class RecordInterfaceProcessorTest {

    @MonotonicNonNull
    Compilation compilation;

    @BeforeEach
    void setup() {
        compilation = javac()
                .withProcessors(new RecordInterfaceProcessor())
                .compile(
                        JavaFileObjects.forSourceString(
                                "TestTemplate",
                                """
                                        import me.supcheg.advancedgui.code.RecordInterface;
                                        import net.kyori.adventure.key.Key;import org.jetbrains.annotations.Unmodifiable;
                                        import java.util.List;
                                        
                                        @RecordInterface
                                        public interface TestTemplate {
                                            Key key();
                                            @Unmodifiable List<Key> subkeys();
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
                .generatedSourceFile("TestTemplateBuilder")
                .contentsAsUtf8String()
                .isEqualTo("""
                        import java.lang.Override;
                        import java.util.List;
                        import java.util.Objects;
                        import me.supcheg.advancedgui.api.builder.AbstractBuilder;
                        import net.kyori.adventure.key.Key;
                        import org.checkerframework.checker.nullness.qual.NonNull;
                        
                        public interface TestTemplateBuilder extends AbstractBuilder<TestTemplate> {
                          @NonNull
                          TestTemplateBuilder key(@NonNull Key key);
                        
                          @NonNull
                          TestTemplateBuilder subkeys(@NonNull List<Key> subkeys);
                        
                          @NonNull
                          default TestTemplateBuilder subkey(@NonNull Key subkey) {
                            Objects.requireNonNull(subkey, "subkey");
                            return subkeys(List.of(subkey));
                          }
                        
                          @NonNull
                          TestTemplateBuilder addSubkeys(@NonNull List<Key> subkeys);
                        
                          @NonNull
                          default TestTemplateBuilder addSubkey(@NonNull Key subkey) {
                            Objects.requireNonNull(subkey, "subkey");
                            return addSubkeys(List.of(subkey));
                          }
                        
                          @NonNull
                          TestTemplateBuilder value(int value);
                        
                          @NonNull
                          @Override
                          TestTemplate build();
                        }
                        """);
    }

    @Test
    void whenGettingObjectImpl_thenExpectedContentPresent() {
        assertThat(compilation)
                .generatedSourceFile("TestTemplateImpl")
                .contentsAsUtf8String()
                .isEqualTo("""
                        import java.util.List;
                        import net.kyori.adventure.key.Key;
                        import org.checkerframework.checker.nullness.qual.NonNull;
                        import org.jetbrains.annotations.Unmodifiable;
                        
                        record TestTemplateImpl(@NonNull Key key, @Unmodifiable @NonNull List<Key> subkeys,
                            int value) implements TestTemplate {
                        }
                        """);
    }

    @Test
    void whenGettingBuilderImpl_thenExpectedContentPresent() {
        assertThat(compilation)
                .generatedSourceFile("TestTemplateBuilderImpl")
                .contentsAsUtf8String()
                .isEqualTo("""
                        import java.lang.Integer;
                        import java.lang.Override;
                        import java.util.ArrayList;
                        import java.util.List;
                        import java.util.Objects;
                        import net.kyori.adventure.key.Key;
                        import org.checkerframework.checker.nullness.qual.NonNull;
                        import org.checkerframework.checker.nullness.qual.Nullable;
                        
                        class TestTemplateBuilderImpl implements TestTemplateBuilder {
                          @Nullable
                          private Key key;
                        
                          @NonNull
                          private final List<Key> subkeys;
                        
                          @Nullable
                          private Integer value;
                        
                          TestTemplateBuilderImpl() {
                            this.subkeys = new ArrayList<>();
                          }
                        
                          TestTemplateBuilderImpl(@NonNull TestTemplateImpl impl) {
                            this.key = impl.key();
                            this.subkeys = new ArrayList<>(impl.subkeys());
                            this.value = impl.value();
                          }
                        
                          @NonNull
                          @Override
                          public TestTemplateBuilderImpl key(@NonNull Key key) {
                            Objects.requireNonNull(key, "key");
                            this.key = key;
                            return this;
                          }
                        
                          @NonNull
                          @Override
                          public TestTemplateBuilderImpl subkeys(@NonNull List<Key> subkeys) {
                            Objects.requireNonNull(subkeys, "subkeys");
                            this.subkeys.clear();
                            this.subkeys.addAll(subkeys);
                            return this;
                          }
                        
                          @NonNull
                          @Override
                          public TestTemplateBuilderImpl addSubkeys(@NonNull List<Key> subkeys) {
                            Objects.requireNonNull(subkeys, "subkeys");
                            this.subkeys.addAll(subkeys);
                            return this;
                          }
                        
                          @NonNull
                          @Override
                          public TestTemplateBuilderImpl value(int value) {
                            this.value = value;
                            return this;
                          }
                        
                          @NonNull
                          @Override
                          public TestTemplateImpl build() {
                            return new TestTemplateImpl(
                                Objects.requireNonNull(this.key, "key"),
                                List.copyOf(this.subkeys),
                                this.value
                            );
                          }
                        }
                        """);
    }
}
