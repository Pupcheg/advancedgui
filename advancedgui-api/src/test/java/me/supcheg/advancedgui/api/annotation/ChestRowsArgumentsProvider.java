package me.supcheg.advancedgui.api.annotation;

import me.supcheg.advancedgui.api.TestInventoryConstants;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.IntStream;
import java.util.stream.Stream;

final class ChestRowsArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return IntStream.rangeClosed(1, TestInventoryConstants.MAX_CHEST_INVENTORY_ROWS)
                .mapToObj(Arguments::arguments);
    }
}
