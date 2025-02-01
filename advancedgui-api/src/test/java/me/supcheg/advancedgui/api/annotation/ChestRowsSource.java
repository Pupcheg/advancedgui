package me.supcheg.advancedgui.api.annotation;

import me.supcheg.advancedgui.api.TestInventoryConstants;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Provides single argument:
 * <p>{@code int} from {@code 1} to {@link TestInventoryConstants#MAX_CHEST_INVENTORY_ROWS}
 *
 * @see ChestRowsArgumentsProvider
 * @see ChestRowsSourceTests
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@ArgumentsSource(ChestRowsArgumentsProvider.class)
public @interface ChestRowsSource {
}
