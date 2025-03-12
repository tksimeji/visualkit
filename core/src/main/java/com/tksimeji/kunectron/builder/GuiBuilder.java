package com.tksimeji.kunectron.builder;

import com.tksimeji.kunectron.hooks.Hooks;
import org.jetbrains.annotations.NotNull;

public interface GuiBuilder<B extends IGuiBuilder<B, H>, H extends Hooks> extends IGuiBuilder<B, H> {
    static @NotNull AnvilGuiBuilder anvilGui() {
        return new AnvilGuiBuilderImpl();
    }

    static @NotNull ChestGuiBuilder chestGui() {
        return new ChestGuiBuilderImpl();
    }

    static @NotNull MerchantGuiBuilder merchantGui() {
        return new MerchantGuiBuilderImpl();
    }

    static @NotNull ScoreboardGuiBuilder scoreboardGui() {
        return new ScoreboardGuiBuilderImpl();
    }
}
