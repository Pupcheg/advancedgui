package me.supcheg.advancedgui.platform.paper.construct;

import me.supcheg.advancedgui.api.button.template.ButtonTemplate;
import me.supcheg.advancedgui.api.lifecycle.pointcut.ObjectPointcut;
import me.supcheg.advancedgui.platform.paper.gui.ButtonImpl;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class ButtonImplConstructor implements TemplateConstructor<ButtonTemplate, Collection<ButtonImpl>> {
    @NotNull
    @Override
    public Collection<ButtonImpl> construct(@NotNull ButtonTemplate template) {
        return template.coordinates().stream()
                .map(coordinate ->
                        new ButtonImpl(
                                coordinate,
                                template.interactions(),
                                template.texture(),
                                template.name(),
                                template.description(),
                                template.attributes(),
                                template.lifecycleListenerRegistry()
                        )
                )
                .peek(button -> button.handleEachLifecycleAction(ObjectPointcut.objectConstructPointcut()))
                .toList();
    }
}
