package com.tksimeji.visualkit;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public abstract class PanelUI extends SimplePanelUI {
    static void show(@NotNull Player player, @NotNull PanelUI ui) {
        player.setScoreboard(ui.scoreboard);
    }

    static void show(@NotNull Player player, @NotNull SharedPanelUI ui) {
        player.setScoreboard(ui.scoreboard);
    }

    static void hide(@NotNull Player player) {
        IPanelUI ui = Visualkit.getSession(IPanelUI.class, player);

        player.setScoreboard(sm.getMainScoreboard());

        if (ui != null) {
            if (ui instanceof PanelUI p) {
                p.kill();
            } else if (ui instanceof ISharedPanelUI s) {
                s.removeAudience(player);
            } else {
                throw new UnsupportedOperationException();
            }
        }
    }

    protected final Player player;

    /**
     * Start a GUI for any player.
     *
     * @param player Player showing GUI
     */
    public PanelUI(@NotNull Player player) {
        show(this.player = player, this);
    }

    /**
     * Get the player displaying the panel.
     *
     * @return Player
     */
    public final @NotNull Player getPlayer() {
        return player;
    }

    @Override
    public final void kill() {
        super.kill();
        hide(player);
    }
}
