package me.supcheg.advancedgui.platform.paper.construct;

public interface TemplateConstructor<T, O> {
    O construct(T template);
}
