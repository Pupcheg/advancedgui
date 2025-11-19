package me.supcheg.advancedgui.platform.paper.render;

import lombok.RequiredArgsConstructor;
import me.supcheg.advancedgui.api.button.Button;
import me.supcheg.advancedgui.api.button.display.ButtonDisplay;
import me.supcheg.advancedgui.api.button.display.UpdatableButtonDisplayProvider;
import me.supcheg.advancedgui.api.coordinate.Coordinate;
import me.supcheg.advancedgui.platform.paper.gui.LayoutImpl;
import me.supcheg.advancedgui.platform.paper.tick.Tickable;
import net.kyori.adventure.util.Ticks;
import net.minecraft.world.item.ItemStack;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class ButtonDisplayRenderController implements Renderer<Button, ItemStack>, Tickable {
    private final Renderer<ButtonDisplay, ItemStack> displayItemRenderer;

    private final Map<Coordinate, UpdateableDisplayState> displayStateByCoordinate;

    public ButtonDisplayRenderController(
            LayoutImpl<?> layout,
            Renderer<ButtonDisplay, ItemStack> displayItemRenderer,
            BiConsumer<Button, ButtonDisplay> updateSubscriber
    ) {
        this.displayItemRenderer = displayItemRenderer;
        this.displayStateByCoordinate = new LinkedHashMap<>();

        for (Button button : layout.buttons()) {
            if (button.displayProvider() instanceof UpdatableButtonDisplayProvider updatable && updatable.updatable()) {
                Consumer<ButtonDisplay> subscriber = display -> updateSubscriber.accept(button, display);
                displayStateByCoordinate.put(button.coordinate(), new UpdateableDisplayState(updatable, subscriber));
            }
        }
    }

    @Override
    public ItemStack render(Button input) {
        ButtonDisplay currentDisplay;

        if (!(input instanceof UpdatableButtonDisplayProvider updatable) || updatable.updatable()) {
            currentDisplay = input.displayProvider().displaysLoop().next();
        } else {
            currentDisplay = displayStateByCoordinate.get(input.coordinate()).current();
        }

        return displayItemRenderer.render(currentDisplay);
    }

    @Override
    public void tick() {
        displayStateByCoordinate.values().forEach(UpdateableDisplayState::tick);
    }

    private static class UpdateableDisplayState implements Tickable {
        private final Consumer<ButtonDisplay> subscriber;

        private final Iterator<ButtonDisplay> displaysLoop;
        private final int switchTicks;

        private ButtonDisplay current;
        private int leftTicksToNext;

        public UpdateableDisplayState(UpdatableButtonDisplayProvider displayProvider, Consumer<ButtonDisplay> subscriber) {
            this.subscriber = subscriber;

            this.displaysLoop = displayProvider.displaysLoop();
            this.switchTicks = Math.toIntExact(displayProvider.switchDuration().toMillis() / Ticks.SINGLE_TICK_DURATION_MS);

            this.current = displaysLoop.next();
            this.leftTicksToNext = switchTicks;
        }

        private ButtonDisplay current() {
            return current;
        }

        @Override
        public void tick() {
            if (--leftTicksToNext > 0) {
                return;
            }

            current = displaysLoop.next();
            leftTicksToNext = switchTicks;
            subscriber.accept(current);
        }
    }
}
