package com.tksimeji.kunectron.listener;

import com.tksimeji.kunectron.Kunectron;
import com.tksimeji.kunectron.element.Element;
import com.tksimeji.kunectron.event.anvil.AnvilGuiTextChangeEventImpl;
import com.tksimeji.kunectron.type.AnvilGuiType;
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
        Kunectron.getGuiControllers(AnvilGuiType.instance())
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
