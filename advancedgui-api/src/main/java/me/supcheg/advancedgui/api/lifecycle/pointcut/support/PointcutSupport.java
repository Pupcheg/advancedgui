package me.supcheg.advancedgui.api.lifecycle.pointcut.support;

import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.api.lifecycle.pointcut.Pointcut;
import me.supcheg.advancedgui.code.RecordInterface;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

@RecordInterface
public interface PointcutSupport extends Buildable<PointcutSupport, PointcutSupportBuilder> {

    static PointcutSupport simplePointcutSupport(Set<Pointcut> pointcuts) {
        return new PointcutSupportImpl(Set.copyOf(pointcuts));
    }

    static PointcutSupportBuilder pointcutSupport() {
        return new PointcutSupportBuilderImpl();
    }

    static PointcutSupport pointcutSupport(Consumer<PointcutSupportBuilder> consumer) {
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
}
