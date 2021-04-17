package com.github.recraftedcivilizations.darkmenus.option.options

import com.github.recraftedcivilizations.darkcitizens.dPlayer.DPlayer
import com.github.recraftedcivilizations.darkmenus.BukkitWrapper
import com.github.recraftedcivilizations.darkmenus.option.IOption
import net.milkbowl.vault.economy.Economy
import org.bukkit.Material
import org.bukkit.entity.Player

/**
 * @author DarkVanityOfLight
 */

/**
 * Create new options here
 */
object OptionFactory {
    private var bukkitWrapper = BukkitWrapper()

    /**
     * Set the bukkit wrapper debugg purposes only
     * @param bukkitWrapper The new bukkit wrapper
     */
    fun setBukkitWrapper(bukkitWrapper: BukkitWrapper){
        this.bukkitWrapper = bukkitWrapper
    }

    /**
     * Create a new option
     * @param isGuiOption Determine if the option should be representable in a Inventory GUI
     * @param isPricedOption Determine if the option should have a price to activate
     * @param isCommandOption Determine if the option should execute a command as action
     * @param economy The economy to draw money from, only required if [isPricedOption] is set
     * @param command The command to execute, only required if [isCommandOption] is set
     * @param price The price this option should cost, only required if [isPricedOption] is set
     * @param icon The icon to display, only required if [isGuiOption] is set
     * @throws IllegalArgumentException If the arguments required for the type of option requested do not match
     * @throws NotImplementedError If the requested option is not available yet
     * @return The requested option
     */
    fun newOption(uniqueId: String, name: String, isGuiOption: Boolean, isPricedOption: Boolean, isCommandOption: Boolean, economy: Economy? = null, command: String? = null, price: Int?, icon: Material? = null): IOption {

        // Check if we should create a GUI option
        if (isGuiOption){
            // Check that the icon is given
            if (icon == null) {
                bukkitWrapper.severe("You need to supply an icon to create an GUI option")
                throw IllegalArgumentException("You need to supply an icon to create an GUI option")
            }

            // Check if we have an command option
            if (isCommandOption){

                //Check that we have a command supplied
                if (command == null) {
                    bukkitWrapper.severe("You need to supply a command to create an command option")
                    throw IllegalArgumentException("You need to supply a command to create a command option")
                }

                // Check if we have a priced option
                if (isPricedOption){
                    // Check that we have a price given
                    if (price == null) {
                        bukkitWrapper.severe("You need to supply a price to create a priced option")
                        throw IllegalArgumentException("You need to supply an price to create an priced option")
                    }

                    // If we want a GUI option that executes a command and has price to execute
                    return PricedCommandGUIOption(uniqueId, name, icon, command, price, economy!!)
                }else{
                    // If we want a GUI option that executes a command without a price
                    return CommandGUIOption(uniqueId, name, icon, command)
                }
            }else{

                bukkitWrapper.severe("There is no implementation for the type of option you requested")
                throw NotImplementedError("There is no implementation for the type of option you requested")
            }
        }else{
            bukkitWrapper.severe("There is no implementation for the type of option you requested")
            throw NotImplementedError("There is no implementation for the type of option you requested")
        }


    }
}