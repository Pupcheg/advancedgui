package me.supcheg.advancedgui.platform.paper.interpret;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class CloseActionTests extends ActionTestsTemplate {
    @Test
    void ignoringActionTest() {
        var action = loadAction("{ type: close, error-strategy: ignore }");

        action.handle(emptyAudienceContext);
        // an ignored error

        action.handle(humanEntityContext);

        verify(humanEntityContext.humanEntity(), times(1))
                .closeInventory();
    }

    @Test
    void fatalActionTest() {
        var action = loadAction("{ type: close, error-strategy: fatal }");

        assertThrows(Exception.class, () -> action.handle(emptyAudienceContext));

        action.handle(humanEntityContext);

        verify(humanEntityContext.humanEntity(), times(1))
                .closeInventory();
    }

    @Test
    void logActionTest() {
        var action = loadAction("{ type: close, error-strategy: log }");

        action.handle(emptyAudienceContext);
        // a logged error

        action.handle(humanEntityContext);

        verify(humanEntityContext.humanEntity(), times(1))
                .closeInventory();
    }
}
