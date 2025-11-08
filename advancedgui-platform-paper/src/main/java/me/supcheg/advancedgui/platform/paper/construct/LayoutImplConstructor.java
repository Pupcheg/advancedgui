package me.supcheg.advancedgui.platform.paper.construct;

import lombok.RequiredArgsConstructor;
import me.supcheg.advancedgui.api.button.template.ButtonTemplate;
import me.supcheg.advancedgui.api.layout.template.AnvilLayoutTemplate;
import me.supcheg.advancedgui.api.layout.template.ChestLayoutTemplate;
import me.supcheg.advancedgui.api.layout.template.LayoutTemplate;
import me.supcheg.advancedgui.api.lifecycle.pointcut.ObjectPointcut;
import me.supcheg.advancedgui.platform.paper.gui.AnvilLayoutImpl;
import me.supcheg.advancedgui.platform.paper.gui.ButtonImpl;
import me.supcheg.advancedgui.platform.paper.gui.ChestLayoutImpl;
import me.supcheg.advancedgui.platform.paper.gui.LayoutImpl;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class LayoutImplConstructor implements TemplateConstructor<LayoutTemplate<?, ?, ?>, LayoutImpl<?>> {
    private final TemplateConstructor<ButtonTemplate, Collection<ButtonImpl>> buttonConstructor;

    @Override
    public LayoutImpl<?> construct(LayoutTemplate<?, ?, ?> template) {
        var layout = switch (template) {
            case AnvilLayoutTemplate anvil -> new AnvilLayoutImpl(
                    constructButtons(anvil.buttons()),
                    anvil.inputUpdateListeners(),
                    anvil.lifecycleListenerRegistry()
            );
            case ChestLayoutTemplate chest -> new ChestLayoutImpl(
                    constructButtons(chest.buttons()),
                    chest.rows(),
                    chest.lifecycleListenerRegistry()
            );
        };
        layout.handleEachLifecycleAction(ObjectPointcut.objectConstructPointcut());
        return layout;
    }

    private Set<ButtonImpl> constructButtons(Set<ButtonTemplate> buttons) {
        return buttons.stream()
                .map(buttonConstructor::construct)
                .<ButtonImpl>mapMulti(Iterable::forEach)
                .collect(Collectors.toUnmodifiableSet());
    }
}
