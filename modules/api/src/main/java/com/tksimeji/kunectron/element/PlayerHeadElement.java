package com.tksimeji.kunectron.element;

import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URL;
import java.util.UUID;

public interface PlayerHeadElement extends ItemElement {
    @Contract("_ -> this")
    @NotNull PlayerHeadElement name(final @Nullable String name);

    @Contract("_ -> this")
    @NotNull PlayerHeadElement uuid(final @Nullable UUID uuid);

    @Contract("_ -> this")
    @NotNull PlayerHeadElement player(final @Nullable OfflinePlayer player);

    @Nullable URL url();

    @Contract("_ -> this")
    @NotNull PlayerHeadElement url(final @Nullable String url);

    @Contract("_ -> this")
    @NotNull PlayerHeadElement url(final @Nullable URL url);
}
