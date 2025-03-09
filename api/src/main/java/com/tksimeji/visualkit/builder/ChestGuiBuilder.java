package com.tksimeji.visualkit.builder;

import com.tksimeji.visualkit.ChestGui;
import com.tksimeji.visualkit.hooks.ChestGuiHooks;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface ChestGuiBuilder extends ItemContainerGuiBuilder<ChestGuiBuilder, ChestGuiHooks> {
    @Contract("_ -> this")
    @NotNull ChestGuiBuilder size(final @NotNull ChestGui.ChestSize size);
}
