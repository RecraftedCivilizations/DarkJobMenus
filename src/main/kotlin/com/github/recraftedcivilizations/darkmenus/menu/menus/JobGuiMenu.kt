package com.github.recraftedcivilizations.darkmenus.menu.menus

import com.github.darkvanityoflight.recraftedcore.gui.InventoryGUI
import com.github.darkvanityoflight.recraftedcore.gui.elements.CloseButtonFactory
import com.github.recraftedcivilizations.darkcitizens.dPlayer.DPlayer
import com.github.recraftedcivilizations.darkcitizens.jobs.IJob
import com.github.recraftedcivilizations.darkmenus.BukkitWrapper
import com.github.recraftedcivilizations.darkmenus.menu.GUIMenu
import com.github.recraftedcivilizations.darkmenus.menu.IJobMenu
import com.github.recraftedcivilizations.darkmenus.option.GUIOption

import org.bukkit.entity.Player

/**
 * @author DarkVanityOfLight
 */

/**
 * Represents a GUI menu specific for a job
 * @param name The name of the GUI
 * @param options All options that can be executed with this menu
 * @param job The job this GUI is for
 * @param bukkitWrapper The bukkit wrapper, debugging purposes only
 */
class JobGuiMenu(override val name: String, override val options: List<GUIOption>, override val job: IJob, private val bukkitWrapper: BukkitWrapper = BukkitWrapper()) : IJobMenu, GUIMenu {

    private val gui: InventoryGUI

    init {

        // Make the number of options dividable by 9
        // +1 Because of the Close button
        var invSize = options.size + 1
        if(invSize % 9 != 0){
            invSize += (9 - (invSize % 9))
        }
        gui = InventoryGUI(invSize, name)

        for (item in options){
            gui.addItem(item.displayItem)
        }

        gui.setSlot(CloseButtonFactory.getCloseButton(), invSize-1)
    }

    /**
     * Display the GUI to a specific player
     * @param player The player to show the gui too
     */
    override fun display(player: Player) {
        gui.show(player)
    }

    /**
     * DO NOT USE THIS, THE CLICKED ITEM WILL EXECUTE ITS ACTION BY ITSELF,
     */
    @Deprecated("Do not use this, the GUI item will execute the action by itself if it gets clicked",
        ReplaceWith("OptionItem.onClick(sender)"))
    override fun executeOption(player: Player, position: Int) {
        options[position].onCall(player)
    }

    /**
     * DO NOT USE THIS, THE CLICKED ITEM WILL EXECUTE ITS ACTION BY ITSELF
     */
    @Deprecated("Do not use this, the GUI item will execute the action by itself if it gets clicked",
        ReplaceWith("OptionItem.onClick(sender)"))
    override fun executeOption(sender: DPlayer, position: Int) {
        executeOption(bukkitWrapper.getPlayer(sender.uuid)!!, position)
    }
}