package com.tksimeji.visualkit;

import com.tksimeji.visualkit.adapter.Adapter;
import com.tksimeji.visualkit.adapter.V1_21_3;
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

import java.util.*;

public final class Visualkit extends JavaPlugin {
    private static Visualkit instance;

    static final @NotNull Set<IVisualkitUI> sessions = new HashSet<>();

    static final @NotNull Set<Adapter> adapters = Set.of(V1_21_3.INSTANCE);

    public static @NotNull Visualkit plugin() {
        return instance;
    }

    public static @NotNull String version() {
        return instance.getPluginMeta().getVersion();
    }

    public static @NotNull ComponentLogger logger() {
        return instance.getComponentLogger();
    }

    public static @Nullable Adapter adapter() {
        return adapters.stream()
                .filter(adapter -> Arrays.stream(adapter.supports()).anyMatch(support -> support.equals(Bukkit.getMinecraftVersion())))
                .findFirst()
                .orElse(null);
    }

    public static @NotNull List<IVisualkitUI> getSessions() {
        return new ArrayList<>(sessions);
    }

    public static <T extends IVisualkitUI> @NotNull List<T> getSessions(@NotNull Class<T> clazz) {
        return getSessions().stream()
                .filter(s -> clazz.isAssignableFrom(s.getClass()))
                .map(s -> (T) s)
                .toList();
    }

    public static <T extends IVisualkitUI> @Nullable T getSession(@NotNull Class<T> clazz, @Nullable Player player) {
        return getSessions(clazz).stream().filter(s -> switch (s) {
            case ContainerUI<?> i -> i.getPlayer() == player;
            case PanelUI i -> i.getPlayer() == player;
            case SharedPanelUI i -> i.isAudience(player);
            case null, default -> false;
        }).findFirst().orElse(null);
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

        if (adapter() == null) {
            logger().warn(Component.text("⚠").color(NamedTextColor.YELLOW)
                    .append(Component.text(":").color(NamedTextColor.GRAY))
                    .appendSpace()
                    .append(Component.text("No adapters were found for your server version. Some features that depend on the adapter will not work. Updating the plugin may solve the problem.").color(NamedTextColor.WHITE)));
        }
    }
}
