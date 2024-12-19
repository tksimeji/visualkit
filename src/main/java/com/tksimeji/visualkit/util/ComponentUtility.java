package com.tksimeji.visualkit.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public final class ComponentUtility {
    public static @NotNull Component empty() {
        TextComponent.Builder empty = Component.text().color(NamedTextColor.WHITE);
        Arrays.stream(TextDecoration.values()).forEach(decoration -> empty.decoration(decoration, false));
        return empty.build();
    }

    public static @NotNull Component spaces(int length) {
        return Component.text(" ".repeat(Math.max(0, length)));
    }

    public static @NotNull String content(@NotNull Component component) {
        return PlainTextComponentSerializer.plainText().serialize(component);
    }

    public static @NotNull String serialize(@NotNull Component component) {
        return LegacyComponentSerializer.legacySection().serialize(component).replace('§', '&');
    }

    public static @NotNull Component deserialize(@NotNull String string) {
        return LegacyComponentSerializer.legacySection().deserialize(string.replace('&', '§'));
    }

    public static boolean equals(@Nullable Component c1, @Nullable Component c2) {
        if (c1 == null || c2 == null) {
            return c1 == c2;
        }

        return LegacyComponentSerializer.legacySection().serialize(c1).equals(LegacyComponentSerializer.legacySection().serialize(c2));
    }
}
