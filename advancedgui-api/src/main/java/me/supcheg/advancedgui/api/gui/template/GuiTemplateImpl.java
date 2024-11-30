package me.supcheg.advancedgui.api.gui.template;

import me.supcheg.advancedgui.api.gui.background.Background;
import me.supcheg.advancedgui.api.gui.tick.GuiTicker;
import me.supcheg.advancedgui.api.layout.template.LayoutTemplate;
import me.supcheg.advancedgui.api.sequence.collection.MutablePositionedCollection;
import me.supcheg.advancedgui.api.sequence.collection.PositionedCollection;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

record GuiTemplateImpl(
        @NotNull Key key,
        @NotNull PositionedCollection<GuiTicker> tickers,
        @NotNull LayoutTemplate<?, ?> layout,
        @NotNull Background background
) implements GuiTemplate {
    @NotNull
    @Override
    public Builder toBuilder() {
        return new BuilderImpl(this);
    }

    static final class BuilderImpl implements GuiTemplate.Builder {
        private Key key;
        private final MutablePositionedCollection<GuiTicker> tickers;
        private LayoutTemplate<?, ?> layout;
        private Background background;

        BuilderImpl() {
            this.tickers = MutablePositionedCollection.mutableEmpty();
        }

        BuilderImpl(@NotNull GuiTemplateImpl impl) {
            this.key = impl.key;
            this.tickers = MutablePositionedCollection.mutableCopyOf(impl.tickers);
            this.layout = impl.layout;
            this.background = impl.background;
        }

        @NotNull
        @Override
        public Builder key(@NotNull Key key) {
            Objects.requireNonNull(key, "key");
            this.key = key;
            return this;
        }

        @Nullable
        @Override
        public Key key() {
            return key;
        }

        @NotNull
        @Override
        public <L extends LayoutTemplate<L, B>, B extends LayoutTemplate.Builder<L, B>> Builder layout(@NotNull L layout) {
            Objects.requireNonNull(layout, "layout");
            this.layout = layout;
            return this;
        }

        @NotNull
        @Override
        public Builder addTicker(@NotNull GuiTicker ticker) {
            Objects.requireNonNull(ticker, "ticker");
            this.tickers.add(ticker);
            return this;
        }

        @NotNull
        @Override
        public MutablePositionedCollection<GuiTicker> tickers() {
            return tickers;
        }

        @NotNull
        @Override
        public Builder background(@NotNull Background background) {
            Objects.requireNonNull(background, "background");
            this.background = background;
            return this;
        }

        @Nullable
        @Override
        public Background background() {
            return background;
        }

        @NotNull
        @Override
        public GuiTemplate build() {
            return new GuiTemplateImpl(
                    key,
                    PositionedCollection.copyOf(tickers),
                    layout,
                    background
            );
        }
    }
}
