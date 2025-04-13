package me.supcheg.advancedgui.platform.paper.util;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import lombok.RequiredArgsConstructor;
import me.supcheg.advancedgui.api.Advancedgui;
import me.supcheg.advancedgui.api.controller.GuiController;
import me.supcheg.advancedgui.api.gui.Gui;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static io.papermc.paper.command.brigadier.Commands.argument;
import static io.papermc.paper.command.brigadier.Commands.literal;
import static io.papermc.paper.command.brigadier.argument.ArgumentTypes.players;
import static me.supcheg.advancedgui.platform.paper.util.GuiArgumentType.gui;

@SuppressWarnings("UnstableApiUsage")
@RequiredArgsConstructor
public final class AdvancedguiCommand {
    private static final String GUI_ARG = "gui";
    private static final String PLAYERS_ARG = "players";

    private final GuiController<?, ?> controller;

    public LiteralCommandNode<CommandSourceStack> build() {
        return literal(Advancedgui.NAMESPACE)
                .then(literal("open")
                        .then(argument(PLAYERS_ARG, players())
                                .then(argument(GUI_ARG, gui(controller))
                                        .executes(AdvancedguiCommand::open)
                                )
                        )
                )
                .build();
    }

    private static int open(@NotNull CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        Gui gui = ctx.getArgument(GUI_ARG, Gui.class);
        List<Player> players = ctx.getArgument(PLAYERS_ARG, PlayerSelectorArgumentResolver.class)
                .resolve(ctx.getSource());

        players.forEach(gui::open);

        return players.size();
    }
}
