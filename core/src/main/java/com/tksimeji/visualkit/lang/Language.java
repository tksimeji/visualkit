package com.tksimeji.visualkit.lang;

import com.tksimeji.visualkit.Visualkit;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Language extends HashMap<NamespacedKey, Component> {
    private static final @NotNull Map<Plugin, MinecraftLocale> locales = new HashMap<>();

    public static @NotNull Component translate(@NotNull NamespacedKey key, @NotNull MinecraftLocale locale, String... args) {
        Component component = Languages.getInstance(locale).get(key);

        for (String arg : args) {
            int index = arg.indexOf("=");

            if (index < 0) {
                throw new IllegalArgumentException();
            }

            String name = arg.substring(0, index);
            String value = arg.substring(index + 1);

            component = component.replaceText(TextReplacementConfig.builder()
                    .matchLiteral("${" + name + "}")
                    .replacement(value)
                    .build());
        }

        return component;
    }

    public static @NotNull Component translate(@NotNull String key, @NotNull MinecraftLocale locale, String... args) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        if (stackTrace.length <= 2) {
            throw new IllegalStateException();
        }

        StackTraceElement caller = ! stackTrace[2].getClassName().equals(Language.class.getName()) ? stackTrace[2] :
                Arrays.stream(stackTrace).filter(i -> {
                    try {
                        Class.forName(i.getClassName(), false, Visualkit.plugin().getClass().getClassLoader());
                        return false;
                    } catch (ClassNotFoundException e) {
                        return true;
                    }
                }).findFirst().orElseThrow(IllegalStateException::new);

        if (caller == null) {
            throw new IllegalStateException();
        }

        Plugin plugin = Arrays.stream(Bukkit.getPluginManager().getPlugins())
                .filter(p -> caller.getClassLoaderName().equals(p.getClass().getClassLoader().getName()))
                .findFirst()
                .orElse(null);

        if (plugin == null) {
            throw new UnsupportedOperationException();
        }

        return translate(new NamespacedKey(plugin, key), locale, args);
    }

    public static @NotNull Component translate(@NotNull NamespacedKey key, @NotNull CommandSender sender, String... args) {
        return translate(key, Objects.requireNonNull(MinecraftLocale.of(sender)), args);
    }

    public static @NotNull Component translate(@NotNull String key, @NotNull CommandSender sender, String... args) {
        return translate(key, Objects.requireNonNull(MinecraftLocale.of(sender)), args);
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
