package com.tksimeji.kunectron.builder;

import com.tksimeji.kunectron.hooks.Hooks;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.NotNull;

public interface GuiBuilder<B extends IGuiBuilder<B, H>, H extends Hooks> extends IGuiBuilder<B, H> {
    static @NotNull AnvilGuiBuilder anvilGui() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    static @NotNull ChestGuiBuilder chestGui() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    static @NotNull MerchantGuiBuilder merchantGui() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }

    static @NotNull ScoreboardGuiBuilder scoreboardGui() {
        throw new NotImplementedException("The API module cannot be called at runtime.");
    }
}
