package com.tksimeji.kunectron.builder;

import com.tksimeji.kunectron.event.Event;
import com.tksimeji.kunectron.event.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

abstract class AbstractGui {
    private final @NotNull Map<Class<? extends Event>, IGuiBuilder.HandlerFunction<?>> handlers;

    public AbstractGui(final @NotNull Map<Class<? extends Event>, IGuiBuilder.HandlerFunction<?>> handlers) {
        this.handlers = handlers;
    }

    @Handler
    public <E extends Event> void onEvent(final @NotNull E event) {
        List<? extends IGuiBuilder.HandlerFunction<E>> handlers = this.handlers.entrySet().stream()
                .filter(handler -> handler.getKey().isAssignableFrom(event.getClass()))
                .map(handler -> (IGuiBuilder.HandlerFunction<E>) handler.getValue())
                .toList();

        for (IGuiBuilder.HandlerFunction<E> handler : handlers) {
            handler.onEvent(event);
        }
    }
}
