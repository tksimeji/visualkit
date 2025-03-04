package com.tksimeji.visualkit;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

@Deprecated(forRemoval = true)
@ApiStatus.ScheduledForRemoval(inVersion = "1.0.0")public abstract class SharedPanelUI extends SimplePanelUI implements ISharedPanelUI {
    private final @NotNull Set<Player> audiences = new HashSet<>();

    /**
     * Start a GUI for any player(s).
     *
     * @param audiences Audiences
     */
    public SharedPanelUI(@NotNull Player... audiences) {
        this(Arrays.stream(audiences).toList());
    }

    /**
     * Start a GUI for any player(s).
     *
     * @param audiences Audience
     */
    public SharedPanelUI(@NotNull List<Player> audiences) {
        audiences.forEach(this::addAudience);
    }

    @Override
    public final @NotNull List<Player> getAudience() {
        return new ArrayList<>(audiences);
    }

    @Override
    public final void addAudience(@NotNull Player audience) {
        PanelUI.show(audience, this);
        audiences.add(audience);
    }

    @Override
    public final void removeAudience(@NotNull Player audience) {
        if (! audiences.contains(audience)) {
            return;
        }

        audiences.remove(audience);
        PanelUI.hide(audience);
    }

    @Override
    public final boolean isAudience(@Nullable Player player) {
        return audiences.contains(player);
    }

    @Override
    public final void kill() {
        super.kill();
        getAudience().forEach(this::removeAudience);
    }
}
