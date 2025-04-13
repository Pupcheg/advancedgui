package me.supcheg.advancedgui.api.lifecycle.pointcut.support;

import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.api.lifecycle.pointcut.Pointcut;
import me.supcheg.advancedgui.api.util.CollectionUtil;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public interface PointcutSupport extends Buildable<PointcutSupport, PointcutSupport.Builder> {

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    static PointcutSupport simplePointcutSupport(@NotNull Set<Pointcut> pointcuts) {
        return new PointcutSupportImpl(Set.copyOf(pointcuts));
    }

    @NotNull
    @Contract("-> new")
    static Builder pointcutSupport() {
        return new PointcutSupportImpl.BuilderImpl();
    }

    @NotNull
    @Contract("_ -> new")
    static PointcutSupport pointcutSupport(@NotNull Consumer<Builder> consumer) {
        return Buildable.configureAndBuild(pointcutSupport(), consumer);
    }

    @NotNull
    @Unmodifiable
    Set<Pointcut> supported();

    default boolean supports(@NotNull Pointcut pointcut) {
        return supported().contains(pointcut);
    }

    @NotNull
    default Set<Pointcut> findUnsupported(@NotNull Collection<Pointcut> pointcuts) {
        HashSet<Pointcut> unsupported = new HashSet<>(pointcuts);
        unsupported.removeAll(supported());
        return Collections.unmodifiableSet(unsupported);
    }

    interface Builder extends AbstractBuilder<PointcutSupport> {

        @NotNull
        @Contract("_ -> this")
        Builder supports(@NotNull Pointcut pointcut);

        @NotNull
        @Contract("_ -> this")
        Builder supports(@NotNull Collection<Pointcut> pointcuts);

        @NotNull
        @Contract("_, _, _ -> this")
        default Builder supports(@NotNull Pointcut first, @NotNull Pointcut second, @NotNull Pointcut @NotNull ... other) {
            return supports(CollectionUtil.makeNoNullsList(first, second, other));
        }
    }
}
