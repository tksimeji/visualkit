package com.tksimeji.visualkit.lang;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Language extends HashMap<NamespacedKey, Component> {
    private static final @NotNull Map<Plugin, MinecraftLocale> locales = new HashMap<>();

    public static @NotNull Component translate(@NotNull MinecraftLocale locale, @NotNull NamespacedKey key) {
        return Languages.getInstance(locale).get(key);
    }

    public static @NotNull Component translate(@NotNull MinecraftLocale locale, @NotNull String key) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        if (stackTrace.length <= 2) {
            throw new IllegalStateException();
        }

        StackTraceElement caller = ! stackTrace[2].getClassName().equals(Language.class.getName()) ? stackTrace[2] :
                Arrays.stream(stackTrace).filter(i -> ! i.getClassName().equals(Language.class.getName())).findFirst().orElseThrow(IllegalStateException::new);

        Plugin plugin = Arrays.stream(Bukkit.getPluginManager().getPlugins())
                .filter(p -> {
                    try {
                        Class.forName(caller.getClassName(), false, p.getClass().getClassLoader());
                        return true;
                    } catch (ClassNotFoundException e) {
                        return false;
                    }
                })
                .findFirst()
                .orElse(null);

        if (plugin == null) {
            throw new UnsupportedOperationException();
        }

        return translate(locale, new NamespacedKey(plugin, key));
    }

    public static @NotNull Component translate(@NotNull CommandSender player, @NotNull NamespacedKey key) {
        return translate(Objects.requireNonNull(MinecraftLocale.of(player)), key);
    }

    public static @NotNull Component translate(@NotNull CommandSender player, @NotNull String key) {
        return translate(Objects.requireNonNull(MinecraftLocale.of(player)), key);
    }

    public static void locale(@NotNull Plugin plugin, @NotNull MinecraftLocale locale) {
        locales.put(plugin, locale);
    }

    private final @NotNull MinecraftLocale locale;

    Language(@NotNull MinecraftLocale locale) {
        this.locale = locale;
        Languages.instances.add(this);
    }

    public @NotNull MinecraftLocale getLocale() {
        return locale;
    }

    @Override
    public @NotNull Component get(Object key) {
        if (! (key instanceof NamespacedKey namespacedKey)) {
            return Component.empty();
        }

        Plugin plugin = Bukkit.getPluginManager().getPlugin(namespacedKey.getNamespace());

        if (plugin == null) {
            throw new IllegalArgumentException();
        }

        MinecraftLocale locale = locales.get(plugin);

        return Optional.ofNullable(super.get(key)).orElse(locale != null ? Languages.getInstance(locale).get(namespacedKey) : Component.text(namespacedKey.asString()));
    }
}
