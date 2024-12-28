package com.tksimeji.visualkit;

import com.tksimeji.visualkit.listener.InventoryListener;
import com.tksimeji.visualkit.listener.PlayerListener;
import com.tksimeji.visualkit.listener.PluginListener;
import com.tksimeji.visualkit.listener.ServerListener;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class Visualkit extends JavaPlugin {
    private static Visualkit instance;

    static final @NotNull Set<IVisualkitUI> sessions = new HashSet<>();

    public static @NotNull Visualkit plugin() {
        return instance;
    }

    public static @NotNull String version() {
        return instance.getPluginMeta().getVersion();
    }

    public static @NotNull ComponentLogger logger() {
        return instance.getComponentLogger();
    }

    public static @NotNull List<IVisualkitUI> sessions() {
        return new ArrayList<>(sessions);
    }

    public static <T extends IVisualkitUI> @NotNull List<T> sessions(@NotNull Class<T> clazz) {
        return sessions().stream()
                .filter(s -> clazz.isAssignableFrom(s.getClass()))
                .map(s -> (T) s)
                .toList();
    }

    public static @Nullable IInventoryUI<?> getInventoryUI(@Nullable Player player) {
        return sessions(IInventoryUI.class).stream().filter(s -> s.getPlayer() == player).findFirst().orElse(null);
    }

    public static @Nullable IPanelUI getPanelUI(@Nullable Player player) {
        if (player == null) {
            return null;
        }

        return sessions(IPanelUI.class).stream()
                .filter(s -> s instanceof PanelUI t ? t.getPlayer() == player : s instanceof SharedPanelUI u && u.isAudience(player))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void onEnable() {
        instance = this;

        Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        Bukkit.getPluginManager().registerEvents(new PluginListener(), this);
        Bukkit.getPluginManager().registerEvents(new ServerListener(), this);

        logger().info(Component.text("       __    ").color(TextColor.color(255, 86, 217)));
        logger().info(Component.text("___  _|  | __").color(TextColor.color(255, 124, 255)).append(Component.text("    Visualkit - " + version()).color(NamedTextColor.WHITE)));
        logger().info(Component.text("\\  \\/ /  |/ /").color(TextColor.color(248, 142, 255)));
        logger().info(Component.text(" \\   /|    < ").color(TextColor.color(225, 142, 255)).append(Component.text("    Help poor children in Uganda!").color(NamedTextColor.GRAY)));
        logger().info(Component.text("  \\_/ |__|_ \\").color(TextColor.color(194, 124, 255)).append(Component.text("    ").append(Component.text("https://iccf-holland.org/").color(NamedTextColor.BLUE).decorate(TextDecoration.UNDERLINED).clickEvent(ClickEvent.openUrl("https://iccf-holland.org/")))));
        logger().info(Component.text("           \\/").color(TextColor.color(164, 86, 255)));
    }
}
