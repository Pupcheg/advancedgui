package me.supcheg.advancedgui.api.button.display;

import java.util.Iterator;

record SingleButtonDisplayProviderImpl(
        ButtonDisplay display
) implements SingleButtonDisplayProvider {
    @Override
    public Iterator<? extends ButtonDisplay> displaysLoop() {
        return new SingleValueLoopIterator<>(display);
    }

    record SingleValueLoopIterator<T>(T value) implements Iterator<T> {
        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public T next() {
            return value;
        }
    }
}
