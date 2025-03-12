package com.tksimeji.kunectron.builder;

import com.google.common.base.Preconditions;
import com.tksimeji.kunectron.event.Event;
import com.tksimeji.kunectron.hooks.Hooks;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public abstract class IGuiBuilderImpl<B extends IGuiBuilder<B, H>, H extends Hooks> implements IGuiBuilder<B, H> {
    protected final @NotNull Map<Class<? extends Event>, HandlerFunction<?>> handlers = new HashMap<>();

    @Override
    public <E extends Event> @NotNull B handler(final @NotNull Class<E> event, final @NotNull HandlerFunction<E> function) {
        Preconditions.checkArgument(event != null, "Event cannot be null.");
        Preconditions.checkArgument(function != null, "Function cannot be null.");
        handlers.put(event, function);
        return (B) this;
    }
}
