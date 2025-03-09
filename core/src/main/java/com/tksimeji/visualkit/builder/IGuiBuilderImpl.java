package com.tksimeji.visualkit.builder;

import com.tksimeji.visualkit.event.Event;
import com.tksimeji.visualkit.hooks.Hooks;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public abstract class IGuiBuilderImpl<B extends IGuiBuilder<B, H>, H extends Hooks> implements IGuiBuilder<B, H> {
    protected final @NotNull Map<Class<? extends Event>, HandlerFunction<?>> handlers = new HashMap<>();

    @Override
    public <E extends Event> @NotNull B handler(final @NotNull Class<E> event, final @NotNull HandlerFunction<E> function) {
        handlers.put(event, function);
        return (B) this;
    }
}
