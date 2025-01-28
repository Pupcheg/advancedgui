package me.supcheg.advancedgui.platform.paper.interpret.close;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.supcheg.advancedgui.api.action.AudienceActionContext;
import me.supcheg.advancedgui.api.loader.interpret.ActionInterpreter;
import net.kyori.adventure.audience.Audience;
import org.bukkit.entity.HumanEntity;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;

import static java.lang.invoke.MethodHandles.empty;
import static java.lang.invoke.MethodHandles.explicitCastArguments;
import static java.lang.invoke.MethodHandles.filterArguments;
import static java.lang.invoke.MethodHandles.guardWithTest;
import static java.lang.invoke.MethodHandles.throwException;
import static java.lang.invoke.MethodType.methodType;
import static java.lang.invoke.StringConcatFactory.makeConcat;

@Slf4j
final class CloseActionInterpreter implements ActionInterpreter<CloseActionInterpretContext> {

    private final MethodHandle ignoring;
    private final MethodHandle logging;
    private final MethodHandle fatal;

    @SneakyThrows
    CloseActionInterpreter() {
        MethodHandles.Lookup lookup = MethodHandles.lookup();

        MethodHandle isHumanEntityInstance = explicitCastArguments(
                lookup.findVirtual(Class.class, "isInstance", methodType(boolean.class, Object.class))
                        .bindTo(HumanEntity.class),
                methodType(boolean.class, Audience.class)
        );

        MethodHandle audienceCloseInventory = explicitCastArguments(
                lookup.findVirtual(HumanEntity.class, "closeInventory", methodType(void.class)),
                methodType(void.class, Audience.class)
        );

        MethodHandle doNothingWithAudience = empty(methodType(void.class, Audience.class));

        MethodHandle logAudienceError = explicitCastArguments(
                lookup.findVirtual(Logger.class, "error", methodType(void.class, String.class, Object.class))
                        .bindTo(log)
                        .bindTo("Unable to close inventory for {}"),
                methodType(void.class, Audience.class)
        );

        MethodHandle throwAudienceException = filterArguments(
                throwException(void.class, IllegalArgumentException.class),
                0, filterArguments(
                        lookup.findConstructor(IllegalArgumentException.class, methodType(void.class, String.class)),
                        0, makeConcat(lookup, "Unable to close inventory for \u0001", methodType(String.class, Audience.class)).dynamicInvoker()
                )
        );

        MethodHandle audienceFromActionContext = lookup.findVirtual(AudienceActionContext.class, "audience", methodType(Audience.class));

        /* This method handle looks like

        var audience = ctx.audience();
        if (audience instanceof HumanEntity humanEntity) {
            humanEntity.closeInventory();
        }
         */
        ignoring = filterArguments(
                guardWithTest(
                        isHumanEntityInstance,
                        audienceCloseInventory,
                        doNothingWithAudience
                ),
                0, audienceFromActionContext
        );

        /* This method handle looks like

        var audience = ctx.audience();
        if (audience instanceof HumanEntity humanEntity) {
            humanEntity.closeInventory();
        } else {
            log.error("Unable to close inventory for {}", audience);
        }
         */
        logging = filterArguments(
                guardWithTest(
                        isHumanEntityInstance,
                        audienceCloseInventory,
                        logAudienceError
                ),
                0, audienceFromActionContext
        );

        /* This method handle looks like

        var audience = ctx.audience();
        if (audience instanceof HumanEntity humanEntity) {
            humanEntity.closeInventory();
        } else {
            throw new IllegalArgumentException("Unable to close inventory for " + audience);
        }
         */
        fatal = filterArguments(
                guardWithTest(
                        isHumanEntityInstance,
                        audienceCloseInventory,
                        throwAudienceException
                ),
                0, audienceFromActionContext
        );
    }

    @NotNull
    @Override
    public MethodHandle interpretMethodHandle(@NotNull CloseActionInterpretContext ctx) {
        return switch (ctx.errorStrategy()) {
            case IGNORE -> ignoring;
            case LOG -> logging;
            case FATAL -> fatal;
        };
    }
}
