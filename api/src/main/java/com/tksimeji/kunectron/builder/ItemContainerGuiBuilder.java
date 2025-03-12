package com.tksimeji.kunectron.builder;

import com.tksimeji.kunectron.IndexGroup;
import com.tksimeji.kunectron.element.ItemElement;
import com.tksimeji.kunectron.hooks.ItemContainerGuiHooks;
import com.tksimeji.kunectron.policy.ItemSlotPolicy;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

interface ItemContainerGuiBuilder<B extends ItemContainerGuiBuilder<B, H>, H extends ItemContainerGuiHooks> extends ContainerGuiBuilder<B, H> {
    @Contract("_, _ -> this")
    @NotNull B element(final int index, final @NotNull ItemElement element);

    @Contract("_, _ -> this")
    @NotNull B element(final @NotNull IndexGroup[] indexGroups, final @NotNull ItemElement element);

    @Contract("_, _ -> this")
    @NotNull B policy(final int index, final @NotNull ItemSlotPolicy policy);

    @Contract("_, _ -> this")
    @NotNull B policy(final @NotNull IndexGroup[] indexGroups, final @NotNull ItemSlotPolicy policy);

    @Contract("_ -> this")
    @NotNull B defaultPolicy(final @NotNull ItemSlotPolicy defaultPolicy);

    @Contract("_ -> this")
    @NotNull B playerDefaultPolicy(final @NotNull ItemSlotPolicy playerDefaultPolicy);
}
