package com.tksimeji.visualkit.element;

import com.tksimeji.visualkit.Killable;
import com.tksimeji.visualkit.util.KillableArrayList;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public final class Lore extends KillableArrayList<@NotNull Xmpl> implements Killable {
    static @NotNull Lore empty() {
        return new Lore();
    }

    private @Nullable Object target;

    Lore(@NotNull Component... components) {
        Arrays.stream(components).forEach(component -> this.add(new Xmpl(component)));
    }

    public @Nullable Object getTarget() {
        return target;
    }

    public void setTarget(@Nullable Object target) {
        this.target = target;
        forEach(line -> line.setTarget(target));
    }

    public @NotNull List<Component> asComponentList() {
        return stream().map(Xmpl::asComponent).toList();
    }

    @Override
    public void kill() {
        clear();
    }
}
