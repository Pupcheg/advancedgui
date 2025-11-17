package me.supcheg.advancedgui.api.gui.template;

import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.api.gui.Gui;
import me.supcheg.advancedgui.api.gui.background.Background;
import me.supcheg.advancedgui.api.layout.Layout;
import me.supcheg.advancedgui.api.layout.template.LayoutTemplate;
import me.supcheg.advancedgui.api.lifecycle.LifecycleListenerRegistry;
import me.supcheg.advancedgui.api.lifecycle.Lifecycled;
import me.supcheg.advancedgui.code.RecordInterface;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.function.Consumer;

@RecordInterface
public interface GuiTemplate extends Keyed, Buildable<GuiTemplate, GuiTemplate.Builder>, Lifecycled<Gui> {

    static Builder gui() {
        throw new UnsupportedOperationException();
    }

    static GuiTemplate gui(Consumer<GuiTemplate.Builder> consumer) {
        return Buildable.configureAndBuild(gui(), consumer);
    }

    @Override
    Key key();

    Background background();

    LayoutTemplate<?, ?, ?> layout();

    @Override
    LifecycleListenerRegistry<Gui> lifecycleListenerRegistry();

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
