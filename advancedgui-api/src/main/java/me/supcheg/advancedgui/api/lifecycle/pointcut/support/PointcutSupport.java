package me.supcheg.advancedgui.api.lifecycle.pointcut.support;

import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.api.lifecycle.pointcut.Pointcut;
import me.supcheg.advancedgui.api.util.CollectionUtil;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public interface PointcutSupport extends Buildable<PointcutSupport, PointcutSupport.Builder> {

    static PointcutSupport simplePointcutSupport(Set<Pointcut> pointcuts) {
        return new PointcutSupportImpl(Set.copyOf(pointcuts));
    }

    static Builder pointcutSupport() {
        return new PointcutSupportImpl.BuilderImpl();
    }

    static PointcutSupport pointcutSupport(Consumer<Builder> consumer) {
        return Buildable.configureAndBuild(pointcutSupport(), consumer);
    }

    @Unmodifiable
    Set<Pointcut> supported();

    default boolean supports(Pointcut pointcut) {
        return supported().contains(pointcut);
    }

    default Set<Pointcut> findUnsupported(Collection<Pointcut> pointcuts) {
        HashSet<Pointcut> unsupported = new HashSet<>(pointcuts);
        unsupported.removeAll(supported());
        return Collections.unmodifiableSet(unsupported);
    }

    interface Builder extends AbstractBuilder<PointcutSupport> {

        Builder supports(Pointcut pointcut);

        Builder supports(Collection<Pointcut> pointcuts);

        default Builder supports(Pointcut first, Pointcut second, Pointcut ... other) {
            return supports(CollectionUtil.makeNoNullsList(first, second, other));
        }
    }
}
