package com.tksimeji.visualkit.controller;

import org.bukkit.inventory.Inventory;

public sealed interface ChestGuiController extends ItemContainerGuiController<Inventory> permits ChestGuiControllerImpl {
}
