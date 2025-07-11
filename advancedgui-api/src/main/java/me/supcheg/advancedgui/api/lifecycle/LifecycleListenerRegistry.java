package me.supcheg.advancedgui.api.lifecycle;

import com.google.common.collect.SortedMultiset;
import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.api.lifecycle.pointcut.Pointcut;
import net.kyori.examination.Examinable;
import net.kyori.examination.ExaminableProperty;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

public interface LifecycleListenerRegistry<S> extends Examinable, Buildable<LifecycleListenerRegistry<S>, LifecycleListenerRegistry.Builder<S>> {

    @SuppressWarnings("unchecked")
    static <S> LifecycleListenerRegistry<S> emptyLifecycleListenerRegistry() {
        return (LifecycleListenerRegistry<S>) LifecycleListenerRegistryImpl.EMPTY;
    }

    static <S> Builder<S> lifecycleListenerRegistry() {
        return new LifecycleListenerRegistryImpl.BuilderImpl<>();
    }

    static <S> LifecycleListenerRegistry<S> lifecycleListenerRegistry(Consumer<Builder<S>> consumer) {
        return Buildable.configureAndBuild(lifecycleListenerRegistry(), consumer);
    }

    @Unmodifiable
    SortedMultiset<LifecycleListener<S>> listeners(Pointcut pointcut);

    @Unmodifiable
    Map<Pointcut, SortedMultiset<LifecycleListener<S>>> listeners();

    @Override
    default Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(
                ExaminableProperty.of("listeners", listeners().values().stream().mapMulti(Iterable::forEach))
        );
    }

    interface Builder<S> extends AbstractBuilder<LifecycleListenerRegistry<S>> {

        Builder<S> add(LifecycleListener<S> listener);

        default Builder<S> add(Consumer<LifecycleListener.Builder<S>> consumer) {
            return add(LifecycleListener.lifecycleListener(consumer));
        }
    }
}
