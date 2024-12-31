package com.tksimeji.visualkit.adapter

import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.inventory.AnvilInventory

interface Adapter {
    fun supports(): Array<String>

    fun fun_u32qi0(c1: Player, c2: Component): AnvilInventory
}