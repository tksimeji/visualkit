package com.tksimeji.kunectron.builder;

import com.tksimeji.kunectron.event.Event;
import com.tksimeji.kunectron.hooks.Hooks;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Internal
interface IGuiBuilder<B extends IGuiBuilder<B, H>, H extends Hooks> {
    @NotNull <E extends Event> B handler(final @NotNull Class<E> event, final @NotNull HandlerFunction<E> function);

    interface HandlerFunction<E extends Event> {
        void onEvent(@NotNull E event);
    }
}
