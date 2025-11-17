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
                                        import net.kyori.adventure.key.Key;
                                        import java.util.List;
                                        
                                        @RecordInterface
                                        public interface TestTemplate {
                                            Key key();
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
                .generatedSourceFile("TestTemplateBuilder")
                .contentsAsUtf8String()
                .isEqualTo("""
                        import java.util.List;
                        import java.util.Objects;
                        import me.supcheg.advancedgui.api.builder.AbstractBuilder;
                        import net.kyori.adventure.key.Key;
                        
                        public interface TestTemplateBuilder extends AbstractBuilder<TestTemplate> {
                          TestTemplateBuilder key(Key key);
                        
                          TestTemplateBuilder subkeys(List<Key> subkeys);
                        
                          default TestTemplateBuilder subkey(Key subkey) {
                            Objects.requireNonNull(subkey, "subkey");
                            return subkeys(List.of(subkey));
                          }
                        
                          TestTemplateBuilder addSubkeys(List<Key> subkeys);
                        
                          default TestTemplateBuilder addSubkey(Key subkey) {
                            Objects.requireNonNull(subkey, "subkey");
                            return addSubkeys(List.of(subkey));
                          }
                        
                          TestTemplateBuilder value(int value);
                        
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
                        
                        record TestTemplateImpl(Key key, List<Key> subkeys, int value) implements TestTemplate {
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
                        import java.util.ArrayList;
                        import java.util.List;
                        import java.util.Objects;
                        import net.kyori.adventure.key.Key;
                        
                        class TestTemplateBuilderImpl implements TestTemplateBuilder {
                          private Key key;
                        
                          private final List<Key> subkeys;
                        
                          private Integer value;
                        
                          TestTemplateBuilderImpl() {
                            this.subkeys = new ArrayList<>();
                          }
                        
                          TestTemplateBuilderImpl(TestTemplateImpl impl) {
                            this.key = impl.key();
                            this.subkeys = new ArrayList<>(impl.subkeys());
                            this.value = impl.value();
                          }
                        
                          public TestTemplateBuilderImpl key(Key key) {
                            Objects.requireNonNull(key, "key");
                            this.key = key;
                            return this;
                          }
                        
                          public TestTemplateBuilderImpl subkeys(List<Key> subkeys) {
                            Objects.requireNonNull(subkeys, "subkeys");
                            this.subkeys.clear();
                            this.subkeys.addAll(subkeys);
                            return this;
                          }
                        
                          public TestTemplateBuilderImpl addSubkeys(List<Key> subkeys) {
                            Objects.requireNonNull(subkeys, "subkeys");
                            this.subkeys.addAll(subkeys);
                            return this;
                          }
                        
                          public TestTemplateBuilderImpl value(int value) {
                            this.value = value;
                            return this;
                          }
                        
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
