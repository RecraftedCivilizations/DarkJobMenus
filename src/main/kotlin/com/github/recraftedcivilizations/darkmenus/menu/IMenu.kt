package com.github.recraftedcivilizations.darkmenus.menu

import com.github.recraftedcivilizations.darkcitizens.dPlayer.DPlayer
import com.github.recraftedcivilizations.darkmenus.menu.menus.SpecificTo
import com.github.recraftedcivilizations.darkmenus.option.IOption
import org.bukkit.entity.Player

/**
 * @author DarkVanityOfLight
 */

/**
 * Represents an menu that contains actions/options that can be executed
 * @property options All options that can be executed from this menu
 * @property name The name of this menu
 */
interface IMenu {
    val options: List<IOption>
    val name: String
    val specificTo: SpecificTo

    /**
     * Execute an specific option
     * @param player The player that executed the option
     * @param position The position of the option
     */
    fun executeOption(player: Player, position: Int): Unit

    /**
     * Execute an specific option
     * @param player The player that executed the option
     * @param position The position of the option
     */
    fun executeOption(sender: DPlayer, position: Int): Unit
}