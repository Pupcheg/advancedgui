package me.supcheg.advancedgui.platform.paper.view;

import lombok.RequiredArgsConstructor;
import me.supcheg.advancedgui.api.button.Button;
import me.supcheg.advancedgui.api.coordinate.Coordinate;
import me.supcheg.advancedgui.api.gui.background.Background;
import me.supcheg.advancedgui.platform.paper.gui.GuiImpl;
import me.supcheg.advancedgui.platform.paper.gui.LayoutImpl;
import me.supcheg.advancedgui.platform.paper.render.Renderer;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class GuiView {
    private final DefaultGuiViewer viewer;
    private final GuiImpl gui;
    private final ServerPlayer serverPlayer;
    private final Renderer<Background, Component> backgroundComponentRenderer;
    private final Renderer<LayoutImpl<?>, NonNullList<ItemStack>> layoutNonNullListRenderer;
    private final Renderer<Button, ItemStack> buttonItemStackRenderer;
    private final ContainerState containerState;

    public GuiImpl gui() {
        return gui;
    }

    public ServerPlayer serverPlayer() {
        return serverPlayer;
    }

    public ContainerState containerState() {
        return containerState;
    }

    public void sendFull() {
        send(backgroundPacket());
        send(layoutPacket());
    }

    public void sendLayout() {
        send(layoutPacket());
    }

    public void sendSlot(@NotNull Coordinate coordinate) {
        send(slotPacket(coordinate));
    }

    public void sendSlot(int index) {
        send(slotPacket(index));
    }

    public void sendEmptyCursor() {
        send(emptyCursorPacket());
    }

    private void send(@NotNull Packet<?> packet) {
        serverPlayer.connection.send(packet);
    }

    @NotNull
    private Packet<?> backgroundPacket() {
        return new ClientboundOpenScreenPacket(
                containerState.nextContainerId(),
                gui.layout().menuType(),
                backgroundComponentRenderer.render(gui.background())
        );
    }

    @NotNull
    private Packet<?> layoutPacket() {
        return new ClientboundContainerSetContentPacket(
                containerState.containerId(),
                containerState.nextStateId(),
                layoutNonNullListRenderer.render(gui.layout()),
                ItemStack.EMPTY
        );
    }

    @NotNull
    private Packet<?> slotPacket(@NotNull Coordinate coordinate) {
        return new ClientboundContainerSetSlotPacket(
                containerState.containerId(),
                containerState.nextStateId(),
                gui.layout().coordinateTranslator().toIndex(coordinate),
                gui.layout().buttonAt(coordinate)
                        .map(buttonItemStackRenderer::render)
                        .orElse(ItemStack.EMPTY)
        );
    }

    @NotNull
    private Packet<?> slotPacket(int index) {
        return new ClientboundContainerSetSlotPacket(
                containerState.containerId(),
                containerState.nextStateId(),
                index,
                gui.layout().buttonAt(index)
                        .map(buttonItemStackRenderer::render)
                        .orElse(ItemStack.EMPTY)
        );
    }

    @NotNull
    private Packet<?> emptyCursorPacket() {
        return new ClientboundContainerSetSlotPacket(
                containerState.containerId(),
                containerState.nextStateId(),
                -1,
                ItemStack.EMPTY
        );
    }

    public void handleRemove() {
        viewer.remove(this);
    }

    public void handleClose() {

    }
}
