package com.tksimeji.visualkit;

import com.tksimeji.visualkit.listener.InventoryListener;
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

    static final Set<VisualkitUI> sessions = new HashSet<>();

    public static @NotNull Visualkit plugin() {
        return instance;
    }

    public static @NotNull String version() {
        return instance.getPluginMeta().getVersion();
    }

    public static @NotNull ComponentLogger logger() {
        return instance.getComponentLogger();
    }

    public static @NotNull List<Component> art() {
        return List.of(Component.text("       __    ").color(TextColor.color(255, 86, 217)),
                Component.text("___  _|  | __").color(TextColor.color(255, 124, 255)).append(Component.text("    Visualkit").color(NamedTextColor.WHITE)),
                Component.text("\\  \\/ /  |/ /").color(TextColor.color(248, 142, 255)),
                Component.text(" \\   /|    < ").color(TextColor.color(225, 142, 255)).append(Component.text("    Help poor children in Uganda!").color(NamedTextColor.GRAY)),
                Component.text("  \\_/ |__|_ \\").color(TextColor.color(194, 124, 255)).append(Component.text("    ").append(Component.text("https://iccf-holland.org/").color(NamedTextColor.BLUE).decorate(TextDecoration.UNDERLINED).clickEvent(ClickEvent.openUrl("https://iccf-holland.org/")))),
                Component.text("           \\/").color(TextColor.color(164, 86, 255)));
    }

    public static @NotNull List<VisualkitUI> sessions() {
        return new ArrayList<>(sessions);
    }

    public static <T extends VisualkitUI> @NotNull List<T> sessions(@NotNull Class<T> clazz) {
        return sessions().stream()
                .filter(s -> clazz.isAssignableFrom(s.getClass()))
                .map(s -> (T) s)
                .toList();
    }

    public static @Nullable IInventoryUI<?> getInventoryUI(@Nullable Player player) {
        return sessions(IInventoryUI.class).stream().filter(s -> s.getPlayer() == player).findFirst().orElse(null);
    }

    public static @Nullable IPanelUI getPanelUI(@Nullable Player player) {
        return sessions(IPanelUI.class).stream().filter(s -> s.getPlayers().contains(player)).findFirst().orElse(null);
    }

    @Override
    public void onEnable() {
        instance = this;

        Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);
        Bukkit.getPluginManager().registerEvents(new ServerListener(), this);

        art().forEach(line -> logger().info(line));
    }
}
