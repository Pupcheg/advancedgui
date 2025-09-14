package me.supcheg.advancedgui.platform.paper.construct;

import lombok.RequiredArgsConstructor;
import me.supcheg.advancedgui.api.button.template.ButtonTemplate;
import me.supcheg.advancedgui.api.layout.template.AnvilLayoutTemplate;
import me.supcheg.advancedgui.api.layout.template.LayoutTemplate;
import me.supcheg.advancedgui.api.lifecycle.pointcut.ObjectPointcut;
import me.supcheg.advancedgui.platform.paper.gui.AnvilLayoutImpl;
import me.supcheg.advancedgui.platform.paper.gui.ButtonImpl;
import me.supcheg.advancedgui.platform.paper.gui.LayoutImpl;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class LayoutImplConstructor implements TemplateConstructor<LayoutTemplate<?, ?, ?>, LayoutImpl<?>> {
    private final TemplateConstructor<ButtonTemplate, Collection<ButtonImpl>> buttonConstructor;

    @Override
    public LayoutImpl<?> construct(LayoutTemplate<?, ?, ?> template) {
        var layout = switch (template) {
            case AnvilLayoutTemplate anvil -> new AnvilLayoutImpl(
                    anvil.buttons().stream()
                            .map(buttonConstructor::construct)
                            .<ButtonImpl>mapMulti(Iterable::forEach)
                            .collect(Collectors.toUnmodifiableSet()),
                    anvil.inputUpdateListeners(),
                    anvil.lifecycleListenerRegistry()
            );
        };
        layout.handleEachLifecycleAction(ObjectPointcut.objectConstructPointcut());
        return layout;
    }
}
