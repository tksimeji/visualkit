package com.tksimeji.visualkit.element;

import com.tksimeji.visualkit.Killable;
import com.tksimeji.visualkit.Tickable;
import com.tksimeji.visualkit.util.ComponentUtility;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public final class Xmpl implements ComponentLike, Killable, Tickable {
    private static final @NotNull Set<Xmpl> instances = new HashSet<>();

    public static @NotNull Set<Xmpl> getInstances() {
        return new HashSet<>(instances);
    }

    static @NotNull Xmpl empty() {
        return new Xmpl(Component.empty());
    }

    private final @NotNull Component source;

    private @Nullable Component component;
    private @Nullable Object target;

    public Xmpl(@NotNull Component source) {
        this(source, null);
    }

    public Xmpl(@NotNull Component source, @Nullable Object target) {
        this.source = ComponentUtility.empty().append(source);
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

    @Override
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
                Component replacement = value instanceof Component c ? c :
                        LegacyComponentSerializer.legacySection().deserialize(Optional.ofNullable(value).orElse("null").toString().replace('&', '§'));

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
