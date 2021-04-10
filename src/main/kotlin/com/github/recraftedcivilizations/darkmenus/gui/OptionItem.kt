package com.github.recraftedcivilizations.darkmenus.gui

import com.github.darkvanityoflight.recraftedcore.gui.Clickable
import com.github.darkvanityoflight.recraftedcore.gui.DisplayItem
import com.github.recraftedcivilizations.darkmenus.option.IOption
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * @author DarkVanityOfLigt
 */

/**
 * Represents an option in a GUI
 * @param itemStack The item to be shown as
 * @param option The option to represent
 */
class OptionItem(itemStack: ItemStack, private val option: IOption) : Clickable(itemStack) {

    override fun clone(): DisplayItem {
        return OptionItem(itemStack, option)
    }

    override fun onClick(player: Player) {
        option.onCall(player)
    }
}