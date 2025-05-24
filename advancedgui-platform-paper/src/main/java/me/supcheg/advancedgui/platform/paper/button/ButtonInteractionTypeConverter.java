package me.supcheg.advancedgui.platform.paper.button;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.supcheg.advancedgui.api.button.interaction.ButtonInteractionType;
import net.minecraft.world.inventory.ClickType;
import org.jetbrains.annotations.NotNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ButtonInteractionTypeConverter {

    // see https://minecraft.wiki/w/Java_Edition_protocol/Packets#Click_Container
    @NotNull
    public static ButtonInteractionType fromVanilla(@NotNull ClickType clickType, int buttonNum) {
        return switch (clickType) {
            case PICKUP -> isRightClick(buttonNum) ?
                    ButtonInteractionType.RIGHT_CLICK :
                    ButtonInteractionType.LEFT_CLICK;

            case QUICK_MOVE -> isRightClick(buttonNum) ?
                    ButtonInteractionType.SHIFT_RIGHT_CLICK :
                    ButtonInteractionType.SHIFT_LEFT_CLICK;

            default -> ButtonInteractionType.LEFT_CLICK;
        };
    }

    private static boolean isRightClick(int buttonNum) {
        return buttonNum == 1;
    }
}
