package com.tksimeji.visualkit.xmpl;

import com.tksimeji.visualkit.Killable;
import com.tksimeji.visualkit.Tickable;
import com.tksimeji.visualkit.util.Components;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Deprecated(forRemoval = true)
@ApiStatus.ScheduledForRemoval(inVersion = "1.0.0")
public final class Xmpl implements ComponentLike, Killable, Tickable {
    private static final @NotNull Set<Xmpl> instances = new HashSet<>();

    public static @NotNull Set<Xmpl> getInstances() {
        return new HashSet<>(instances);
    }

    public static @NotNull Xmpl empty() {
        return new Xmpl(Components.empty());
    }

    private final @NotNull Component source;

    private @Nullable Component component;
    private @Nullable XmplTarget target;

    public Xmpl(@NotNull Component source) {
        this(source, null);
    }

    public Xmpl(@NotNull Component source, @Nullable XmplTarget target) {
        this.source = Components.empty().append(source);
        this.target = target;
        instances.add(this);
    }

    public @NotNull Component getSource() {
        return source;
    }

    public @Nullable Object getTarget() {
        return target;
    }

    public void setTarget(@Nullable XmplTarget target) {
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

        target.getXmplMap().forEach((key, value) -> {
            String placeholder = "${" + key + "}";
            Component replacement = value instanceof Component c ? c :
                    value instanceof String s ? LegacyComponentSerializer.legacySection().deserialize(s) :
                            Component.text(Optional.ofNullable(value).orElse("null").toString());

            component = component.replaceText(TextReplacementConfig.builder()
                    .matchLiteral(placeholder)
                    .replacement(replacement)
                    .build());
        });
    }

    @Override
    public void kill() {
        Xmpl.instances.remove(this);
    }
}
