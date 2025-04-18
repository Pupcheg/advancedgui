package me.supcheg.advancedgui.platform.paper;

import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import me.supcheg.advancedgui.api.button.Button;
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
import me.supcheg.advancedgui.platform.paper.render.DefaultBackgroundComponentRenderer;
import me.supcheg.advancedgui.platform.paper.render.DefaultButtonItemStackRenderer;
import me.supcheg.advancedgui.platform.paper.render.DefaultLayoutNonNullListItemStackRenderer;
import me.supcheg.advancedgui.platform.paper.render.Renderer;
import me.supcheg.advancedgui.platform.paper.resourcepack.DefaultBackgroundImageMetaLookup;
import me.supcheg.advancedgui.platform.paper.view.DefaultGuiViewer;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.renderer.ComponentRenderer;
import net.kyori.adventure.util.Ticks;
import net.minecraft.world.item.ItemStack;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

final class PaperGuiControllerImpl implements PaperGuiController {
    private static final PointcutSupport POINTCUT_SUPPORT = PointcutSupport.pointcutSupport(
            pointcutSupport -> pointcutSupport
                    .supports(
                            // Registration
                            RegistrationPointcut.preRegisterPointcut(),
                            RegistrationPointcut.postRegisterPointcut(),
                            RegistrationPointcut.preUnregisterPointcut(),
                            RegistrationPointcut.postUnregisterPointcut()
                    )
                    .supports(
                            // Tick
                            TickPointcut.beforeTickPointcut(),
                            TickPointcut.afterTickPointcut()
                    )
                    .supports(
                            // Object
                            ObjectPointcut.objectConstructPointcut()
                    )
    );

    private final ComponentRenderer<ComponentRenderContext> componentRenderer;
    private final Plugin plugin;

    private final Map<Key, GuiImpl> registry;
    private final Map<Key, Gui> unmodifiableRegistryView;
    private final TemplateConstructor<GuiTemplate, GuiImpl> guiConstructor;
    private final ScheduledTask tickTask;

    PaperGuiControllerImpl(
            @NotNull Plugin plugin,
            @NotNull ComponentRenderer<ComponentRenderContext> componentRenderer
    ) {
        this.componentRenderer = componentRenderer;
        this.plugin = plugin;

        this.registry = new ConcurrentHashMap<>();
        this.unmodifiableRegistryView = Collections.unmodifiableMap(registry);

        Renderer<Button, ItemStack> buttonItemStackRenderer = new DefaultButtonItemStackRenderer();

        DefaultGuiViewer guiViewer = new DefaultGuiViewer(
                new DefaultPlatformAudienceConverter(Bukkit.getName(), CraftPlayer.class),
                new DefaultBackgroundComponentRenderer(
                        new DefaultBackgroundImageMetaLookup()
                ),
                new DefaultLayoutNonNullListItemStackRenderer(
                        buttonItemStackRenderer
                ),
                buttonItemStackRenderer
        );

        this.guiConstructor = new GuiImplConstructor(
                new LayoutImplConstructor(
                        new ButtonImplConstructor()
                ),
                guiViewer
        );
        this.tickTask = startTicking();
    }

    @NotNull
    @Override
    public ComponentRenderer<ComponentRenderContext> componentRenderer() {
        return componentRenderer;
    }

    @NotNull
    @Override
    public PointcutSupport pointcutSupport() {
        return POINTCUT_SUPPORT;
    }

    @UnmodifiableView
    @NotNull
    @Override
    public Collection<Gui> guis() {
        return unmodifiableRegistryView.values();
    }

    @Nullable
    @Override
    public Gui gui(@NotNull Key key) {
        Objects.requireNonNull(key, "key");
        return registry.get(key);
    }

    @NotNull
    @Override
    public Gui register(@NotNull GuiTemplate template) {
        Objects.requireNonNull(template, "template");

        GuiImpl gui = guiConstructor.construct(template);

        gui.handleEachLifecycleAction(RegistrationPointcut.preRegisterPointcut());
        registry.put(gui.key(), gui);
        gui.handleEachLifecycleAction(RegistrationPointcut.postRegisterPointcut());

        return gui;
    }

    @Override
    public void unregister(@NotNull Key key) {
        Objects.requireNonNull(key, "key");

        GuiImpl gui = registry.get(key);
        if (gui != null) {
            gui.handleEachLifecycleAction(RegistrationPointcut.preUnregisterPointcut());
            registry.remove(key, gui);
            gui.handleEachLifecycleAction(RegistrationPointcut.postUnregisterPointcut());
        }
    }

    @NotNull
    private ScheduledTask startTicking() {
        return Bukkit.getAsyncScheduler()
                .runAtFixedRate(
                        plugin,
                        this::tick,
                        Ticks.SINGLE_TICK_DURATION_MS,
                        Ticks.SINGLE_TICK_DURATION_MS,
                        TimeUnit.MILLISECONDS
                );
    }

    private void tick(@NotNull ScheduledTask task) {
        for (GuiImpl gui : registry.values()) {
            gui.tick();
        }
    }

    @Override
    public void close() {
        tickTask.cancel();
    }

    @NotNull
    @Override
    public Builder toBuilder() {
        return new BuilderImpl(this);
    }

    @NotNull
    @Override
    public Plugin plugin() {
        return plugin;
    }

    static final class BuilderImpl implements Builder {
        private ComponentRenderer<ComponentRenderContext> componentRenderer;
        private Plugin plugin;

        BuilderImpl() {
            componentRenderer = ComponentRenderers.noopComponentRenderer();
        }

        BuilderImpl(@NotNull PaperGuiControllerImpl impl) {
            componentRenderer = impl.componentRenderer;
            plugin = impl.plugin;
        }

        @NotNull
        @Override
        public Builder componentRenderer(@NotNull ComponentRenderer<ComponentRenderContext> componentRenderer) {
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

        @NotNull
        @Override
        public Builder plugin(@NotNull Plugin plugin) {
            Objects.requireNonNull(plugin, "plugin");
            this.plugin = plugin;
            return this;
        }

        @NotNull
        @Override
        public PaperGuiController build() {
            return new PaperGuiControllerImpl(
                    Objects.requireNonNull(plugin, "plugin"),
                    componentRenderer
            );
        }

    }
}
