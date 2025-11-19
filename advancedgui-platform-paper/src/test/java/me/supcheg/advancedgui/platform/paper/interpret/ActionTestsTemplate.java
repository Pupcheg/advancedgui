package me.supcheg.advancedgui.platform.paper.interpret;

import lombok.SneakyThrows;
import me.supcheg.advancedgui.api.action.AudienceActionContext;
import me.supcheg.advancedgui.api.audience.GuiAudience;
import me.supcheg.advancedgui.api.loader.configurate.ConfigurateGuiLoader;
import me.supcheg.advancedgui.platform.paper.interpret.util.SimpleYamlConfigurationLoader;
import org.bukkit.entity.HumanEntity;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.spongepowered.configurate.serialize.TypeSerializerCollection;

abstract class ActionTestsTemplate {
    @MonotonicNonNull
    static SimpleYamlConfigurationLoader LOADER;

    @MonotonicNonNull
    GuiAudience audience;
    @MonotonicNonNull
    HumanEntity humanEntity;

    @MonotonicNonNull
    AudienceActionContext emptyAudienceContext;
    @MonotonicNonNull
    HumanEntityActionContext humanEntityContext;

    @BeforeAll
    static void beforeAll() {
        LOADER = new SimpleYamlConfigurationLoader(ConfigurateGuiLoader.makeSerializers(TypeSerializerCollection.defaults()));
    }

    @BeforeEach
    void setup() {
        audience = Mockito.mock(GuiAudience.class);
        emptyAudienceContext = () -> audience;
        humanEntity = Mockito.mock(HumanEntity.class);
        humanEntityContext = () -> humanEntity;
    }

    @SneakyThrows
    static AudienceContextAction loadAction(@Language("yaml") String yaml) {
        return LOADER.require(AudienceContextAction.class, yaml);
    }

}
