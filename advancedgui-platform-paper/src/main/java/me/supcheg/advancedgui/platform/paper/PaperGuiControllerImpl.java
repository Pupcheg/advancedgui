package me.supcheg.advancedgui.platform.paper;

import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import lombok.extern.slf4j.Slf4j;
import me.supcheg.advancedgui.api.button.display.ButtonDisplay;
import me.supcheg.advancedgui.api.component.ComponentRenderContext;
import me.supcheg.advancedgui.api.component.ComponentRenderers;
import me.supcheg.advancedgui.api.gui.Gui;
import me.supcheg.advancedgui.api.gui.template.GuiTemplate;
import me.supcheg.advancedgui.api.lifecycle.pointcut.ObjectPointcut;
import me.supcheg.advancedgui.api.lifecycle.pointcut.RegistrationPointcut;
import me.supcheg.advancedgui.api.lifecycle.pointcut.TickPointcut;
import me.supcheg.advancedgui.api.lifecycle.pointcut.support.PointcutSupport;
import me.supcheg.advancedgui.platform.paper.construct.ButtonImplConstructor;
import me.supcheg.advancedgui.platform.paper.construct.GuiImplConstructor;
import me.supcheg.advancedgui.platform.paper.construct.LayoutImplConstructor;
import me.supcheg.advancedgui.platform.paper.construct.TemplateConstructor;
import me.supcheg.advancedgui.platform.paper.gui.GuiImpl;
import me.supcheg.advancedgui.platform.paper.network.DelegatingNetworkInjection;
import me.supcheg.advancedgui.platform.paper.network.NetworkInjection;
import me.supcheg.advancedgui.platform.paper.network.NmsNetworkInjection;
import me.supcheg.advancedgui.platform.paper.network.message.AdvancedguiPluginChannel;
import me.supcheg.advancedgui.platform.paper.render.DefaultBackgroundComponentRenderer;
import me.supcheg.advancedgui.platform.paper.render.DefaultButtonDisplayItemStackRenderer;
import me.supcheg.advancedgui.platform.paper.render.Renderer;
import me.supcheg.advancedgui.platform.paper.resourcepack.DefaultBackgroundImageMetaLookup;
import me.supcheg.advancedgui.platform.paper.view.DefaultGuiViewer;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.renderer.ComponentRenderer;
import net.kyori.adventure.util.Ticks;
import net.minecraft.world.item.ItemStack;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
final class PaperGuiControllerImpl implements PaperGuiController {
    private static final PointcutSupport POINTCUT_SUPPORT = PointcutSupport.pointcutSupport(
            pointcutSupport -> pointcutSupport
                    .addSupported(Set.of(
                            // Registration
                            RegistrationPointcut.preRegisterPointcut(),
                            RegistrationPointcut.postRegisterPointcut(),
                            RegistrationPointcut.preUnregisterPointcut(),
                            RegistrationPointcut.postUnregisterPointcut()
                    ))
                    .addSupported(Set.of(
                            // Tick
                            TickPointcut.beforeTickPointcut(),
                            TickPointcut.afterTickPointcut()
                    ))
                    .addSupported(Set.of(
                            // Object
                            ObjectPointcut.objectConstructPointcut()
                    ))
    );

    private final ComponentRenderer<ComponentRenderContext> componentRenderer;
    private final Plugin plugin;

    private final ExecutorService guiTasksExecutor;
    private final Map<Key, GuiImpl> registry;
    private final Map<Key, Gui> unmodifiableRegistryView;
    private final TemplateConstructor<GuiTemplate, GuiImpl> guiConstructor;
    private final NetworkInjection networkInjection;
    private final ScheduledTask tickTask;
    private final AdvancedguiPluginChannel channel;

    PaperGuiControllerImpl(
            Plugin plugin,
            ComponentRenderer<ComponentRenderContext> componentRenderer
    ) {
        this.guiTasksExecutor = Executors.newSingleThreadExecutor();

        this.componentRenderer = componentRenderer;
        this.plugin = plugin;

        this.registry = new ConcurrentHashMap<>();
        this.unmodifiableRegistryView = Collections.unmodifiableMap(registry);

        Renderer<ButtonDisplay, ItemStack> displayItemStackRenderer = new DefaultButtonDisplayItemStackRenderer();

        this.channel = new AdvancedguiPluginChannel(plugin);
        channel.register();

        DefaultGuiViewer guiViewer = new DefaultGuiViewer(
                new DefaultPlatformAudienceConverter(Bukkit.getName()),
                new DefaultBackgroundComponentRenderer(
                        new DefaultBackgroundImageMetaLookup()
                ),
                displayItemStackRenderer,
                (DelegatingNetworkInjection) () -> PaperGuiControllerImpl.this.networkInjection,
                channel
        );

        this.guiConstructor = new GuiImplConstructor(
                new LayoutImplConstructor(
                        new ButtonImplConstructor()
                ),
                guiViewer
        );

        this.networkInjection = new NmsNetworkInjection(guiViewer, guiTasksExecutor);

        this.tickTask = Bukkit.getAsyncScheduler()
                .runAtFixedRate(
                        plugin,
                        this::tick,
                        Ticks.SINGLE_TICK_DURATION_MS,
                        Ticks.SINGLE_TICK_DURATION_MS,
                        TimeUnit.MILLISECONDS
                );
    }

    @Override
    public ComponentRenderer<ComponentRenderContext> componentRenderer() {
        return componentRenderer;
    }

    @Override
    public PointcutSupport pointcutSupport() {
        return POINTCUT_SUPPORT;
    }

    @UnmodifiableView
    @Override
    public Collection<Gui> guis() {
        return unmodifiableRegistryView.values();
    }

    @Nullable
    @Override
    public Gui gui(Key key) {
        Objects.requireNonNull(key, "key");
        return registry.get(key);
    }

    @Override
    public Gui register(GuiTemplate template) {
        Objects.requireNonNull(template, "template");

        GuiImpl gui = guiConstructor.construct(template);

        gui.handleEachLifecycleAction(RegistrationPointcut.preRegisterPointcut());
        registry.put(gui.key(), gui);
        gui.handleEachLifecycleAction(RegistrationPointcut.postRegisterPointcut());

        return gui;
    }

    @Override
    public void unregister(Key key) {
        Objects.requireNonNull(key, "key");

        GuiImpl gui = registry.get(key);
        if (gui != null) {
            gui.handleEachLifecycleAction(RegistrationPointcut.preUnregisterPointcut());
            registry.remove(key, gui);
            gui.handleEachLifecycleAction(RegistrationPointcut.postUnregisterPointcut());
        }
    }

    private void tick(ScheduledTask task) {
        for (GuiImpl gui : registry.values()) {
            try {
                gui.tick();
            } catch (Exception e) {
                log.error("An error occurred while ticking {}", gui, e);
            }
        }
    }

    @Override
    public void close() throws IOException {
        Closeable tickTaskCloseable = tickTask::cancel;

        try (
                guiTasksExecutor;
                tickTaskCloseable;
                networkInjection;
                channel
        ) {
            // this try-with-resources will generate safe close for each Closeable
        }
    }

    @Override
    public Builder toBuilder() {
        return new BuilderImpl(this);
    }

    @Override
    public Plugin plugin() {
        return plugin;
    }

    static final class BuilderImpl implements Builder {
        @MonotonicNonNull
        private ComponentRenderer<ComponentRenderContext> componentRenderer;
        @MonotonicNonNull
        private Plugin plugin;

        BuilderImpl() {
            componentRenderer = ComponentRenderers.noopComponentRenderer();
        }

        BuilderImpl(PaperGuiControllerImpl impl) {
            componentRenderer = impl.componentRenderer;
            plugin = impl.plugin;
        }

        @Override
        public Builder componentRenderer(ComponentRenderer<ComponentRenderContext> componentRenderer) {
            Objects.requireNonNull(componentRenderer, "componentRenderer");
            this.componentRenderer = componentRenderer;
            return this;
        }

        @Nullable
        @Override
        public ComponentRenderer<ComponentRenderContext> componentRenderer() {
            return componentRenderer;
        }

        @Nullable
        @Override
        public Plugin plugin() {
            return plugin;
        }

        @Override
        public Builder plugin(Plugin plugin) {
            Objects.requireNonNull(plugin, "plugin");
            this.plugin = plugin;
            return this;
        }

        @Override
        public PaperGuiController build() {
            return new PaperGuiControllerImpl(
                    Objects.requireNonNull(plugin, "plugin"),
                    componentRenderer
            );
        }

    }
}
