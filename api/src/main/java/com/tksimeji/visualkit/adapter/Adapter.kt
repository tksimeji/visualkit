package com.tksimeji.visualkit

import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.inventory.AnvilInventory

interface Adapter {
    fun target(): String

    fun fun_u32qi0(c1: Player, c2: Component): AnvilInventory

    fun fun_4td3lj()
}