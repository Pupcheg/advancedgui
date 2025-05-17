package me.supcheg.advancedgui.platform.paper.interpret.close;

import me.supcheg.advancedgui.api.action.Action;
import me.supcheg.advancedgui.api.action.AudienceActionContext;
import me.supcheg.advancedgui.api.loader.configurate.ConfigurateGuiLoader;
import me.supcheg.advancedgui.platform.paper.interpret.util.SimpleYamlConfigurationLoader;
import net.kyori.adventure.audience.Audience;
import org.bukkit.entity.HumanEntity;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.serialize.TypeSerializerCollection;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CloseActionTests {

    static SimpleYamlConfigurationLoader LOADER;

    AudienceActionContext emptyAudienceContext;
    HumanEntityActionContext humanEntityContext;

    @BeforeAll
    static void beforeAll() {
        LOADER = new SimpleYamlConfigurationLoader(ConfigurateGuiLoader.makeSerializers(TypeSerializerCollection.defaults()));
    }

    @BeforeEach
    void setup() {
        emptyAudienceContext = Audience::empty;
        HumanEntity humanEntity = Mockito.mock(HumanEntity.class);
        humanEntityContext = () -> humanEntity;
    }

    @Test
    void ignoringActionTest() throws ConfigurateException {
        AudienceContextAction action = LOADER.require(
                AudienceContextAction.class,
                """
                        type: close
                        error-strategy: ignore
                        """
        );

        action.handle(emptyAudienceContext);
        // an ignored error

        action.handle(humanEntityContext);
        verify(humanEntityContext.humanEntity(), times(1))
                .closeInventory();
    }

    @Test
    void fatalActionTest() throws ConfigurateException {
        AudienceContextAction action = LOADER.require(
                AudienceContextAction.class,
                """
                        type: close
                        error-strategy: fatal
                        """
        );

        assertThrows(Exception.class, () -> action.handle(emptyAudienceContext));

        action.handle(humanEntityContext);
        verify(humanEntityContext.humanEntity(), times(1))
                .closeInventory();
    }

    @Test
    void logActionTest() throws ConfigurateException {
        AudienceContextAction action = LOADER.require(
                AudienceContextAction.class,
                """
                        type: close
                        error-strategy: log
                        """
        );

        action.handle(emptyAudienceContext);
        // a logged error

        action.handle(humanEntityContext);
        verify(humanEntityContext.humanEntity(), times(1))
                .closeInventory();
    }

    public interface AudienceContextAction extends Action {
        void handle(@NotNull AudienceActionContext ctx);
    }

    interface HumanEntityActionContext extends AudienceActionContext {
        @NotNull
        HumanEntity humanEntity();

        @Override
        @NotNull
        default Audience audience() {
            return humanEntity();
        }
    }
}
