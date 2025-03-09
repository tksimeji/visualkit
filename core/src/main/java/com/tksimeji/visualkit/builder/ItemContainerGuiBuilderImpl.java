package com.tksimeji.visualkit.builder;

import com.tksimeji.visualkit.IndexGroup;
import com.tksimeji.visualkit.controller.impl.GuiControllerImpl;
import com.tksimeji.visualkit.element.ItemElement;
import com.tksimeji.visualkit.hooks.ItemContainerGuiHooks;
import com.tksimeji.visualkit.policy.ItemSlotPolicy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class ItemContainerGuiBuilderImpl<B extends ItemContainerGuiBuilder<B, H>, H extends ItemContainerGuiHooks> extends ContainerGuiBuilderImpl<B, H> implements ItemContainerGuiBuilder<B, H> {
    protected final @NotNull Map<Integer, ItemElement> elements = new HashMap<>();

    protected final @NotNull Map<Integer, ItemSlotPolicy> policies = new HashMap<>();

    protected @Nullable ItemSlotPolicy defaultPolicy;

    protected @Nullable ItemSlotPolicy playerDefaultPolicy;

    @Override
    public @NotNull B element(final int index, final @NotNull ItemElement element) {
        elements.put(index, element);
        return (B) this;
    }

    @Override
    public @NotNull B element(final @NotNull IndexGroup[] indexGroups, final @NotNull ItemElement element) {
        for (int index : Arrays.stream(indexGroups).flatMap(indexGroup -> GuiControllerImpl.parseIndexGroup(indexGroup).stream()).collect(Collectors.toSet())) {
            element(index, element);
        }
        return (B) this;
    }

    @NotNull
    @Override
    public B policy(final int index, final @NotNull ItemSlotPolicy policy) {
        policies.put(index, policy);
        return (B) this;
    }

    @NotNull
    @Override
    public B policy(final @NotNull IndexGroup[] indexGroups, final @NotNull ItemSlotPolicy policy) {
        for (int index : Arrays.stream(indexGroups).flatMap(indexGroup -> GuiControllerImpl.parseIndexGroup(indexGroup).stream()).collect(Collectors.toSet())) {
            policy(index, policy);
        }
        return (B) this;
    }

    @NotNull
    @Override
    public B defaultPolicy(@NotNull ItemSlotPolicy defaultPolicy) {
        this.defaultPolicy = defaultPolicy;
        return (B) this;
    }

    @NotNull
    @Override
    public B playerDefaultPolicy(@NotNull ItemSlotPolicy playerDefaultPolicy) {
        this.playerDefaultPolicy = playerDefaultPolicy;
        return (B) this;
    }
}
