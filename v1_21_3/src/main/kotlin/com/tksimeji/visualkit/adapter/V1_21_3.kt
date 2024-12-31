package com.tksimeji.visualkit.adapter

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import net.minecraft.core.BlockPos
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket
import net.minecraft.world.inventory.AnvilMenu
import net.minecraft.world.inventory.ContainerLevelAccess
import org.bukkit.craftbukkit.entity.CraftPlayer
import org.bukkit.craftbukkit.event.CraftEventFactory
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.AnvilInventory

object V1_21_3: Adapter {
    override fun supports(): Array<String> {
        return arrayOf("1.21", "1.21.1", "1.21.3", "1.21.4")
    }

    override fun fun_u32qi0(c1: Player, c2: Component): AnvilInventory {
        val p = (c1 as CraftPlayer).handle

        CraftEventFactory.handleInventoryCloseEvent(p, InventoryCloseEvent.Reason.UNKNOWN)
        p.containerMenu = p.inventoryMenu

        val id = p.nextContainerCounter()
        val container = AnvilMenu(id, p.inventory, ContainerLevelAccess.create(p.serverLevel(), BlockPos.of(BlockPos.asLong(0, 0, 0))))

        container.checkReachable = false
        container.title = net.minecraft.network.chat.Component.translatable(LegacyComponentSerializer.legacySection().serialize(c2))

        val inventory = container.bukkitView.topInventory

        p.connection.sendPacket(ClientboundOpenScreenPacket(container.containerId, container.type, container.title))
        p.containerMenu = container
        p.initMenu(container)

        return inventory
    }
}