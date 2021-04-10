package com.github.recraftedcivilizations.darkmenus

import org.bukkit.Bukkit
import org.bukkit.command.ConsoleCommandSender

/**
 * @author DarkVanityOfLight
 */

open class BukkitWrapper: com.github.darkvanityoflight.recraftedcore.api.BukkitWrapper() {

    fun executeCommand(command: String){
        val console: ConsoleCommandSender = Bukkit.getServer().consoleSender;
        Bukkit.dispatchCommand(console, command)
    }
}