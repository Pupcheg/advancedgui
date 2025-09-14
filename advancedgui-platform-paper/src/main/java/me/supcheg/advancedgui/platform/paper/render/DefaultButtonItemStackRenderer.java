package me.supcheg.advancedgui.platform.paper.render;

import io.papermc.paper.adventure.PaperAdventure;
import me.supcheg.advancedgui.api.button.Button;
import me.supcheg.advancedgui.api.button.description.Description;
import net.kyori.adventure.key.Key;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemLore;

import static io.papermc.paper.adventure.PaperAdventure.asVanilla;
import static me.supcheg.advancedgui.api.button.attribute.ButtonAttribute.glowing;
import static me.supcheg.advancedgui.api.button.attribute.ButtonAttribute.hidden;

public class DefaultButtonItemStackRenderer implements Renderer<Button, ItemStack> {
    @Override
    public ItemStack render(Button input) {

        if (input.hasAttribute(hidden())) {
            return ItemStack.EMPTY;
        }

        ItemStack itemStack = new ItemStack(Items.PAPER);
        itemStack.set(DataComponents.ITEM_MODEL, model(input.texture()));
        itemStack.set(DataComponents.ITEM_NAME, name(input.name()));
        itemStack.set(DataComponents.LORE, lore(input.description()));

        if (input.hasAttribute(glowing())) {
            itemStack.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);
        }

        return itemStack;
    }

    private ResourceLocation model(Key key) {
        return PaperAdventure.asVanilla(key);
    }

    private ItemLore lore(Description description) {
        var transform = PaperAdventure.asVanilla(description.lines());
        return new ItemLore(transform, transform);
    }

    private Component name(net.kyori.adventure.text.Component name) {
        return asVanilla(name);
    }
}
