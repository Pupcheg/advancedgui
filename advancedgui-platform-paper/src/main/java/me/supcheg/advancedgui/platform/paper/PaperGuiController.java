package me.supcheg.advancedgui.platform.paper;

import me.supcheg.advancedgui.api.controller.GuiController;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface PaperGuiController extends GuiController<PaperGuiController, PaperGuiController.Builder> {

    @NotNull
    Plugin plugin();

    interface Builder extends GuiController.Builder<PaperGuiController, Builder> {
        @Nullable
        Plugin plugin();

        @NotNull
        @Contract("_ -> this")
        Builder plugin(@NotNull Plugin plugin);
    }
}
