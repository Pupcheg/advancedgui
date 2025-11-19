package me.supcheg.advancedgui.platform.paper.view;

import com.google.common.cache.CacheBuilder;
import lombok.RequiredArgsConstructor;
import me.supcheg.advancedgui.api.button.display.ButtonDisplay;
import me.supcheg.advancedgui.api.gui.background.Background;
import me.supcheg.advancedgui.platform.paper.PlatformAudienceConverter;
import me.supcheg.advancedgui.platform.paper.gui.GuiImpl;
import me.supcheg.advancedgui.platform.paper.network.NetworkInjection;
import me.supcheg.advancedgui.platform.paper.render.ButtonDisplayRenderController;
import me.supcheg.advancedgui.platform.paper.render.DefaultLayoutNonNullListItemStackRenderer;
import me.supcheg.advancedgui.platform.paper.render.Renderer;
import net.kyori.adventure.audience.Audience;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;

import java.util.Optional;
import java.util.concurrent.ConcurrentMap;

@RequiredArgsConstructor
public class DefaultGuiViewer implements GuiViewer, GuiViewLookup {
    private final PlatformAudienceConverter audienceConverter;
    private final Renderer<Background, Component> backgroundComponentRenderer;
    private final Renderer<ButtonDisplay, ItemStack> buttonItemStackRenderer;
    private final NetworkInjection networkInjection;

    private final ConcurrentMap<ServerPlayer, GuiView> viewByServerPlayer = CacheBuilder.newBuilder()
            .weakKeys()
            .<ServerPlayer, GuiView>build()
            .asMap();

    @Override
    public GuiView open(Audience audience, GuiImpl gui) {
        ServerPlayer serverPlayer = audienceConverter.verifyAndConvert(audience);

        var lazy = new Object() {
            @MonotonicNonNull
            GuiView view;
        };
        var displayRenderController = new ButtonDisplayRenderController(
                gui.layout(),
                buttonItemStackRenderer,
                (button, display) -> lazy.view.sendSlotWith(button.coordinate(), display)
        );

        var layoutNonNullListRenderer = new DefaultLayoutNonNullListItemStackRenderer(
                displayRenderController
        );

        GuiView view = new GuiView(
                this,
                gui,
                serverPlayer,
                backgroundComponentRenderer,
                layoutNonNullListRenderer,
                displayRenderController,
                buttonItemStackRenderer,
                new ContainerState(serverPlayer::nextContainerCounter)
        );
        lazy.view = view;

        networkInjection.inject(serverPlayer);

        viewByServerPlayer.compute(
                serverPlayer,
                (__, previousView) -> {
                    if (previousView != null) {
                        previousView.handleRemove();
                    }
                    return view;
                }
        );
        view.sendFull();

        return view;
    }

    void remove(GuiView view) {
        viewByServerPlayer.remove(view.serverPlayer(), view);
    }

    @Override
    public Optional<GuiView> lookupGuiView(ServerPlayer serverPlayer) {
        return Optional.ofNullable(viewByServerPlayer.get(serverPlayer));
    }

    @Override
    public void tick() {
        viewByServerPlayer.values().forEach(GuiView::tick);
    }
}
