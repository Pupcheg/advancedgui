package me.supcheg.advancedgui.platform.paper;

import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.api.controller.GuiController;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public interface PaperGuiController extends GuiController<PaperGuiController, PaperGuiController.Builder> {

    @NotNull
    @Contract("-> new")
    static Builder paperGuiController() {
        return new PaperGuiControllerImpl.BuilderImpl();
    }

    @NotNull
    @Contract("_ -> new")
    static PaperGuiController paperGuiController(@NotNull Consumer<Builder> consumer) {
        return Buildable.configureAndBuild(paperGuiController(), consumer);
    }

    @NotNull
    Plugin plugin();

    @Override
    void close();

    interface Builder extends GuiController.Builder<PaperGuiController, Builder> {
        @Nullable
        Plugin plugin();

        @NotNull
        @Contract("_ -> this")
        Builder plugin(@NotNull Plugin plugin);
    }
}
