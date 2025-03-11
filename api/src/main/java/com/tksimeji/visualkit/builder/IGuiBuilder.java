package com.tksimeji.visualkit.builder;

import com.tksimeji.visualkit.event.Event;
import com.tksimeji.visualkit.hooks.Hooks;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Internal
interface IGuiBuilder<B extends IGuiBuilder<B, H>, H extends Hooks> {
    @NotNull <E extends Event> B handler(final @NotNull Class<E> event, final @NotNull HandlerFunction<E> function);

    interface HandlerFunction<E extends Event> {
        void onEvent(@NotNull E event);
    }
}
