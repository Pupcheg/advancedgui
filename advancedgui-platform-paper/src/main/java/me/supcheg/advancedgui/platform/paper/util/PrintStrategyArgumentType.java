package me.supcheg.advancedgui.platform.paper.util;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import io.papermc.paper.command.brigadier.argument.CustomArgumentType;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletableFuture;

import static io.papermc.paper.command.brigadier.MessageComponentSerializer.message;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.translatable;

@SuppressWarnings("UnstableApiUsage")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class PrintStrategyArgumentType implements CustomArgumentType<PrintStrategy, String> {
    private static final DynamicCommandExceptionType PRINT_STRATEGY_NOT_FOUND = new DynamicCommandExceptionType(
            key -> message()
                    .serialize(
                            translatable()
                                    .key("advancedgui.chat.command.print_strategy_not_found")
                                    .fallback("Print strategy not found for key %s".formatted(key))
                                    .arguments(text(String.valueOf(key)))
                                    .build()
                    )
    );
    private static final PrintStrategy[] VALUES = PrintStrategy.values();

    private final ArgumentType<String> stringType = StringArgumentType.string();

    public static PrintStrategyArgumentType printStrategy() {
        return new PrintStrategyArgumentType();
    }

    @Override
    public PrintStrategy parse(StringReader reader) throws CommandSyntaxException {
        int cursor = reader.getCursor();

        String key = stringType.parse(reader).toUpperCase();
        PrintStrategy strategy;

        try {
            strategy = PrintStrategy.valueOf(key);
        } catch (IllegalArgumentException e) {
            reader.setCursor(cursor);
            throw PRINT_STRATEGY_NOT_FOUND.createWithContext(reader, key);
        }

        return strategy;
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        for (PrintStrategy value : VALUES) {
            builder.suggest(value.toString().toLowerCase());
        }
        return builder.buildFuture();
    }

    @Override
    public ArgumentType<String> getNativeType() {
        return stringType;
    }
}
