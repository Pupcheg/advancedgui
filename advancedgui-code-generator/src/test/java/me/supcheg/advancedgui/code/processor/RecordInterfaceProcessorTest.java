package me.supcheg.advancedgui.code.processor;

import com.google.testing.compile.JavaFileObjects;
import org.junit.jupiter.api.Test;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;

class RecordInterfaceProcessorTest {
    @Test
    void test() {
        var compilation =
                javac().withProcessors(new RecordInterfaceProcessor())
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

        assertThat(compilation)
                .succeededWithoutWarnings();

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
}
