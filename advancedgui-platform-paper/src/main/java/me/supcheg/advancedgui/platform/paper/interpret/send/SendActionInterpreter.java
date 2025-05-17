package me.supcheg.advancedgui.platform.paper.interpret.send;

import lombok.SneakyThrows;
import me.supcheg.advancedgui.api.action.AudienceActionContext;
import me.supcheg.advancedgui.api.loader.interpret.ActionInterpreter;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;

import static java.lang.invoke.MethodType.methodType;

final class SendActionInterpreter implements ActionInterpreter<SendActionInterpretContext> {

    private final MethodHandle chat;
    private final MethodHandle actionBar;

    @SneakyThrows
    SendActionInterpreter() {
        MethodHandles.Lookup lookup = MethodHandles.lookup();

        MethodHandle audienceFromActionContext = lookup.findVirtual(AudienceActionContext.class, "audience", methodType(Audience.class));

        MethodHandle audienceSendMessage = lookup.findVirtual(Audience.class, "sendMessage", methodType(void.class, Component.class));
        this.chat = MethodHandles.filterArguments(audienceSendMessage, 0, audienceFromActionContext);

        MethodHandle audienceSendActionBar = lookup.findVirtual(Audience.class, "sendActionBar", methodType(void.class, Component.class));
        this.actionBar = MethodHandles.filterArguments(audienceSendActionBar, 0, audienceFromActionContext);
    }

    @NotNull
    @Override
    public MethodHandle interpretMethodHandle(@NotNull SendActionInterpretContext ctx) {
        MethodHandle handle = switch (ctx.target()) {
            case CHAT -> chat;
            case ACTION_BAR -> actionBar;
        };

        handle = MethodHandles.insertArguments(handle, 1, ctx.content());

        return handle;
    }
}
