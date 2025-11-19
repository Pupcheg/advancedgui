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
                                "me.supcheg.advancedgui.api.builder.AbstractBuilder",
                                """
                                        package me.supcheg.advancedgui.api.builder;
                                        
                                        import net.kyori.adventure.util.Buildable;
                                        
                                        @SuppressWarnings("deprecation")
                                        public interface AbstractBuilder<R> extends net.kyori.adventure.builder.AbstractBuilder<R>, Buildable.Builder<R> {
                                        }
                                        """
                        ),
                        JavaFileObjects.forSourceString(
                                "me.supcheg.advancedgui.api.builder.Buildable",
                                """
                                        package me.supcheg.advancedgui.api.builder;
                                        
                                        import java.util.Objects;
                                        import java.util.function.Consumer;
                                        
                                        public interface Buildable<R, B extends AbstractBuilder<R>> extends net.kyori.adventure.util.Buildable<R, B> {
                                        
                                            static <R, B extends AbstractBuilder<R>> R configureAndBuild(B builder, Consumer<? super B> consumer) {
                                                Objects.requireNonNull(builder, "builder");
                                                consumer.accept(builder);
                                                return builder.build();
                                            }
                                        
                                        }
                                        """
                        ),
                        JavaFileObjects.forSourceString(
                                "Template",
                                """
                                        import me.supcheg.advancedgui.code.RecordInterface;
                                        import org.jetbrains.annotations.Unmodifiable;
                                        import net.kyori.adventure.key.Key;
                                        import me.supcheg.advancedgui.api.builder.Buildable;
                                        import java.util.List;
                                        import java.util.Map;
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
                                        
                                            SubTemplate subtemplate();
                                        
                                            @Unmodifiable
                                            Map<Key, KeyedString> keyedStrings();
                                        }
                                        """),
                        JavaFileObjects.forSourceString(
                                "SubTemplate",
                                """
                                        import me.supcheg.advancedgui.code.RecordInterface;
                                        import org.jetbrains.annotations.Unmodifiable;
                                        import me.supcheg.advancedgui.api.builder.Buildable;
                                        import java.util.Set;
                                        import java.util.function.Consumer;
                                        
                                        @RecordInterface
                                        public interface SubTemplate extends Buildable<SubTemplate, SubTemplateBuilder> {
                                        
                                            static SubTemplateBuilder subTemplate() {
                                                return new SubTemplateBuilderImpl();
                                            }
                                        
                                            static SubTemplate subTemplate(Consumer<SubTemplateBuilder> consumer) {
                                                return Buildable.configureAndBuild(subTemplate(), consumer);
                                            }
                                        
                                            @Unmodifiable
                                            Set<String> contents();
                                        }
                                        """
                        ),
                        JavaFileObjects.forSourceString(
                                "KeyedString",
                                """
                                        import me.supcheg.advancedgui.code.RecordInterface;
                                        import me.supcheg.advancedgui.api.builder.Buildable;
                                        import net.kyori.adventure.key.Key;
                                        import java.util.function.Consumer;
                                        
                                        @RecordInterface
                                        public interface KeyedString extends Buildable<KeyedString, KeyedStringBuilder> {
                                        
                                            static KeyedStringBuilder keyedString() {
                                                return new KeyedStringBuilderImpl();
                                            }
                                        
                                            static KeyedString keyedString(Consumer<KeyedStringBuilder> consumer) {
                                                return Buildable.configureAndBuild(keyedString(), consumer);
                                            }
                                        
                                            Key key();
                                        
                                            String string();
                                        }
                                        """
                        )
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
                        import java.util.Map;
                        import java.util.function.Consumer;
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
                            TemplateBuilder subkey(@NonNull Key subkey);
                        
                            @NonNull
                            TemplateBuilder addSubkeys(@NonNull List<Key> subkeys);
                        
                            @NonNull
                            TemplateBuilder addSubkey(@NonNull Key subkey);
                        
                            @NonNull
                            TemplateBuilder value(int value);
                        
                            @NonNull
                            TemplateBuilder subtemplate(@NonNull SubTemplate subtemplate);
                        
                            @NonNull
                            default TemplateBuilder subtemplate(@NonNull Consumer<SubTemplateBuilder> subtemplate) {
                                return subtemplate(SubTemplate.subTemplate(subtemplate));
                            }
                        
                            @NonNull
                            TemplateBuilder keyedStrings(@NonNull Map<Key, KeyedString> keyedStrings);
                        
                            @NonNull
                            TemplateBuilder putKeyedStrings(@NonNull Map<Key, KeyedString> keyedStrings);
                        
                            @NonNull
                            TemplateBuilder putKeyedString(@NonNull Key key, @NonNull KeyedString keyedString);
                        
                            @NonNull
                            default TemplateBuilder addKeyedString(@NonNull KeyedString keyedString) {
                                return putKeyedString(keyedString.key(), keyedString);
                            }
                        
                            @NonNull
                            default TemplateBuilder addKeyedString(@NonNull Consumer<KeyedStringBuilder> keyedString) {
                                return addKeyedString(KeyedString.keyedString(keyedString));
                            }
                        
                            @Nullable
                            Key key();
                        
                            @NonNull
                            List<Key> subkeys();
                        
                            @Nullable
                            Integer value();
                        
                            @Nullable
                            SubTemplate subtemplate();
                        
                            @NonNull
                            Map<Key, KeyedString> keyedStrings();
                        
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
                        import java.util.Map;
                        import net.kyori.adventure.key.Key;
                        import org.checkerframework.checker.nullness.qual.NonNull;
                        import org.jetbrains.annotations.Unmodifiable;
                        
                        record TemplateImpl(@NonNull Key key, @Unmodifiable @NonNull List<Key> subkeys, int value,
                                @NonNull SubTemplate subtemplate,
                                @Unmodifiable @NonNull Map<Key, KeyedString> keyedStrings) implements Template {
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
                        import java.util.HashMap;
                        import java.util.List;
                        import java.util.Map;
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
                        
                            @Nullable
                            private SubTemplate subtemplate;
                        
                            @NonNull
                            private final Map<Key, KeyedString> keyedStrings;
                        
                            TemplateBuilderImpl() {
                                this.subkeys = new ArrayList<>();
                                this.keyedStrings = new HashMap<>();
                            }
                        
                            TemplateBuilderImpl(@NonNull TemplateImpl impl) {
                                this.key = impl.key();
                                this.subkeys = new ArrayList<>(impl.subkeys());
                                this.value = impl.value();
                                this.subtemplate = impl.subtemplate();
                                this.keyedStrings = new HashMap<>(impl.keyedStrings());
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
                            public TemplateBuilderImpl subkey(@NonNull Key subkey) {
                                Objects.requireNonNull(subkey, "subkey");
                                this.subkeys.clear();
                                this.subkeys.add(subkey);
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
                            public TemplateBuilderImpl addSubkey(@NonNull Key subkey) {
                                Objects.requireNonNull(subkey, "subkey");
                                this.subkeys.add(subkey);
                                return this;
                            }
                        
                            @NonNull
                            @Override
                            public TemplateBuilderImpl value(int value) {
                                this.value = value;
                                return this;
                            }
                        
                            @NonNull
                            @Override
                            public TemplateBuilderImpl subtemplate(@NonNull SubTemplate subtemplate) {
                                Objects.requireNonNull(subtemplate, "subtemplate");
                                this.subtemplate = subtemplate;
                                return this;
                            }
                        
                            @NonNull
                            @Override
                            public TemplateBuilderImpl keyedStrings(@NonNull Map<Key, KeyedString> keyedStrings) {
                                Objects.requireNonNull(keyedStrings, "keyedStrings");
                                this.keyedStrings.clear();
                                this.keyedStrings.putAll(keyedStrings);
                                return this;
                            }
                        
                            @NonNull
                            @Override
                            public TemplateBuilderImpl putKeyedStrings(@NonNull Map<Key, KeyedString> keyedStrings) {
                                Objects.requireNonNull(keyedStrings, "keyedStrings");
                                this.keyedStrings.putAll(keyedStrings);
                                return this;
                            }
                        
                            @NonNull
                            @Override
                            public TemplateBuilderImpl putKeyedString(@NonNull Key key, @NonNull KeyedString keyedString) {
                                Objects.requireNonNull(key, "key");
                                Objects.requireNonNull(keyedString, "keyedString");
                                this.keyedStrings.put(key, keyedString);
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
                        
                            @Nullable
                            @Override
                            public SubTemplate subtemplate() {
                                return subtemplate;
                            }
                        
                            @NonNull
                            @Override
                            public Map<Key, KeyedString> keyedStrings() {
                                return keyedStrings;
                            }
                        
                            @NonNull
                            @Override
                            public TemplateImpl build() {
                                return new TemplateImpl(
                                        Objects.requireNonNull(this.key, "key"),
                                        List.copyOf(this.subkeys),
                                        this.value,
                                        Objects.requireNonNull(this.subtemplate, "subtemplate"),
                                        Map.copyOf(this.keyedStrings)
                                );
                            }
                        }
                        """);
    }
}
