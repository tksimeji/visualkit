package com.tksimeji.visualkit;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ISharedPanelUI extends IPanelUI {
    /**
     * Get a list of audiences.
     *
     * @return Audience list
     */
    @NotNull List<@NotNull Player> getAudience();

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
    boolean isAudience(@NotNull Player player);
}
