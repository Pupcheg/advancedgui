package me.supcheg.advancedgui.platform.paper.construct;

import lombok.RequiredArgsConstructor;
import me.supcheg.advancedgui.api.gui.template.GuiTemplate;
import me.supcheg.advancedgui.api.layout.template.LayoutTemplate;
import me.supcheg.advancedgui.api.lifecycle.pointcut.ObjectPointcut;
import me.supcheg.advancedgui.platform.paper.gui.GuiImpl;
import me.supcheg.advancedgui.platform.paper.gui.LayoutImpl;
import me.supcheg.advancedgui.platform.paper.view.GuiViewer;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class GuiImplConstructor implements TemplateConstructor<GuiTemplate, GuiImpl> {
    private final TemplateConstructor<LayoutTemplate<?, ?, ?>, LayoutImpl<?>> layoutConstructor;
    private final GuiViewer guiViewer;

    @NotNull
    @Override
    public GuiImpl construct(@NotNull GuiTemplate template) {
        var gui = new GuiImpl(
                template.key(),
                template.background(),
                layoutConstructor.construct(template.layout()),
                template.lifecycleListenerRegistry(),
                guiViewer,
                template
        );
        gui.handleEachLifecycleAction(ObjectPointcut.objectConstructPointcut());
        return gui;
    }
}
