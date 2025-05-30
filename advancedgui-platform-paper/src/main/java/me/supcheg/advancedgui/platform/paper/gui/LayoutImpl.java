package me.supcheg.advancedgui.platform.paper.gui;

import me.supcheg.advancedgui.api.layout.Layout;
import me.supcheg.advancedgui.platform.paper.lifecycle.DefaultLifecycled;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.NotNull;

import java.util.function.IntUnaryOperator;

public interface LayoutImpl<T extends Layout<T>> extends Layout<T>, DefaultLifecycled<T> {

    @NotNull
    MenuType<?> menuType();

    void tick();

    @NotNull
    default IntUnaryOperator indexFixer() {
        return IntUnaryOperator.identity();
    }

}
