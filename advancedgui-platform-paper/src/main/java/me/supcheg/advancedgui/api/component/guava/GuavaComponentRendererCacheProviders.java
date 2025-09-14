package me.supcheg.advancedgui.api.component.guava;

import com.google.common.cache.CacheBuilder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.function.Consumer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GuavaComponentRendererCacheProviders {

    public static GuavaComponentRendererCacheProvider guava(Consumer<CacheBuilder<Object, Object>> configurer) {
        return () -> {
            var builder = CacheBuilder.newBuilder();
            configurer.accept(builder);
            return builder.build();
        };
    }

}
