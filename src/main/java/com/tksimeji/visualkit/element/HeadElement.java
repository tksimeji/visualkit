package com.tksimeji.visualkit.element;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.tksimeji.mojango.Mojango;
import com.tksimeji.mojango.texture.Skin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerTextures;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.UUID;

public final class HeadElement extends VisualkitElement {
    HeadElement() {
        super(Material.PLAYER_HEAD);
    }

    /**
     * The HeadElement must always be of type {@code Material.PLAYER_HEAD}.
     * If any other value is specified, an UnsupportedOperationException will be thrown.
     *
     * @param type Item type
     * @return Updated element
     */
    @Override
    @Deprecated
    public @NotNull VisualkitElement type(@NotNull Material type) {
        if (type != Material.PLAYER_HEAD) {
            throw new UnsupportedOperationException();
        }

        return this;
    }

    private @Nullable URL url;

    /**
     * Set any skin to a texture.
     * However, you cannot specify a texture server other than {@code textures.minecraft.net}.
     *
     * @param url Texture URL
     * @return Updated element
     */
    public @NotNull HeadElement url(@Nullable String url) {
        if (url == null) {
            this.url = null;
            return this;
        }

        try {
            URL u = URI.create(url).toURL();

            if (! u.getHost().equalsIgnoreCase("textures.minecraft.net")) {
                throw new IllegalArgumentException("No servers other than textures.minecraft.net are allowed.");
            }

            this.url = u;

            return this;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Set the texture to any player's skin.
     *
     * @param uuid  Player UUID
     * @return Updated element
     */
    public @NotNull HeadElement uuid(@Nullable UUID uuid) {
        if (uuid == null) {
            url(null);
            return this;
        }

        Skin skin = Mojango.INSTANCE.account(uuid).getSkin();
        url(skin != null ? skin.getUrl() : null);

        return this;
    }

    /**
     * Set the texture to any player's skin.
     *
     * @param player Player name
     * @return Updated element
     */
    public @NotNull HeadElement player(@Nullable OfflinePlayer player) {
        return uuid(player != null ? player.getUniqueId() : null);
    }

    @Override
    public @NotNull ItemStack asItemStack(@NotNull Object object) {
        ItemStack itemStack = super.asItemStack(object);

        if (url == null) {
            return itemStack;
        }

        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();

        PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID());
        PlayerTextures textures = profile.getTextures();

        textures.setSkin(url);
        profile.setTextures(textures);

        skullMeta.setPlayerProfile(profile);
        itemStack.setItemMeta(skullMeta);

        return itemStack;
    }
}
