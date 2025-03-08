package com.tksimeji.visualkit.controller;

import com.tksimeji.visualkit.MerchantGui;
import com.tksimeji.visualkit.Visualkit;
import com.tksimeji.visualkit.controller.impl.ContainerGuiControllerImpl;
import com.tksimeji.visualkit.element.TradeElement;
import com.tksimeji.visualkit.element.TradeElementImpl;
import com.tksimeji.visualkit.event.MerchantGuiEvents;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.MenuType;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantInventory;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class MerchantGuiControllerImpl extends ContainerGuiControllerImpl<MerchantInventory> implements MerchantGuiController {
    private final @NotNull List<TradeElement> elements = new ArrayList<>();

    private final @NotNull Player player;

    private final @NotNull Merchant merchant = Bukkit.createMerchant();

    private @NotNull MerchantInventory inventory;

    private boolean updated;

    public MerchantGuiControllerImpl(final @NotNull Object gui) {
        super(gui);

        player = getDeclarationOrThrow(gui, MerchantGui.Player.class, Player.class).getLeft();

        update();
        updated = false;

        Map<Integer, TradeElement> elementMap = new TreeMap<>();
        List<TradeElement> elementList = new ArrayList<>();

        for (Pair<TradeElement, MerchantGui.Element> declaration : getDeclarations(gui, MerchantGui.Element.class, TradeElement.class)) {
            MerchantGui.Element annotation = declaration.getRight();
            if (annotation.index() != -1) {
                elementMap.put(annotation.index(), declaration.getLeft());
            } else {
                elementList.add(declaration.getLeft());
            }
        }

        for (Map.Entry<Integer, TradeElement> element : elementMap.entrySet()) {
            setElement(element.getKey(), element.getValue());
        }

        for (TradeElement element : elementList) {
            addElement(element);
        }
    }

    @Override
    public void init() {
        callEvent(new MerchantGuiEvents.InitEvent(gui));
    }

    @Override
    public @NotNull Player getPlayer() {
        return player;
    }

    @Override
    public @NotNull MerchantInventory getInventory() {
        return inventory;
    }

    @Override
    public @NotNull TradeElement getElement(final int index) {
        return elements.get(index);
    }

    @Override
    public @NotNull List<TradeElement> getElements() {
        return new ArrayList<>(elements);
    }

    @Override
    public boolean setElement(int index, @NotNull TradeElement element) {
        if (index < 0 || index > elements.size()) {
            return false;
        }

        if (index == elements.size()) {
            elements.add(element);
        } else {
            elements.set(index, element);
        }
        update();
        return true;
    }

    @Override
    public void addElement(@NotNull TradeElement element) {
        setElement(elements.size(), element);
    }

    @Override
    public void removeElement(int index) {
        elements.remove(index);
        update();
    }

    @Override
    public void insertElement(int index, @NotNull TradeElement element) {
        elements.add(index, element);
        update();
    }

    @Override
    public void close() {
        if (updated) {
            updated = false;
            return;
        }

        callEvent(new MerchantGuiEvents.CloseEvent(gui));

        elements.stream()
                .filter(element -> element instanceof TradeElementImpl)
                .forEach(element -> ((TradeElementImpl) element).removeObserver(this));
        super.close();
    }

    @Override
    public boolean select(final int index, final @NotNull TradeElement element) {
        if (!element.canSelect()) {
            return true;
        }

        return callEvent(new MerchantGuiEvents.SelectEvent(gui, index, element));
    }

    @Override
    public boolean purchase(final int index, final @NotNull TradeElement element) {
        if (!element.canPurchase()) {
            return true;
        }

        return callEvent(new MerchantGuiEvents.PurchaseEvent(gui, index, element));
    }

    @Override
    public void update() {
        merchant.setRecipes(elements.stream().map(TradeElement::create).toList());
        updated = true;
        Bukkit.getScheduler().runTask(Visualkit.plugin(), () -> {
            player.openInventory(MenuType.MERCHANT.builder()
                    .title(getDeclarationOrDefault(gui, MerchantGui.Title.class, ComponentLike.class, Component.empty()).getLeft().asComponent())
                    .merchant(merchant)
                    .build(player));

            inventory = (MerchantInventory) player.getOpenInventory().getTopInventory();
        });
    }
}
