package com.github.recraftedcivilizations.darkmenus.menu

import com.github.recraftedcivilizations.darkmenus.option.GUIOption
import org.bukkit.entity.Player

/**
 * @author DarkVanityOfLight
 */

/**
 * Represents an menu that has an graphical user interface
 */
interface GUIMenu : IJobMenu{

    /**
     * A list with all options of this menu
     */
    override val options: List<GUIOption>

    /**
     * Display the GUI to a sender
     */
    fun display(player: Player)
}