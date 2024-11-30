package com.tksimeji.visualkit;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public interface VisualkitUI extends Tickable {
    @NotNull Component title();
}
