package com.tksimeji.kunectron.controller;

import com.tksimeji.kunectron.MerchantGui;
import com.tksimeji.kunectron.Kunectron;
import com.tksimeji.kunectron.controller.impl.ContainerGuiControllerImpl;
import com.tksimeji.kunectron.element.TradeElement;
import com.tksimeji.kunectron.element.TradeElementImpl;
import com.tksimeji.kunectron.event.merchant.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public final class MerchantGuiControllerImpl extends ContainerGuiControllerImpl<MerchantInventory> implements MerchantGuiController {
    private final @NotNull Player player;

    private final @NotNull List<TradeElement> elements = new ArrayList<>();

    private final @NotNull Merchant merchant = Bukkit.createMerchant();

    private @Nullable MerchantInventory inventory;

    private int update;

    public MerchantGuiControllerImpl(final @NotNull Object gui) {
        super(gui);

        player = getDeclarationOrThrow(gui, MerchantGui.Player.class, Player.class).getLeft();

        update();
        update = 0;

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
        callEvent(new MerchantGuiInitEventImpl(gui));
    }

    @Override
    public @NotNull Player getPlayer() {
        return player;
    }

    @Override
    public @NotNull MerchantInventory getInventory() {
        if (inventory == null) {
            throw new IllegalStateException();
        }
        return inventory;
    }

    @Override
    public @Nullable TradeElement getElement(final int index) {
        if (index >= elements.size()) {
            return null;
        }
        return elements.get(index);
    }

    @Override
    public @NotNull List<TradeElement> getElements() {
        return new ArrayList<>(elements);
    }

    @Override
    public boolean setElement(final int index, final @NotNull TradeElement element) {
        if (index < 0 || index > elements.size()) {
            return false;
        }

        if (index == elements.size()) {
            elements.add(element);
        } else {
            elements.set(index, element);
        }

        if (element instanceof TradeElementImpl impl) {
            impl.registerObserver(this);
        }

        update();
        return true;
    }

    @Override
    public void addElement(final @NotNull TradeElement element) {
        setElement(elements.size(), element);
    }

    @Override
    public void removeElement(final int index) {
        if (getElement(index) instanceof TradeElementImpl impl) {
            impl.unregisterObserver(this);
        }
        elements.remove(index);
        update();
    }

    @Override
    public void insertElement(final int index, final @NotNull TradeElement element) {
        if (index > elements.size()) {
            return;
        }

        if (element instanceof TradeElementImpl impl) {
            impl.registerObserver(this);
        }

        elements.add(index, element);
        update();
    }

    @Override
    public boolean select(final int index) {
        TradeElement element = elements.get(index);
        if (!element.canSelect()) {
            return true;
        }
        return callEvent(new MerchantGuiSelectEventImpl(gui, index, element));
    }

    @Override
    public boolean purchase(final int index) {
        TradeElement element = elements.get(index);
        if (!element.canPurchase()) {
            return true;
        }
        return callEvent(new MerchantGuiPurchaseEventImpl(gui, index, element));
    }

    @Override
    public void close() {
        if (0 < update) {
            update--;
            return;
        }

        callEvent(new MerchantGuiCloseEventImpl(gui));
        super.close();
    }

    @Override
    public void update() {
        merchant.setRecipes(elements.stream().map(TradeElement::create).toList());
        update++;
        Bukkit.getScheduler().runTask(Kunectron.plugin(), () -> {
            player.openInventory(MenuType.MERCHANT.builder()
                    .title(getDeclarationOrDefault(gui, MerchantGui.Title.class, ComponentLike.class, Component.empty()).getLeft().asComponent())
                    .merchant(merchant)
                    .build(player));

            inventory = (MerchantInventory) player.getOpenInventory().getTopInventory();
        });
    }
}
