package me.supcheg.advancedgui.platform.paper.interpret;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class CloseActionTests extends ActionTestsTemplate {
    @BeforeEach
    void setup() {
        super.setup();

        doThrow(new UnsupportedOperationException("Expected exception")).when(audience).close();
        doThrow(new UnsupportedOperationException("Expected exception")).when(humanEntity).closeInventory();
    }

    @Test
    void ignoringActionTest() {
        var action = loadAction("{ type: close, error-strategy: ignore }");

        action.handle(emptyAudienceContext);
        // an ignored error

        action.handle(humanEntityContext);
        // an ignored error

        verify(humanEntityContext.humanEntity(), times(1))
                .closeInventory();
    }

    @Test
    void fatalActionTest() {
        var action = loadAction("{ type: close, error-strategy: fatal }");

        assertThrows(Exception.class, () -> action.handle(emptyAudienceContext));

        assertThrows(Exception.class, () -> action.handle(humanEntityContext));

        verify(humanEntityContext.humanEntity(), times(1))
                .closeInventory();
    }

    @Test
    void logActionTest() {
        var action = loadAction("{ type: close, error-strategy: log }");

        action.handle(emptyAudienceContext);
        // a logged error

        action.handle(humanEntityContext);
        // a logged error

        verify(humanEntityContext.humanEntity(), times(1))
                .closeInventory();
    }
}
