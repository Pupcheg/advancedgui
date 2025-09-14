package me.supcheg.advancedgui.platform.paper;

import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.api.controller.GuiController;
import org.bukkit.plugin.Plugin;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.IOException;
import java.util.function.Consumer;

public interface PaperGuiController extends GuiController<PaperGuiController, PaperGuiController.Builder> {

    static Builder paperGuiController() {
        return new PaperGuiControllerImpl.BuilderImpl();
    }

    static PaperGuiController paperGuiController(Consumer<Builder> consumer) {
        return Buildable.configureAndBuild(paperGuiController(), consumer);
    }

    Plugin plugin();

    @Override
    void close() throws IOException;

    interface Builder extends GuiController.Builder<PaperGuiController, Builder> {
        @Nullable
        Plugin plugin();

        Builder plugin(Plugin plugin);
    }
}
