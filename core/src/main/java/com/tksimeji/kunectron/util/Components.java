package com.tksimeji.kunectron.util;

import com.tksimeji.kunectron.Kunectron;
import com.tksimeji.kunectron.markupextension.MarkupExtensionException;
import com.tksimeji.kunectron.markupextension.context.Context;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Components {
    private static final @NotNull Pattern markupExtensionPattern = Pattern.compile("\\{([^}]*)}");

    public static @NotNull Component spaces(final int length) {
        return Component.text(" ".repeat(Math.max(0, length)));
    }

    public static @NotNull String plainText(final @NotNull ComponentLike component) {
        return PlainTextComponentSerializer.plainText().serialize(component.asComponent());
    }

    public static @NotNull String legacyComponent(final @NotNull ComponentLike component) {
        return LegacyComponentSerializer.legacySection().serialize(component.asComponent());
    }

    public static @NotNull Component markupExtension(final @NotNull ComponentLike component, final @NotNull Context<?> ctx) {
        if (!hasMarkupExtension(component)) {
            return component.asComponent();
        }

        return component.asComponent()
                .replaceText(builder -> builder.match(markupExtensionPattern)
                .replacement((result, componentBuilder) -> {
                    try {
                        return componentBuilder.content(Kunectron.getMarkupExtensionParser().parse(result.group(1)).evaluateDeep(ctx).toString());
                    } catch (MarkupExtensionException e) {
                        return Component.text(e.getMessage()).color(NamedTextColor.RED);
                    }
                }));
    }

    public static boolean hasMarkupExtension(final @Nullable ComponentLike component) {
        if (component == null) {
            return false;
        }

        String text = plainText(component);
        Matcher matcher = markupExtensionPattern.matcher(text);
        return matcher.find();
    }
}
