package com.tksimeji.kunectron.adapter

import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.inventory.AnvilInventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.plugin.java.JavaPlugin

interface Adapter {
    fun supports(): Array<String>

    fun fun_u32qi0(c1: Player, c2: Component): AnvilInventory

    fun fun_adp3uc(c1: ItemStack, c2: ItemMeta, c3: JavaPlugin)
}