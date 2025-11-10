package me.supcheg.advancedgui.platform.paper.construct;

import me.supcheg.advancedgui.api.button.template.ButtonTemplate;
import me.supcheg.advancedgui.api.lifecycle.pointcut.ObjectPointcut;
import me.supcheg.advancedgui.platform.paper.gui.ButtonImpl;

import java.util.Collection;

public class ButtonImplConstructor implements TemplateConstructor<ButtonTemplate, Collection<ButtonImpl>> {
    @Override
    public Collection<ButtonImpl> construct(ButtonTemplate template) {
        return template.coordinates().stream()
                .map(coordinate ->
                        new ButtonImpl(
                                coordinate,
                                template.interactions(),
                                template.displayProvider(),
                                template.lifecycleListenerRegistry()
                        )
                )
                .peek(button -> button.handleEachLifecycleAction(ObjectPointcut.objectConstructPointcut()))
                .toList();
    }
}
