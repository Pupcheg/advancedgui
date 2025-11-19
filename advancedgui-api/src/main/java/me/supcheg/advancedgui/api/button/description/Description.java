package me.supcheg.advancedgui.api.button.description;

import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.code.RecordInterface;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;
import java.util.function.Consumer;

@RecordInterface
public interface Description extends Buildable<Description, DescriptionBuilder> {

    static DescriptionBuilder description() {
        return new DescriptionBuilderImpl();
    }

    static Description description(Consumer<DescriptionBuilder> consumer) {
        return Buildable.configureAndBuild(description(), consumer);
    }

    @Unmodifiable
    List<Component> lines();
}
