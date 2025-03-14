package com.tksimeji.kunectron.builder;

import com.tksimeji.kunectron.event.GuiEvent;
import com.tksimeji.kunectron.hooks.Hooks;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Internal
interface IGuiBuilder<B extends IGuiBuilder<B, H>, H extends Hooks> {
    @NotNull <E extends GuiEvent> B handler(final @NotNull Class<E> event, final @NotNull IGuiBuilder.HandlerFunction1<E> function);

    @NotNull <E extends GuiEvent> B handler(final @NotNull Class<E> event, final @NotNull IGuiBuilder.HandlerFunction2<E, H> function);

    @NotNull <E extends GuiEvent> B handlerAsync(final @NotNull Class<E> event, final @NotNull IGuiBuilder.HandlerFunction1<E> function);

    @NotNull <E extends GuiEvent> B handlerAsync(final @NotNull Class<E> event, final @NotNull IGuiBuilder.HandlerFunction2<E, H> function);

    interface HandlerFunction {
    }

    interface HandlerFunction1<E extends GuiEvent> extends HandlerFunction {
        void onEvent(@NotNull E event);
    }

    interface HandlerFunction2<E extends GuiEvent, H> extends HandlerFunction {
        void onEvent(@NotNull E event, @NotNull H hooks);
    }
}