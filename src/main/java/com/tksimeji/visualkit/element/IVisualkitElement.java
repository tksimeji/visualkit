package com.tksimeji.visualkit.element;

import com.tksimeji.visualkit.Killable;
import com.tksimeji.visualkit.api.Click;
import com.tksimeji.visualkit.api.Mouse;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface IVisualkitElement<T extends IVisualkitElement<T>> extends Killable {
    /**
     * Gets the item type.
     *
     * @return Item type
     */
    @NotNull Material type();

    /**
     * Gets the title.
     *
     * @return Title
     */
    @NotNull Component title();

    /**
     * Sets the title.
     *
     * @param title Title
     * @return Updated element
     */
    @NotNull T title(@Nullable Component title);

    /**
     * Gets the lore.
     *
     * @return Lore
     */
    @NotNull List<Component> lore();

    /**
     * Sets the lore.
     *
     * @param components Lore
     * @return Updated element
     */
    @NotNull T lore(@NotNull Component... components);

    /**
     * Gets the stack count.
     *
     * @return Stack count
     */
    int stack();

    /**
     * Sets the stack count.
     *
     * @param stack Stack count
     * @return Updated element
     */
    @NotNull T stack(int stack);

    /**
     * Gets the custom model data.
     *
     * @return Custom model data
     */
    int model();

    /**
     * Sets the custom model data.
     *
     * @param model Custom model data
     * @return Updated element
     */
    @NotNull T model(int model);

    /**
     * Gets whether the item has an enchantment aura.
     *
     * @return True if has aura
     */
    boolean aura();

    /**
     * Sets whether to have an enchantment aura.
     *
     * @param aura True if has aura
     * @return Updated element
     */
    @NotNull T aura(boolean aura);


    /**
     * Gets the handler to be called on click.
     *
     * @return Handler
     */
    @Nullable Handler handler();

    /**
     * Sets the handler to be called on click.
     *
     * @param handler Handler
     * @return Updated element
     */
    @NotNull T handler(@NotNull Handler1 handler);

    /**
     * Sets the handler to be called on click.
     *
     * @param handler Handler
     * @return Updated element
     */
    @NotNull T handler(@NotNull Handler2 handler);

    /**
     * Gets the sound to be played on click.
     *
     * @return Sound
     */
    @Nullable Sound sound();

    /**
     * Gets the volume of the sound being played.
     *
     * @return Sound volume
     */
    float volume();

    /**
     * Gets the pitch of the sound being played.
     */
    float pitch();

    /**
     * Sets the sound to be played on click.
     *
     * @param sound Sound
     * @return Updated element
     */
    @NotNull T sound(@NotNull Sound sound);

    /**
     * Sets the sound to be played on click.
     *
     * @param sound Sound
     * @param volume Sound volume
     * @param pitch Sound pitch
     * @return Updated element
     */
    @NotNull T sound(@NotNull Sound sound, float volume, float pitch);

    interface Handler {}

    interface Handler1 extends Handler {
        void onClick(int slot, @NotNull Click click, @NotNull Mouse mouse);
    }

    interface Handler2 extends Handler {
        void onClick();
    }
}
