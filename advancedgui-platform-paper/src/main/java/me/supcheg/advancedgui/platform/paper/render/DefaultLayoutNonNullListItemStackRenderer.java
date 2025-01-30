package me.supcheg.advancedgui.platform.paper.render;

import lombok.RequiredArgsConstructor;
import me.supcheg.advancedgui.api.button.Button;
import me.supcheg.advancedgui.api.coordinate.CoordinateTranslator;
import me.supcheg.advancedgui.platform.paper.gui.LayoutImpl;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

@RequiredArgsConstructor
public class DefaultLayoutNonNullListItemStackRenderer implements Renderer<LayoutImpl<?>, NonNullList<ItemStack>> {
    private final Renderer<Button, ItemStack> buttonItemStackRenderer;

    @NotNull
    @Override
    public NonNullList<ItemStack> render(@NotNull LayoutImpl<?> input) {
        CoordinateTranslator coordinateTranslator = input.coordinateTranslator();

        ItemStack[] itemStacks = new ItemStack[coordinateTranslator.slotsCount()];
        Arrays.fill(itemStacks, ItemStack.EMPTY);

        for (Button button : input.buttons()) {
            int index = coordinateTranslator.toIndex(button.coordinate());
            itemStacks[index] = buttonItemStackRenderer.render(button);
        }

        return NonNullList.of(ItemStack.EMPTY, itemStacks);
    }
}
