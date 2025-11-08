package me.supcheg.advancedgui.platform.paper.view;

import com.google.common.cache.CacheBuilder;
import lombok.RequiredArgsConstructor;
import me.supcheg.advancedgui.api.Advancedgui;
import me.supcheg.advancedgui.api.button.Button;
import me.supcheg.advancedgui.api.gui.background.Background;
import me.supcheg.advancedgui.api.messaging.DebugViewGuiTemplate;
import me.supcheg.advancedgui.platform.paper.PlatformAudienceConverter;
import me.supcheg.advancedgui.platform.paper.gui.GuiImpl;
import me.supcheg.advancedgui.platform.paper.gui.LayoutImpl;
import me.supcheg.advancedgui.platform.paper.network.NetworkInjection;
import me.supcheg.advancedgui.platform.paper.network.message.AdvancedguiPluginChannel;
import me.supcheg.advancedgui.platform.paper.render.Renderer;
import net.kyori.adventure.audience.Audience;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.entity.CraftPlayer;

import java.util.Optional;
import java.util.concurrent.ConcurrentMap;

@RequiredArgsConstructor
public class DefaultGuiViewer implements GuiViewer, GuiViewLookup {
    private final PlatformAudienceConverter audienceConverter;
    private final Renderer<Background, Component> backgroundComponentRenderer;
    private final Renderer<LayoutImpl<?>, NonNullList<ItemStack>> layoutNonNullListRenderer;
    private final Renderer<Button, ItemStack> buttonItemStackRenderer;
    private final NetworkInjection networkInjection;
    private final AdvancedguiPluginChannel channel;

    private final ConcurrentMap<ServerPlayer, GuiView> viewByServerPlayer = CacheBuilder.newBuilder()
            .weakKeys()
            .<ServerPlayer, GuiView>build()
            .asMap();

    @Override
    public GuiView open(Audience audience, GuiImpl gui) {
        ServerPlayer serverPlayer = audienceConverter.verifyAndConvert(audience);
        GuiView view = new GuiView(
                this,
                gui,
                serverPlayer,
                backgroundComponentRenderer,
                layoutNonNullListRenderer,
                buttonItemStackRenderer,
                new ContainerState(serverPlayer::nextContainerCounter)
        );

        networkInjection.inject(serverPlayer);

        viewByServerPlayer.compute(
                serverPlayer,
                (__, previousView) -> {
                    if (previousView != null) {
                        previousView.handleRemove();
                    }
                    return view;
                }
        );
        view.sendFull();

        CraftPlayer bukkitEntity = serverPlayer.getBukkitEntity();
        if (bukkitEntity.hasPermission(Advancedgui.NAMESPACE + ".debug")) {
            channel.sendMessage(bukkitEntity, new DebugViewGuiTemplate(gui.source()));
        }
        return view;
    }

    void remove(GuiView view) {
        viewByServerPlayer.remove(view.serverPlayer(), view);
    }

    @Override
    public Optional<GuiView> lookupGuiView(ServerPlayer serverPlayer) {
        return Optional.ofNullable(viewByServerPlayer.get(serverPlayer));
    }
}
