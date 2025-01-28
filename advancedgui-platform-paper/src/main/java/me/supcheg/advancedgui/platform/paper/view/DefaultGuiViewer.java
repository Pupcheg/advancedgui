package me.supcheg.advancedgui.platform.paper.view;

import com.google.common.cache.CacheBuilder;
import lombok.RequiredArgsConstructor;
import me.supcheg.advancedgui.api.button.Button;
import me.supcheg.advancedgui.api.coordinate.Coordinate;
import me.supcheg.advancedgui.api.gui.background.Background;
import me.supcheg.advancedgui.api.layout.Layout;
import me.supcheg.advancedgui.platform.paper.PlatformAudienceConverter;
import me.supcheg.advancedgui.platform.paper.gui.GuiImpl;
import me.supcheg.advancedgui.platform.paper.render.Renderer;
import net.kyori.adventure.audience.Audience;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
public class DefaultGuiViewer implements GuiViewer {
    private final PlatformAudienceConverter audienceConverter;
    private final Renderer<Background, Component> backgroundComponentRenderer;
    private final Renderer<Layout<?>, NonNullList<ItemStack>> layoutNonNullListRenderer;
    private final Renderer<Button, ItemStack> buttonItemStackRenderer;

    private final Map<ServerPlayer, GuiView> viewByServerPlayer = CacheBuilder.newBuilder()
            .weakKeys()
            .<ServerPlayer, GuiView>build()
            .asMap();

    @NotNull
    private GuiView findViewOrThrow(@NotNull Audience audience) {
        return Objects.requireNonNull(viewByServerPlayer.get(audienceConverter.verifyAndConvert(audience)), "No opened gui");
    }

    @Override
    public void open(Audience audience, GuiImpl gui) {
        ServerPlayer serverPlayer = audienceConverter.verifyAndConvert(audience);
        GuiView view = new GuiView(
                gui,
                serverPlayer,
                backgroundComponentRenderer,
                layoutNonNullListRenderer,
                buttonItemStackRenderer,
                new ContainerState(serverPlayer::nextContainerCounter)
        );

        viewByServerPlayer.put(serverPlayer, view);

        view.sendFull();
    }

    @Override
    public void updateFull(Audience audience) {
        findViewOrThrow(audience).sendFull();
    }

    @Override
    public void updateLayout(Audience audience) {
        findViewOrThrow(audience).sendLayout();
    }

    @Override
    public void updateButton(Audience audience, Coordinate coordinate) {
        findViewOrThrow(audience).sendSlot(coordinate);
    }

    @Override
    public void emptyCursor(Audience audience) {
        findViewOrThrow(audience).sendEmptyCursor();
    }
}
