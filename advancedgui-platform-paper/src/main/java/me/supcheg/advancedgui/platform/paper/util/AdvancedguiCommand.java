package me.supcheg.advancedgui.platform.paper.util;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import lombok.RequiredArgsConstructor;
import me.supcheg.advancedgui.api.Advancedgui;
import me.supcheg.advancedgui.api.controller.GuiController;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

import static io.papermc.paper.command.brigadier.Commands.argument;
import static io.papermc.paper.command.brigadier.Commands.literal;
import static io.papermc.paper.command.brigadier.argument.ArgumentTypes.key;

@SuppressWarnings("UnstableApiUsage")
@RequiredArgsConstructor
public final class AdvancedguiCommand {
    private final GuiController<?, ?> controller;

    public LiteralCommandNode<CommandSourceStack> build() {
        return literal(Advancedgui.NAMESPACE)
                .then(literal("open")
                        .then(argument("key", key())
                                .executes(this::open)
                        )
                )
                .build();
    }

    private int open(@NotNull CommandContext<CommandSourceStack> ctx) {
        Key key = ctx.getArgument("key", Key.class);
        controller.guiOrThrow(key)
                .open(ctx.getSource().getSender());
        return Command.SINGLE_SUCCESS;
    }
}
