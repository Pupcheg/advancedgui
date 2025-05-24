package me.supcheg.advancedgui.platform.paper.interpret;

import org.junit.jupiter.api.Test;

import static net.kyori.adventure.text.Component.text;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class SendActionTests extends ActionTestsTemplate {
    @Test
    void chatTargetTest() {
        var action = loadAction("{ type: send, content: text, target: chat }");

        action.handle(humanEntityContext);

        verify(humanEntityContext.humanEntity(), times(1))
                .sendMessage(text("text"));
    }

    @Test
    void actionBarTargetTest() {
        var action = loadAction("{ type: send, content: text, target: action_bar }");

        action.handle(humanEntityContext);

        verify(humanEntityContext.humanEntity(), times(1))
                .sendActionBar(text("text"));
    }
}
