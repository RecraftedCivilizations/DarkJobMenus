package com.github.recraftedcivilizations.darkmenus.option.options

import com.github.darkvanityoflight.recraftedcore.gui.Clickable
import com.github.recraftedcivilizations.darkcitizens.dPlayer.DPlayer
import com.github.recraftedcivilizations.darkmenus.BukkitWrapper
import com.github.recraftedcivilizations.darkmenus.gui.OptionItem
import com.github.recraftedcivilizations.darkmenus.option.GUIOption
import com.github.recraftedcivilizations.darkmenus.option.ICommandOption
import me.clip.placeholderapi.PlaceholderAPI
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * @author DarkVanityOfLight
 */

/**
 * An option that can be represented in a GUI and executes a command
 * @param icon The icon this should be displayed as
 * @param command The command to execute
 * @param bukkitWrapper The bukkit wrapper, debugging purposes only
 * @property displayItem The Clickable item this will be shown as
 */
class CommandGUIOption(private val icon: Material, override val command: String, private val bukkitWrapper: BukkitWrapper) : GUIOption, ICommandOption{
    override val displayItem: Clickable = OptionItem(ItemStack(icon), this)

    /**
     * Defines what happens when the option gets executed
     * @param sender The Player that called the option
     */
    override fun onCall(sender: Player) {
        bukkitWrapper.executeCommand(PlaceholderAPI.setPlaceholders(sender, command))
    }

    /**
     * Defines what happens when the option gets executed
     * @param sender The Player that called the option
     */
    override fun onCall(sender: DPlayer) {
        val sender = bukkitWrapper.getPlayer(sender.uuid)!!
        onCall(sender)
    }
}