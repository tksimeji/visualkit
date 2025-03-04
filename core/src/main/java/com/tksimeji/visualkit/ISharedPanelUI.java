package com.tksimeji.visualkit;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Deprecated(since = "1.0.0", forRemoval = true)
public interface ISharedPanelUI extends IPanelUI {
    /**
     * Gets a list of audiences.
     *
     * @return Audience list
     */
    @NotNull List<Player> getAudience();

    /**
     * Add an audience.
     *
     * @param audience Audience
     */
    void addAudience(@NotNull Player audience);

    /**
     * Remove an audience.
     *
     * @param audience Audience
     */
    void removeAudience(@NotNull Player audience);

    /**
     * Determine if a player is in the panel's audience.
     *
     * @param player Players surveyed
     * @return True for audience.
     */
    boolean isAudience(@Nullable Player player);
}
