package com.tksimeji.visualkit.listener;

import com.tksimeji.visualkit.Visualkit;
import com.tksimeji.visualkit.element.Element;
import com.tksimeji.visualkit.event.anvil.AnvilGuiTextChangeEventImpl;
import com.tksimeji.visualkit.type.AnvilGuiType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public final class AnvilGuiListener implements Listener {
    @EventHandler
    public void onPrepareAnvil(final @NotNull PrepareAnvilEvent event) {
        Visualkit.getGuiControllers(AnvilGuiType.instance())
                .stream()
                .filter(controller -> controller.getInventory() == event.getInventory())
                .findFirst().ifPresent(controller -> {
                    ItemStack itemStack = event.getResult();

                    if (itemStack != null && itemStack.hasItemMeta()) {
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        Component component = itemMeta.hasDisplayName() ? itemMeta.displayName() : Component.empty();
                        String text = PlainTextComponentSerializer.plainText().serialize(component);

                        if (!controller.getText().equals(text)) {
                            controller.callEvent(new AnvilGuiTextChangeEventImpl(controller.getGui(), text));
                        }
                    }

                    if (controller.isOverwriteResultSlot()) {
                        event.setResult(null);
                    } else {
                        controller.setResultElement(itemStack != null ? Element.item(itemStack) : null);
                    }
                });
    }
}
