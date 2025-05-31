package me.supcheg.advancedgui.api.lifecycle.pointcut.support;

import me.supcheg.advancedgui.api.lifecycle.pointcut.Pointcut;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

record PointcutSupportImpl(
        Set<Pointcut> supported
) implements PointcutSupport {

    @Override
    public PointcutSupport.Builder toBuilder() {
        return new BuilderImpl(this);
    }

    static class BuilderImpl implements Builder {
        private final Set<Pointcut> pointcuts;

        BuilderImpl() {
            this.pointcuts = new HashSet<>();
        }

        BuilderImpl(PointcutSupportImpl impl) {
            this.pointcuts = new HashSet<>(impl.supported);
        }

        @Override
        public Builder supports(Pointcut pointcut) {
            Objects.requireNonNull(pointcut, "pointcut");
            pointcuts.add(pointcut);
            return this;
        }

        @Override
        public Builder supports(Collection<Pointcut> pointcuts) {
            Objects.requireNonNull(pointcuts, "pointcuts");
            this.pointcuts.addAll(pointcuts);
            return this;
        }

        @Override
        public PointcutSupport build() {
            return new PointcutSupportImpl(
                    Set.copyOf(pointcuts)
            );
        }
    }
}
