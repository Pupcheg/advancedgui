package me.supcheg.advancedgui.platform.paper.construct;

import lombok.RequiredArgsConstructor;
import me.supcheg.advancedgui.api.button.template.ButtonTemplate;
import me.supcheg.advancedgui.api.layout.template.AnvilLayoutTemplate;
import me.supcheg.advancedgui.api.layout.template.LayoutTemplate;
import me.supcheg.advancedgui.platform.paper.gui.AnvilLayoutImpl;
import me.supcheg.advancedgui.platform.paper.gui.ButtonImpl;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class LayoutImplConstructor implements TemplateConstructor<LayoutTemplate<?, ?, ?>, AnvilLayoutImpl> {
    private final TemplateConstructor<ButtonTemplate, Collection<ButtonImpl>> buttonConstructor;

    @NotNull
    @Override
    public AnvilLayoutImpl construct(@NotNull LayoutTemplate<?, ?, ?> template) {
        return switch (template) {
            case AnvilLayoutTemplate anvil -> new AnvilLayoutImpl(
                    anvil.buttons().stream()
                            .map(buttonConstructor::construct)
                            .flatMap(Collection::stream)
                            .collect(Collectors.toUnmodifiableSet()),
                    anvil.inputUpdateListeners(),
                    anvil.lifecycleListenerRegistry()
            );
        };
    }
}
