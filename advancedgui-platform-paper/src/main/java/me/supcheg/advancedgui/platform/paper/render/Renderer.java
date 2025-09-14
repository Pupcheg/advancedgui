package me.supcheg.advancedgui.platform.paper.render;

public interface Renderer<I, O> {
    O render(I input);
}
