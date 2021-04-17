package com.github.recraftedcivilizations.darkmenus.option

import com.github.recraftedcivilizations.darkcitizens.dPlayer.DPlayer
import org.bukkit.entity.Player

/**
 * @author DarkVanityOfLight
 */

/**
 * Represents an generic option that can be called from a menu
 */
interface IOption {
    val uniqueId: String
    val name: String

    /**
     * Defines what happens when the option gets executed
     * @param sender The Player that called the option
     */
    fun onCall(sender: Player)

    /**
     * Defines what happens when the option gets executed
     * @param sender The Player that called the option
     */
    fun onCall(sender: DPlayer)
}