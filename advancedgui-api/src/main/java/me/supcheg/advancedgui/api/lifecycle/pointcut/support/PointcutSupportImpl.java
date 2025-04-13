package me.supcheg.advancedgui.api.lifecycle.pointcut.support;

import me.supcheg.advancedgui.api.lifecycle.pointcut.Pointcut;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

record PointcutSupportImpl(
        @NotNull Set<Pointcut> supported
) implements PointcutSupport {
    @NotNull
    @Override
    public PointcutSupport.Builder toBuilder() {
        return new BuilderImpl(this);
    }

    static class BuilderImpl implements Builder {
        private final Set<Pointcut> pointcuts;

        BuilderImpl() {
            this.pointcuts = new HashSet<>();
        }

        BuilderImpl(@NotNull PointcutSupportImpl impl) {
            this.pointcuts = new HashSet<>(impl.supported);
        }

        @NotNull
        @Override
        public Builder supports(@NotNull Pointcut pointcut) {
            Objects.requireNonNull(pointcut, "pointcut");
            pointcuts.add(pointcut);
            return this;
        }

        @NotNull
        @Override
        public Builder supports(@NotNull Collection<Pointcut> pointcuts) {
            Objects.requireNonNull(pointcuts, "pointcuts");
            this.pointcuts.addAll(pointcuts);
            return this;
        }

        @NotNull
        @Override
        public PointcutSupport build() {
            return new PointcutSupportImpl(
                    Set.copyOf(pointcuts)
            );
        }
    }
}
