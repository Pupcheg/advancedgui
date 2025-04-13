package me.supcheg.advancedgui.api.component.guava;

import com.google.common.cache.CacheBuilder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GuavaComponentRendererCacheProviders {
    @NotNull
    @Contract("_ -> new")
    public static  GuavaComponentRendererCacheProvider guava(@NotNull Consumer<CacheBuilder<Object, Object>> configurer) {
        return () -> {
            var builder = CacheBuilder.newBuilder();
            configurer.accept(builder);
            return builder.build();
        };
    }
}
