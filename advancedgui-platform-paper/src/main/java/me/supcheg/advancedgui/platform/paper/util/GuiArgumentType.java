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
import org.checkerframework.checker.nullness.qual.Nullable;

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

    public static GuiArgumentType gui(GuiController<?, ?> controller) {
        return new GuiArgumentType(
                Objects.requireNonNull(controller, "controller")
        );
    }

    @Override
    public Gui parse(StringReader reader) throws CommandSyntaxException {
        int cursor = reader.getCursor();

        Key key = keyType.parse(reader);
        @Nullable Gui gui = controller.gui(key);

        if (gui == null) {
            reader.setCursor(cursor);
            throw GUI_NOT_FOUND.createWithContext(reader, key);
        }
        return gui;
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> ctx, SuggestionsBuilder builder) {
        for (Gui gui : controller.guis()) {
            var keyAsString = gui.key().asString();
            if (keyAsString.toLowerCase().startsWith(builder.getRemainingLowerCase())) {
                builder.suggest(keyAsString);
            }
        }
        return builder.buildFuture();
    }

    @Override
    public ArgumentType<Key> getNativeType() {
        return keyType;
    }
}
