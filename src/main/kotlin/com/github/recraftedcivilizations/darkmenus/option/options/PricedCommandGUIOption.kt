package com.github.recraftedcivilizations.darkmenus.option.options


import com.github.recraftedcivilizations.darkmenus.BukkitWrapper
import com.github.recraftedcivilizations.darkcitizens.dPlayer.DPlayer
import com.github.recraftedcivilizations.darkmenus.gui.OptionItem
import com.github.recraftedcivilizations.darkmenus.option.GUIOption
import com.github.recraftedcivilizations.darkmenus.option.ICommandOption
import com.github.recraftedcivilizations.darkmenus.option.IPricedOption
import me.clip.placeholderapi.PlaceholderAPI
import net.milkbowl.vault.economy.Economy
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * @author DarkVanityOfLight
 */

/**
 * Represents an action that costs money, can be represented in an GUI and executes and command when it gets called
 * @see GUIOption
 * @see ICommandOption
 * @see IPricedOption
 * @param icon The icon to be represented as
 * @param economy The economy to withdraw from
 * @param bukkitWrapper The bukkit wrapper, for debugging purposes only
 */
class PricedCommandGUIOption(
    private val icon: Material,
    override val command: String,
    override val price: Int,
    private val economy: Economy,
    private val bukkitWrapper: BukkitWrapper
) : GUIOption, ICommandOption, IPricedOption{
    override val displayItem = OptionItem(ItemStack(icon), this)

    override fun onCall(sender: Player) {
        // Check that the player has enough money
        if (economy.has(sender, price.toDouble())){
            // Withdraw the price from the player
            val transaction = economy.withdrawPlayer(sender, price.toDouble())
            // Check that the transaction succeeded
            if (transaction.transactionSuccess()){
                // Replace eventual placeholders in the command and execute it
                bukkitWrapper.executeCommand(PlaceholderAPI.setPlaceholders(sender, command))
            }else{
                sender.sendMessage("${ChatColor.RED}Something went wrong please try again")
            }
        }else{
            sender.sendMessage("You don't have enough money")
        }
    }

    override fun onCall(sender: DPlayer) {
        val sender = bukkitWrapper.getPlayer(sender.uuid)!!
        onCall(sender)
    }
}