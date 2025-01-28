package me.supcheg.advancedgui.platform.paper.view;

import lombok.RequiredArgsConstructor;

import java.util.function.IntSupplier;

@RequiredArgsConstructor
public class ContainerState {
    private final IntSupplier containerIdSource;

    private int containerId = -1;
    private int stateId = 0;

    public int containerId() {
        if (containerId == -1) {
            throw new IllegalStateException("Container has not been created");
        }
        return containerId;
    }

    public int nextContainerId() {
        containerId = containerIdSource.getAsInt();
        stateId = 0;
        return containerId;
    }

    public int nextStateId() {
        return stateId++;
    }
}
