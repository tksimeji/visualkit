package com.tksimeji.kunectron.element;

import com.google.common.base.Preconditions;
import com.tksimeji.mojango.Mojango;
import com.tksimeji.mojango.texture.Skin;
import com.tksimeji.kunectron.Kunectron;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerHeadElementImpl extends ItemElementImpl implements PlayerHeadElement {
    private static final Map<String, UUID> UUID_CACHE = new HashMap<>();

    private @Nullable URL url;

    public PlayerHeadElementImpl() {
        super(ItemType.PLAYER_HEAD);
    }

    @Override
    public @NotNull ItemElement type(final @NotNull ItemType type) {
        if (type != ItemType.PLAYER_HEAD) {
            return new ItemElementImpl(type);
        }
        return this;
    }

    @Override
    public @NotNull PlayerHeadElement name(final @Nullable String name) {
        if (name == null) {
            return url((URL) null);
        }

        if (UUID_CACHE.containsKey(name)) {
            return uuid(UUID_CACHE.get(name));
        }

        Bukkit.getScheduler().runTaskAsynchronously(Kunectron.plugin(), () -> {
            UUID uuid = Mojango.account(name).getUniqueId();
            uuid(uuid);
            UUID_CACHE.put(name, uuid);
        });
        return this;
    }

    @Override
    public @NotNull PlayerHeadElement uuid(final @Nullable UUID uuid) {
        if (uuid == null) {
            return url((URL) null);
        }

        Player player = Bukkit.getPlayer(uuid);

        if (player != null) {
            URL skin = player.getPlayerProfile().getTextures().getSkin();
            if (skin != null) {
                return url(skin);
            }
        }

        Bukkit.getScheduler().runTaskAsynchronously(Kunectron.plugin(), () -> {
            Skin skin = Mojango.account(uuid).getSkin();
            url(skin != null ? skin.getUrl() : null);
        });
        return this;
    }

    @Override
    public @NotNull PlayerHeadElement player(final @Nullable OfflinePlayer player) {
        return uuid(player != null ? player.getUniqueId() : null);
    }

    @Override
    public @Nullable URL url() {
        return url;
    }

    @Override
    public @NotNull PlayerHeadElement url(final @Nullable String url) {
        if (url == null) {
            this.url = null;
            return url((URL) null);
        }

        try {
            URL uri = URI.create(url).toURL();
            return url(uri);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid url format: " + url);
        }
    }

    @NotNull
    @Override
    public PlayerHeadElement url(final @Nullable URL url) {
        if (url == null) {
            this.url = null;
        } else {
            Preconditions.checkArgument(url.getHost().equalsIgnoreCase("textures.minecraft.net"), "No servers other then 'texture.minecraft.net' are allowed.");
            this.url = url;
        }
        return this;
    }
}
