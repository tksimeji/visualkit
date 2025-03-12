package com.tksimeji.kunectron.builder;

import com.tksimeji.kunectron.ChestGui;
import com.tksimeji.kunectron.hooks.ChestGuiHooks;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface ChestGuiBuilder extends ItemContainerGuiBuilder<ChestGuiBuilder, ChestGuiHooks> {
    @Contract("_ -> this")
    @NotNull ChestGuiBuilder size(final @NotNull ChestGui.ChestSize size);
}
