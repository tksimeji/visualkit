package com.tksimeji.visualkit.element;

import com.tksimeji.visualkit.Killable;
import com.tksimeji.visualkit.Tickable;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class Xmpl implements Killable, Tickable {
    private static final Set<Xmpl> instances = new HashSet<>();

    public static @NotNull Set<Xmpl> getInstances() {
        return new HashSet<>(instances);
    }

    static @NotNull Xmpl empty() {
        return new Xmpl(Component.empty());
    }

    static @NotNull Component component() {
        TextComponent.Builder plain = Component.text().color(NamedTextColor.WHITE);
        Arrays.stream(TextDecoration.values()).forEach(decoration -> plain.decoration(decoration, false));
        return plain.build();
    }

    private final @NotNull Component source;

    private @Nullable Component component;
    private @Nullable Object target;

    public Xmpl(@NotNull Component source) {
        this(source, null);
    }

    public Xmpl(@NotNull Component source, @Nullable Object target) {
        this.source = component().append(source);
        this.target = target;
        instances.add(this);
    }

    public @NotNull Component getSource() {
        return source;
    }

    public @Nullable Object getTarget() {
        return target;
    }

    public void setTarget(@Nullable Object target) {
        this.target = target;
    }

    public @NotNull Component asComponent() {
        return component != null ? component : getSource();
    }

    @Override
    public void tick() {
        if (target == null) {
            return;
        }

        component = source.asComponent();

        try {
            for (Field field : target.getClass().getDeclaredFields()) {
                field.setAccessible(true);

                Object value = field.get(target);

                String placeholder = "${" + field.getName() + "}";
                String replacement = value != null ? value.toString() : "null";

                component = component.replaceText(TextReplacementConfig.builder()
                        .matchLiteral(placeholder)
                        .replacement(replacement)
                        .build());
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void kill() {
        Xmpl.instances.remove(this);
    }
}
