package me.supcheg.advancedgui.platform.paper.view;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.supcheg.advancedgui.api.button.Button;
import me.supcheg.advancedgui.api.button.display.ButtonDisplay;
import me.supcheg.advancedgui.api.coordinate.Coordinate;
import me.supcheg.advancedgui.api.gui.background.Background;
import me.supcheg.advancedgui.platform.paper.ToStringHelper;
import me.supcheg.advancedgui.platform.paper.gui.GuiImpl;
import me.supcheg.advancedgui.platform.paper.gui.LayoutImpl;
import me.supcheg.advancedgui.platform.paper.render.ButtonDisplayRenderController;
import me.supcheg.advancedgui.platform.paper.render.Renderer;
import me.supcheg.advancedgui.platform.paper.tick.Tickable;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundContainerClosePacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import net.minecraft.network.protocol.game.ClientboundSetCursorItemPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

@Slf4j
@RequiredArgsConstructor
public class GuiView implements Tickable {
    private final DefaultGuiViewer viewer;
    private final GuiImpl gui;
    private final ServerPlayer serverPlayer;
    private final Renderer<Background, Component> backgroundComponentRenderer;
    private final Renderer<LayoutImpl<?>, NonNullList<ItemStack>> layoutNonNullListRenderer;
    private final ButtonDisplayRenderController displayRenderController;
    private final Renderer<ButtonDisplay, ItemStack> buttonDisplayRenderer;
    private final ContainerState containerState;

    private boolean closed;

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

    public void sendSlot(Coordinate coordinate) {
        send(slotPacket(coordinate));
    }

    public void sendSlot(int index) {
        send(slotPacket(index));
    }

    public void sendSlotWith(Coordinate coordinate, ButtonDisplay display) {
        send(slotPacket(coordinate, display));
    }

    public void sendButton(Button button) {
        send(slotPacket(button));
    }

    public void sendEmptyCursor() {
        send(emptyCursorPacket());
    }

    public void sendClose() {
        send(closePacket());
    }

    private void send(Packet<?> packet) {
        if (closed) {
            throw new IllegalStateException("GuiView is closed");
        }

        if (log.isDebugEnabled()) {
            log.debug("Sending {} to {}", ToStringHelper.toStringReflectively(packet), serverPlayer.getScoreboardName());
        }
        serverPlayer.connection.send(packet);
    }

    private Packet<?> backgroundPacket() {
        return new ClientboundOpenScreenPacket(
                containerState.nextContainerId(),
                gui.layout().menuType(),
                backgroundComponentRenderer.render(gui.background())
        );
    }

    private Packet<?> layoutPacket() {
        return new ClientboundContainerSetContentPacket(
                containerState.containerId(),
                containerState.nextStateId(),
                layoutNonNullListRenderer.render(gui.layout()),
                ItemStack.EMPTY
        );
    }

    private Packet<?> slotPacket(Coordinate coordinate) {
        return new ClientboundContainerSetSlotPacket(
                containerState.containerId(),
                containerState.nextStateId(),
                gui.layout().coordinateTranslator().toIndex(coordinate),
                gui.layout().buttonAt(coordinate)
                        .map(displayRenderController::render)
                        .orElse(ItemStack.EMPTY)
        );
    }

    private Packet<?> slotPacket(int index) {
        return new ClientboundContainerSetSlotPacket(
                containerState.containerId(),
                containerState.nextStateId(),
                index,
                gui.layout().buttonAt(index)
                        .map(displayRenderController::render)
                        .orElse(ItemStack.EMPTY)
        );
    }

    private Packet<?> slotPacket(Button button) {
        return new ClientboundContainerSetSlotPacket(
                containerState.containerId(),
                containerState.nextStateId(),
                gui.layout().coordinateTranslator().toIndex(button.coordinate()),
                displayRenderController.render(button)
        );
    }

    private Packet<?> slotPacket(Coordinate coordinate, ButtonDisplay display) {
        return new ClientboundContainerSetSlotPacket(
                containerState.containerId(),
                containerState.nextStateId(),
                gui.layout().coordinateTranslator().toIndex(coordinate),
                buttonDisplayRenderer.render(display)
        );
    }

    private Packet<?> emptyCursorPacket() {
        return new ClientboundSetCursorItemPacket(ItemStack.EMPTY);
    }

    private Packet<?> closePacket() {
        return new ClientboundContainerClosePacket(containerState.containerId());
    }

    public void handleRemove() {
    }

    public void handleClose() {
        if (closed) {
            return;
        }
        closed = true;
        viewer.remove(this);
    }

    @Override
    public void tick() {
        displayRenderController.tick();
    }
}
