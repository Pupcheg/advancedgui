package me.supcheg.advancedgui.platform.paper.gui;

import me.supcheg.advancedgui.api.gui.Gui;
import me.supcheg.advancedgui.api.gui.background.Background;
import me.supcheg.advancedgui.api.gui.template.GuiTemplate;
import me.supcheg.advancedgui.api.lifecycle.LifecycleListenerRegistry;
import me.supcheg.advancedgui.platform.paper.lifecycle.DefaultLifecycled;
import me.supcheg.advancedgui.platform.paper.view.GuiViewer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;

import static me.supcheg.advancedgui.api.lifecycle.pointcut.TickPointcut.afterTickPointcut;
import static me.supcheg.advancedgui.api.lifecycle.pointcut.TickPointcut.beforeTickPointcut;

public record GuiImpl(
        Key key,
        Background background,
        LayoutImpl<?> layout,
        LifecycleListenerRegistry<Gui> lifecycleListenerRegistry,
        GuiViewer viewer,
        GuiTemplate source
) implements Gui, DefaultLifecycled<Gui> {

    @Override
    public void open(Audience audience) {
        viewer.open(audience, this);
    }

    public void tick() {
        handleEachLifecycleAction(beforeTickPointcut());

        layout.tick();

        handleEachLifecycleAction(afterTickPointcut());
    }
}
