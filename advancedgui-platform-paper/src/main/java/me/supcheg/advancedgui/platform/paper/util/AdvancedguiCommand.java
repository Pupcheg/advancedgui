package me.supcheg.advancedgui.platform.paper.util;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import me.supcheg.advancedgui.api.Advancedgui;
import me.supcheg.advancedgui.api.controller.GuiController;
import me.supcheg.advancedgui.api.gui.Gui;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static io.papermc.paper.command.brigadier.Commands.argument;
import static io.papermc.paper.command.brigadier.Commands.literal;
import static io.papermc.paper.command.brigadier.argument.ArgumentTypes.players;
import static me.supcheg.advancedgui.platform.paper.util.GuiArgumentType.gui;
import static me.supcheg.advancedgui.platform.paper.util.PrintStrategyArgumentType.printStrategy;

@SuppressWarnings("UnstableApiUsage")
@RequiredArgsConstructor
public final class AdvancedguiCommand {
    private static final String GUI_ARG = "gui";
    private static final String PRINT_STRATEGY_ARG = "strategy";
    private static final String PLAYERS_ARG = "players";

    private final GuiController<?, ?> controller;

    public LiteralCommandNode<CommandSourceStack> build() {
        return literal(Advancedgui.NAMESPACE)
                .then(literal("open")
                        .then(argument(PLAYERS_ARG, players())
                                .then(argument(GUI_ARG, gui(controller))
                                        .executes(this::open)
                                )
                        )
                )
                .then(literal("print")
                        .then(argument(PRINT_STRATEGY_ARG, printStrategy())
                                .then(argument(GUI_ARG, gui(controller))
                                        .executes(this::print)
                                )
                        )
                )
                .build();
    }

    private int open(@NotNull CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        Gui gui = ctx.getArgument(GUI_ARG, Gui.class);
        List<Player> players = ctx.getArgument(PLAYERS_ARG, PlayerSelectorArgumentResolver.class)
                .resolve(ctx.getSource());

        players.forEach(gui::open);

        return players.size();
    }

    @SneakyThrows
    private int print(@NotNull CommandContext<CommandSourceStack> ctx) {
        Gui gui = ctx.getArgument(GUI_ARG, Gui.class);
        PrintStrategy strategy = ctx.getArgument(PRINT_STRATEGY_ARG, PrintStrategy.class);

        Component message = strategy.print(gui.source());

        ctx.getSource().getSender().sendMessage(message);

        return Command.SINGLE_SUCCESS;
    }
}
