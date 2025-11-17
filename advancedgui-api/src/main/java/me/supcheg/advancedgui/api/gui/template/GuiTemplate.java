package me.supcheg.advancedgui.api.gui.template;

import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.api.gui.Gui;
import me.supcheg.advancedgui.api.gui.background.Background;
import me.supcheg.advancedgui.api.layout.template.LayoutTemplate;
import me.supcheg.advancedgui.api.lifecycle.LifecycleListenerRegistry;
import me.supcheg.advancedgui.api.lifecycle.Lifecycled;
import me.supcheg.advancedgui.code.RecordInterface;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;

import java.util.function.Consumer;

@RecordInterface
public interface GuiTemplate extends Keyed, Buildable<GuiTemplate, GuiTemplateBuilder>, Lifecycled<Gui> {

    static GuiTemplateBuilder gui() {
        return new GuiTemplateBuilderImpl();
    }

    static GuiTemplate gui(Consumer<GuiTemplateBuilder> consumer) {
        return Buildable.configureAndBuild(gui(), consumer);
    }

    @Override
    Key key();

    Background background();

    LayoutTemplate<?, ?, ?> layout();

    @Override
    LifecycleListenerRegistry<Gui> lifecycleListenerRegistry();
}
