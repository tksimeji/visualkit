package com.tksimeji.visualkit.hooks;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface ContainerGuiHooks extends Hooks {
    @NotNull Player usePlayer();

    void useClose();
}
