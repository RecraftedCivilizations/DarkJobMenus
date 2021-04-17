package com.github.recraftedcivilizations.darkmenus.commands

import com.github.recraftedcivilizations.darkmenus.BukkitWrapper
import com.github.recraftedcivilizations.darkmenus.menu.GUIMenu
import com.github.recraftedcivilizations.darkmenus.parser.ConfigParser
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class DarkMenusShow(private val configParser: ConfigParser) : CommandExecutor{

    /**
     * Show a given menu to the player
     * Executes the given command, returning its success.
     * <br></br>
     * If false is returned, then the "usage" plugin.yml entry for this command
     * (if defined) will be sent to the player.
     *
     * @param sender Source of the command
     * @param command Command which was executed
     * @param label Alias of the command which was used
     * @param args Passed command arguments
     * @return true if a valid command, otherwise false
     */
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player){ sender.sendMessage("Fuck off console man!!"); return false}

        val menuName = args.joinToString(" ")

        // Loop through all available menus
        for (menu in configParser.menus){
            // If the menu has the right name
            if (menu.name == menuName){
                // Check if the sender can execute the menu if not keep searching for the menu
                if (menu.specificTo.canExecute(sender, menu)){

                    // If the menu is a GUI menu display it
                    if (menu is GUIMenu){
                        menu.display(sender)
                        return true
                    }else{
                        throw NotImplementedError("Other menus then GUIMenus aren't implemented yet, this shouldn't even be possible")
                    }

                }
            }
        }

        sender.sendMessage("${ChatColor.RED}Could not find the menu $menuName!!")
        return false
    }
}