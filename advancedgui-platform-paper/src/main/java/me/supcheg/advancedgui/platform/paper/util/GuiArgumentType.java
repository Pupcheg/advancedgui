package me.supcheg.advancedgui.platform.paper.util;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.CustomArgumentType;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import me.supcheg.advancedgui.api.controller.GuiController;
import me.supcheg.advancedgui.api.gui.Gui;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static io.papermc.paper.command.brigadier.MessageComponentSerializer.message;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.translatable;

@SuppressWarnings("UnstableApiUsage")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class GuiArgumentType implements CustomArgumentType<Gui, Key> {
    private static final DynamicCommandExceptionType GUI_NOT_FOUND = new DynamicCommandExceptionType(
            key -> message()
                    .serialize(
                            translatable()
                                    .key("advancedgui.chat.command.gui_not_found")
                                    .fallback("Gui not found for key %s".formatted(key))
                                    .arguments(text(String.valueOf(key)))
                                    .build()
                    )
    );

    private final ArgumentType<Key> keyType = ArgumentTypes.key();
    private final GuiController<?, ?> controller;

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static GuiArgumentType gui(@NotNull GuiController<?, ?> controller) {
        return new GuiArgumentType(
                Objects.requireNonNull(controller, "controller")
        );
    }

    @NotNull
    @Override
    public Gui parse(@NotNull StringReader reader) throws CommandSyntaxException {
        int cursor = reader.getCursor();

        Key key = keyType.parse(reader);
        Gui gui = controller.gui(key);

        if (gui == null) {
            reader.setCursor(cursor);
            throw GUI_NOT_FOUND.createWithContext(reader, key);
        }
        return gui;
    }

    @NotNull
    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(@NotNull CommandContext<S> context, @NotNull SuggestionsBuilder builder) {
        for (Gui gui : controller.guis()) {
            builder.suggest(gui.key().asString());
        }
        return builder.buildFuture();
    }

    @NotNull
    @Override
    public ArgumentType<Key> getNativeType() {
        return keyType;
    }
}
