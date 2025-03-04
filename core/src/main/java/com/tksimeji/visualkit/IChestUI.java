package com.tksimeji.visualkit;

import com.tksimeji.visualkit.api.Size;
import com.tksimeji.visualkit.element.IVisualkitElement;
import com.tksimeji.visualkit.policy.PolicyTarget;
import com.tksimeji.visualkit.policy.SlotPolicy;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

@Deprecated(since = "1.0.0", forRemoval = true)
public interface IChestUI extends IContainerUI<Inventory> {
    /**
     * Defines the size of the chest.
     *
     * @return Chest size
     */
    @NotNull Size size();

    /**
     * Gets the element of a given slot.
     *
     * @param slot Slot index number
     * @return GUI element
     */
    @Nullable IVisualkitElement<?> getElement(int slot);

    /**
     * Gets the element list.
     *
     * @return Element list
     */
    @NotNull List<IVisualkitElement<?>> getElements();

    /**
     * Gets the element map.
     *
     * @return Element map
     */
    @NotNull Map<Integer, IVisualkitElement<?>> getElementMap();

    /**
     * Sets an element in any slot.
     *
     * @param slot Slot index number
     * @param element The element to set, if null it will become an empty slot
     */
    void setElement(int slot, @Nullable IVisualkitElement<?> element);

    /**
     * Sets on element in any slot.
     *
     * @param slot    Slot index number
     * @param element The element to set, if null it will become an empty slot
     */
    void setElement(int slot, @Nullable ItemStack element);

    /**
     * Gets the policy of a given slot.
     *
     * @param slot Slot index number
     * @return Slot Policy
     */
    @NotNull SlotPolicy getPolicy(int slot);

    /**
     * Gets the policy of a given slot.
     *
     * @param slot Slot index number
     * @param target Target
     * @return Slot Policy
     */
    @NotNull SlotPolicy getPolicy(int slot, @NotNull PolicyTarget target);

    /**
     * Sets a policy in any slot.
     *
     * @param slot Slot index number
     * @param policy Slot policy
     */
    void setPolicy(int slot, @NotNull SlotPolicy policy);

    /**
     * Sets a policy in any slot.
     *
     * @param slot Slot index number
     * @param target Target
     * @param policy Slot policy
     */
    void setPolicy(int slot, @NotNull SlotPolicy policy, @NotNull PolicyTarget target);
}
