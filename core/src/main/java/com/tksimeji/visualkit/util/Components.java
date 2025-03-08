package com.tksimeji.visualkit.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public final class Components {
    public static @NotNull Component empty() {
        TextComponent.Builder empty = Component.text().color(NamedTextColor.WHITE);
        Arrays.stream(TextDecoration.values()).forEach(decoration -> empty.decoration(decoration, false));
        return empty.build();
    }

    public static @NotNull Component spaces(final int length) {
        return Component.text(" ".repeat(Math.max(0, length)));
    }

    public static @NotNull String plainText(final @NotNull ComponentLike component) {
        return PlainTextComponentSerializer.plainText().serialize(component.asComponent());
    }

    public static @NotNull String legacyComponent(final @NotNull ComponentLike component) {
        return LegacyComponentSerializer.legacySection().serialize(component.asComponent());
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

        return serialize(c1).equals(serialize(c2));
    }
}
