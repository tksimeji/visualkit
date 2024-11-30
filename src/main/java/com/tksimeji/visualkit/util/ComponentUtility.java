package com.tksimeji.visualkit.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ComponentUtility {
    public static @NotNull Component space(int length) {
        return Component.text(" ".repeat(Math.max(0, length)));
    }

    public static boolean equals(@Nullable Component c1, @Nullable Component c2) {
        if (c1 == null || c2 == null) {
            return c1 == c2;
        }

        return LegacyComponentSerializer.legacySection().serialize(c1).equals(LegacyComponentSerializer.legacySection().serialize(c2));
    }
}
