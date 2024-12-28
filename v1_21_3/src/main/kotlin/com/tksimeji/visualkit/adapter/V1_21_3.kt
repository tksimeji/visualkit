package com.tksimeji.visualkit.v1_21_3

import com.tksimeji.visualkit.Adapter
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import net.minecraft.core.BlockPosition
import net.minecraft.network.chat.IChatBaseComponent
import net.minecraft.network.protocol.game.PacketPlayOutOpenWindow
import net.minecraft.world.inventory.ContainerAccess
import net.minecraft.world.inventory.ContainerAnvil
import org.bukkit.craftbukkit.v1_21_R2.entity.CraftPlayer
import org.bukkit.craftbukkit.v1_21_R2.event.CraftEventFactory
import org.bukkit.entity.Player
import org.bukkit.inventory.AnvilInventory
import org.bukkit.inventory.Merchant

object V1_21_3: Adapter {
    override fun target(): String {
        return "1.21.3"
    }

    override fun fun_u32qi0(c1: Player, c2: Component): AnvilInventory {
        val p = (c1 as CraftPlayer).handle

        CraftEventFactory.handleInventoryCloseEvent(p)
        p.cd = p.cc

        val id = p.nextContainerCounter()
        val container = ContainerAnvil(id, p.gi(), ContainerAccess.a(p.y(), BlockPosition.d(BlockPosition.a(0, 0, 0))))

        container.checkReachable = false
        container.title = IChatBaseComponent.c(LegacyComponentSerializer.legacySection().serialize(c2))

        val inventory = container.bukkitView.topInventory

        p.f.sendPacket(PacketPlayOutOpenWindow(container.l, container.a(), container.title))
        p.cd = container
        p.a(container)

        return inventory
    }

    override fun fun_4td3lj() {
        TODO("Not yet implemented")
    }
}