package com.tksimeji.visualkit.lang;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@ApiStatus.ScheduledForRemoval(inVersion = "1.0.0")
@Deprecated(forRemoval = true)
public final class Languages {
    static final @NotNull Set<Language> instances = new HashSet<>();

    private static final @NotNull String directory = "lang";

    static @NotNull Language getInstance(@NotNull MinecraftLocale locale) {
        return instances.stream().filter(lang -> lang.getLocale() == locale).findFirst().orElse(new Language(locale));
    }

    public static void mount(@NotNull Plugin plugin) {
        try (JarFile jar = new JarFile(plugin.getClass().getProtectionDomain().getCodeSource().getLocation().getPath())) {
            Enumeration<JarEntry> entries = jar.entries();

            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();

                String path = entry.getName();

                if (! path.startsWith(directory + "/")) {
                    continue;
                }

                String name = Paths.get(path).getFileName().toString();
                String extension = null;

                int lastIndexOfDot = name.lastIndexOf('.');

                if (0 < lastIndexOfDot && lastIndexOfDot < name.length() - 1) {
                    extension = name.substring(lastIndexOfDot + 1);
                    name = name.substring(0, lastIndexOfDot);
                }

                if (extension == null || ! extension.equals("json")) {
                    continue;
                }

                MinecraftLocale locale = MinecraftLocale.of(name);

                if (locale == null) {
                    continue;
                }

                Language lang = getInstance(locale);

                try (InputStream input = jar.getInputStream(entry)) {
                    InputStreamReader reader = new InputStreamReader(input);
                    JsonElement json = JsonParser.parseReader(reader);

                    if (! (json instanceof JsonObject obj)) {
                        continue;
                    }

                    obj.keySet().forEach(key -> lang.put(new NamespacedKey(plugin, key), LegacyComponentSerializer.legacySection().deserialize((obj.get(key).getAsString().replace('&', '§')))));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void unmount(@NotNull Plugin plugin) {
        instances.forEach(lang -> lang.entrySet().removeIf(e -> e.getKey().namespace().equals(plugin.getName())));
    }
}
