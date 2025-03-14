package com.tksimeji.kunectron.builder;

import com.google.common.base.Preconditions;
import com.tksimeji.kunectron.event.GuiEvent;
import com.tksimeji.kunectron.hooks.Hooks;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class IGuiBuilderImpl<B extends IGuiBuilder<B, H>, H extends Hooks> implements IGuiBuilder<B, H> {
    protected final @NotNull List<HandlerInfo> handlers = new ArrayList<>();

    @Override
    public <E extends GuiEvent> @NotNull B handler(final @NotNull Class<E> event, final @NotNull HandlerFunction1<E> function) {
        Preconditions.checkArgument(event != null, "Event cannot be null.");
        Preconditions.checkArgument(function != null, "Function cannot be null.");
        handlers.add(new HandlerInfoImpl(event, function, false));
        return (B) this;
    }

    @Override
    public <E extends GuiEvent> @NotNull B handler(@NotNull Class<E> event, @NotNull HandlerFunction2<E, H> function) {
        Preconditions.checkArgument(event != null, "Event cannot be null.");
        Preconditions.checkArgument(function != null, "Function cannot be null.");
        handlers.add(new HandlerInfoImpl(event, function, false));
        return (B) this;
    }

    @Override
    public <E extends GuiEvent> @NotNull B handlerAsync(@NotNull Class<E> event, @NotNull HandlerFunction1<E> function) {
        Preconditions.checkArgument(event != null, "Event cannot be null.");
        Preconditions.checkArgument(function != null, "Function cannot be null.");
        handlers.add(new HandlerInfoImpl(event, function, true));
        return (B) this;
    }

    @Override
    public <E extends GuiEvent> @NotNull B handlerAsync(@NotNull Class<E> event, @NotNull HandlerFunction2<E, H> function) {
        Preconditions.checkArgument(event != null, "Event cannot be null.");
        Preconditions.checkArgument(function != null, "Function cannot be null.");
        handlers.add(new HandlerInfoImpl(event, function, true));
        return (B) this;
    }

    public interface HandlerInfo {
        @NotNull Class<? extends GuiEvent> event();

        @NotNull HandlerFunction function();

        boolean async();
    }

    private record HandlerInfoImpl(@NotNull Class<? extends GuiEvent> event, @NotNull HandlerFunction function, boolean async) implements HandlerInfo {
    }
}