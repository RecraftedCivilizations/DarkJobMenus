package com.github.recraftedcivilizations.darkmenus.parser

import com.github.darkvanityoflight.recraftedcore.configparser.ARecraftedConfigParser
import com.github.recraftedcivilizations.darkmenus.BukkitWrapper
import com.github.recraftedcivilizations.darkmenus.menu.IMenu
import com.github.recraftedcivilizations.darkmenus.option.IOption
import com.github.recraftedcivilizations.darkmenus.option.options.OptionFactory
import net.milkbowl.vault.economy.Economy
import org.bukkit.Material
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.file.FileConfiguration

/**
 * Use this to get information from the config file,
 * everything needed will be stored here
 * @param config The config file to parse
 * @param economy The server econ
 * @param bukkitWrapper For debugging purposes only
 * @property options All parsed options will be available here
 * @property menus All parsed menus will be available here
 */
class ConfigParser(config: FileConfiguration, val economy: Economy, private val bukkitWrapper: BukkitWrapper = BukkitWrapper()) : ARecraftedConfigParser(config) {
    val options = emptySet<IOption>().toMutableSet()
    val menus = emptySet<IMenu>().toMutableSet()

    /**
     * Read the config file
     */
    override fun read() {

        // Get the different sections
        var optionsSection = config.getConfigurationSection(optionsName)
        val menusSection = config.getConfigurationSection(menusName)

        if (optionsSection == null) {
            bukkitWrapper.severe("Could not find the options section, I'll define it for you, but you should add it using th $optionsName tag!!")
            optionsSection = config.createSection("options")
        }

        parseOptions(optionsSection)

    }

    /**
     * Parse an config section containing options
     */
    private fun parseOptions(optionsSection: ConfigurationSection){

        for (optionSection in optionsSection.getKeys(false)){
            val section = optionsSection.getConfigurationSection(optionSection)
            configSectionToOption(section!!)
        }

    }

    /**
     * Parse an config section to an option and register it at [options]
     */
    private fun configSectionToOption(optionSection: ConfigurationSection) {

        val name = optionSection.getString(nameName, null)

        if (name == null){
            bukkitWrapper.warning("The property $nameName is not defined for the option ${optionSection.name}, define it using the $nameName tag!!")
            return
        }

        if (!optionSection.isSet(isGuiOptionName)){
            bukkitWrapper.warning("The property $isGuiOptionName is not set for the option ${optionSection.name}, I'll default it to true but you should set it yourself!!")
        }

        val isGuiOption = optionSection.getBoolean(isGuiOptionName, true)

        if (!optionSection.isSet(isPricedOptionName)){
            bukkitWrapper.warning("The property $isPricedOptionName is not set for the option ${optionSection.name}, I'll default it to false but you should set it yourself!!")
        }

        val isPricedOption = optionSection.getBoolean(isPricedOptionName, true)

        if (!optionSection.isSet(isCommandOptionName)){
            bukkitWrapper.warning("The property $isCommandOptionName is not set for the option ${optionSection.name}, I'll default it to false but you should set it yourself!!")
        }

        val isCommandOption = optionSection.getBoolean(isCommandOptionName, false)


        val iconAsString = optionSection.getString(iconName)
        var icon: Material? = null
        if (isGuiOption) {
            if (iconAsString == null) {
                bukkitWrapper.warning("The option ${optionSection.name} has no icon defined, I'll default it to something for you but you should define it yourself using the $iconAsString tag!!")
            } else {
                if (Material.getMaterial(iconAsString) == null) {
                    bukkitWrapper.warning("The icon for the option ${optionSection.name} does not exist!")
                }
            }
            icon = iconAsString?.let { Material.getMaterial(it) } ?: Material.PLAYER_HEAD
        }

        var price: Int = optionSection.getInt(priceName, -1)
        if (isPricedOption && price == -1){
            bukkitWrapper.warning("You did not define a price for the priced option ${optionSection.name}, I'll default it to 100, but you should define it using the $priceName tag")
            price = 100
        }

        val command = optionSection.getString(commandName, null)
        if (command == null && isCommandOption){
            bukkitWrapper.warning("You did not define a command for your command option, the option ${optionSection.name} will not be available")
            return
        }

        val option = OptionFactory.newOption(optionSection.name, name, isGuiOption, isPricedOption, isCommandOption, economy, command, price, icon)
        options.add(option)
    }


    /**
     * Contains all static strings to identify the paths
     */
    companion object{
        const val optionsName = "options"
        const val isGuiOptionName = "isGuiOption"
        const val isPricedOptionName = "isPricedOption"
        const val isCommandOptionName = "isCommandOption"
        const val iconName = "icon"
        const val priceName = "price"
        const val commandName = "command"
        const val nameName = "name"
        const val menusName = "menus"
    }
}