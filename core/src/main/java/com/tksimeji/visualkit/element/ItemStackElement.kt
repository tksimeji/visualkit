package com.tksimeji.visualkit.element

import com.tksimeji.visualkit.element.IVisualkitElement.Handler
import com.tksimeji.visualkit.util.ComponentUtility
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import java.lang.Deprecated

@Deprecated(since = "1.0.0", forRemoval = true)
class ItemStackElement internal constructor(private val itemStack: ItemStack):
    IVisualkitElement<ItemStackElement> {
    private var handler: Handler? = null

    private var sound: Sound? = null
    private var volume: Float = 1.0f
    private var pitch: Float = 1.0f

    override fun type(): Material {
        return itemStack.type
    }

    override fun title(): Component {
        return itemStack.itemMeta?.displayName() ?: Component.empty()
    }

    override fun title(title: Component?): ItemStackElement {
        itemStack.setItemMeta(itemStack.itemMeta?.also { it.displayName(if (title != null) ComponentUtility.empty().append(title) else null) })
        return this
    }

    override fun lore(): MutableList<Component> {
        return itemStack.itemMeta?.lore() ?: mutableListOf()
    }

    override fun lore(vararg components: Component?): ItemStackElement {
        itemStack.setItemMeta(itemStack.itemMeta?.also { it.lore(components.filterNotNull().map { c -> ComponentUtility.empty().append(c) }) })
        return this
    }

    override fun stack(): Int {
        return itemStack.amount
    }

    override fun stack(stack: Int): ItemStackElement {
        itemStack.amount = stack
        return this
    }

    override fun model(): Int {
        return itemStack.itemMeta?.customModelData ?: -1
    }

    override fun model(model: Int): ItemStackElement {
        itemStack.setItemMeta(itemStack.itemMeta?.also { it.setCustomModelData(model) })
        return this
    }

    override fun aura(): Boolean {
        return itemStack.itemMeta?.hasEnchants() ?: false
    }

    override fun aura(aura: Boolean): ItemStackElement {
        itemStack.setItemMeta(itemStack.itemMeta?.also { it.addEnchant(Enchantment.INFINITY, 1, false) })
        return this
    }

    override fun handler(): Handler? {
        return handler
    }

    override fun handler(handler: IVisualkitElement.Handler1): ItemStackElement {
        this.handler = handler
        return this
    }

    override fun handler(handler: IVisualkitElement.Handler2): ItemStackElement {
        this.handler = handler
        return this
    }

    override fun sound(): Sound? {
        return sound
    }

    override fun sound(sound: Sound): ItemStackElement {
        this.sound = sound
        return this
    }

    override fun sound(sound: Sound, volume: Float, pitch: Float): ItemStackElement {
        this.sound = sound
        this.volume = volume
        this.pitch = pitch
        return this
    }

    override fun volume(): Float {
        return volume
    }

    override fun pitch(): Float {
        return pitch
    }

    fun asItemStack(): ItemStack {
        return itemStack
    }

    override fun kill() {}
}