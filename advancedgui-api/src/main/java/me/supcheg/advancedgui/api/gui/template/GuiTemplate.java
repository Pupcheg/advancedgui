package me.supcheg.advancedgui.api.gui.template;

import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.api.gui.Gui;
import me.supcheg.advancedgui.api.gui.background.Background;
import me.supcheg.advancedgui.api.layout.Layout;
import me.supcheg.advancedgui.api.layout.template.LayoutTemplate;
import me.supcheg.advancedgui.api.lifecycle.Lifecycled;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import net.kyori.examination.Examinable;
import net.kyori.examination.ExaminableProperty;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.function.Consumer;
import java.util.stream.Stream;

public interface GuiTemplate extends Keyed, Examinable, Buildable<GuiTemplate, GuiTemplate.Builder>, Lifecycled<Gui> {

    static Builder gui() {
        return new GuiTemplateImpl.BuilderImpl();
    }

    static GuiTemplate gui(Consumer<GuiTemplate.Builder> consumer) {
        return Buildable.configureAndBuild(gui(), consumer);
    }

    Background background();

    LayoutTemplate<?, ?, ?> layout();

    @Override
    default Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(
                ExaminableProperty.of("key", key()),
                ExaminableProperty.of("background", background()),
                ExaminableProperty.of("layout", layout()),
                ExaminableProperty.of("lifecycleListenerRegistry", lifecycleListenerRegistry())
        );
    }

    interface Builder extends AbstractBuilder<GuiTemplate>, Lifecycled.Builder<Gui, Builder> {

        Builder key(Key key);

        @Nullable
        Key key();

        Builder layout(LayoutTemplate<?, ?, ?> layout);

        default <L extends Layout<L>, T extends LayoutTemplate<L, T, B>, B extends LayoutTemplate.Builder<L, T, B>> Builder layout(B builder, Consumer<B> consumer) {
            return layout(Buildable.configureAndBuild(builder, consumer));
        }

        default <L extends Layout<L>, T extends LayoutTemplate<L, T, B>, B extends LayoutTemplate.Builder<L, T, B>> Builder layout(B layout) {
            return layout(layout.build());
        }

        @Nullable
        LayoutTemplate<?, ?, ?> layout();

        default Builder background(Consumer<Background.Builder> consumer) {
            return background(Background.background(consumer));
        }

        Builder background(Background background);

        @Nullable
        Background background();
    }
}
