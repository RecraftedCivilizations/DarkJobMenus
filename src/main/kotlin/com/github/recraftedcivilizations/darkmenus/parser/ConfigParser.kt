package com.github.recraftedcivilizations.darkmenus.parser

import com.github.darkvanityoflight.recraftedcore.configparser.ARecraftedConfigParser
import com.github.recraftedcivilizations.darkcitizens.jobs.IJob
import com.github.recraftedcivilizations.darkcitizens.jobs.JobManager
import com.github.recraftedcivilizations.darkmenus.BukkitWrapper
import com.github.recraftedcivilizations.darkmenus.menu.IMenu
import com.github.recraftedcivilizations.darkmenus.menu.menus.MenuFactory
import com.github.recraftedcivilizations.darkmenus.menu.menus.SpecificTo
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
class ConfigParser(config: FileConfiguration, private val economy: Economy, private val jobManager: JobManager, private val bukkitWrapper: BukkitWrapper = BukkitWrapper()) : ARecraftedConfigParser(config) {
    val options = emptySet<IOption>().toMutableSet()
    val menus = emptySet<IMenu>().toMutableSet()

    /**
     * Read the config file
     */
    override fun read() {

        // Get the different sections
        var optionsSection = config.getConfigurationSection(optionsName)
        var menusSection = config.getConfigurationSection(menusName)

        if (optionsSection == null) {
            bukkitWrapper.severe("Could not find the options section, I'll define it for you, but you should add it using th $optionsName tag!!")
            optionsSection = config.createSection("options")
        }

        parseOptions(optionsSection)

        if (menusSection == null){
            bukkitWrapper.severe("Could not find the menus section, I'll define it for you, but you should add it using the $menusSection tag!!")
            menusSection = config.createSection(menusName)
        }
    }

    /**
     * Parse an config section containing options
     */
    private fun parseOptions(optionsSection: ConfigurationSection){

        for (configurationSection in optionsSection.getKeys(false)){
            val section = optionsSection.getConfigurationSection(configurationSection)
            configSectionToOption(section!!)
        }

    }

    /**
     * Parse an config section to an option and register it at [options]
     */
    private fun configSectionToOption(configurationSection: ConfigurationSection) {

        val name = configurationSection.getString(optionNameName, null)

        if (name == null){
            bukkitWrapper.warning("The property $optionNameName is not defined for the option ${configurationSection.name}, define it using the $optionNameName tag!!")
            return
        }

        if (!configurationSection.isSet(isGuiOptionName)){
            bukkitWrapper.warning("The property $isGuiOptionName is not set for the option ${configurationSection.name}, I'll default it to true but you should set it yourself!!")
        }

        val isGuiOption = configurationSection.getBoolean(isGuiOptionName, true)

        if (!configurationSection.isSet(isPricedOptionName)){
            bukkitWrapper.warning("The property $isPricedOptionName is not set for the option ${configurationSection.name}, I'll default it to false but you should set it yourself!!")
        }

        val isPricedOption = configurationSection.getBoolean(isPricedOptionName, true)

        if (!configurationSection.isSet(isCommandOptionName)){
            bukkitWrapper.warning("The property $isCommandOptionName is not set for the option ${configurationSection.name}, I'll default it to false but you should set it yourself!!")
        }

        val isCommandOption = configurationSection.getBoolean(isCommandOptionName, false)


        val iconAsString = configurationSection.getString(iconName)
        var icon: Material? = null
        if (isGuiOption) {
            if (iconAsString == null) {
                bukkitWrapper.warning("The option ${configurationSection.name} has no icon defined, I'll default it to something for you but you should define it yourself using the $iconAsString tag!!")
            } else {
                if (Material.getMaterial(iconAsString) == null) {
                    bukkitWrapper.warning("The icon for the option ${configurationSection.name} does not exist!")
                }
            }
            icon = iconAsString?.let { Material.getMaterial(it) } ?: Material.PLAYER_HEAD
        }

        var price: Int = configurationSection.getInt(priceName, -1)
        if (isPricedOption && price == -1){
            bukkitWrapper.warning("You did not define a price for the priced option ${configurationSection.name}, I'll default it to 100, but you should define it using the $priceName tag")
            price = 100
        }

        val command = configurationSection.getString(commandName, null)
        if (command == null && isCommandOption){
            bukkitWrapper.warning("You did not define a command for your command option, the option ${configurationSection.name} will not be available")
            return
        }

        val option = OptionFactory.newOption(configurationSection.name, name, isGuiOption, isPricedOption, isCommandOption, economy, command, price, icon)
        options.add(option)
    }

    /**
     * Parse all menus found in the menu section
     */
    private fun parseMenus(configurationSection: ConfigurationSection){
        for (menuId in configurationSection.getKeys(false)){
            configSectionToMenu(configurationSection.getConfigurationSection(menuId)!!)
        }
    }

    /**
     * Parse a menu section to a menu
     */
    private fun configSectionToMenu(configurationSection: ConfigurationSection){


        val name = configurationSection.getString(menuNameName, null)

        if (name == null){
            bukkitWrapper.warning("The property $menuNameName is not defined for the menu ${configurationSection.name}, define it using the $menuNameName tag!!")
            return
        }

        if (!configurationSection.isSet(isGuiMenuName)){
            bukkitWrapper.warning("The property $isGuiMenuName is not defined for the menu ${configurationSection.name}, I'll default it to true but you should define it yourself using the $isGuiMenuName tag!!")
        }

        val isGuiMenu = configurationSection.getBoolean(isGuiMenuName, false)

        val specificTo: SpecificTo
        if (!configurationSection.isSet(menuSpecificToName)){
            bukkitWrapper.warning("The property $menuSpecificToName is not defined for the menu ${configurationSection.name}, I'll default it to NOTHING. but you should define it using the $menuSpecificToName tag!!")
            specificTo = SpecificTo.NOTHING
        }else{
            val specificToId = configurationSection.getString(menuSpecificToName)!!

            specificTo = try {
                SpecificTo.valueOf(specificToId)
            }catch (e: IllegalArgumentException){
                bukkitWrapper.warning("Could not find the specificTo type for your menu ${configurationSection.name}, I'll default it to NOTHING, but you should fix this")
                SpecificTo.NOTHING
            }
        }

        if (!configurationSection.isSet(menuOptionsName)){
            bukkitWrapper.warning("You didn't define any options for the menu ${configurationSection.name}!!")
            return
        }

        val optionsNames = configurationSection.getStringList(menuOptionsName)
        val parsedOptions = mutableListOf<IOption>()

        // Search for the defined action and add it to the action set
        for(optionName in optionsNames){

            // Parse the string to the actual option
            var parsedOption: IOption? = null
            for (option in options){
                if (option.uniqueId == optionName){
                    parsedOption = option
                    break
                }
            }

            // Warn if no option was found else add it to the parsed options
            if (parsedOption == null){
                bukkitWrapper.warning("Could not find the option $optionName, did you define it in the $optionsName section?")
            }else{
                parsedOptions.add(parsedOption)
            }

        }

        var job: IJob? = null
        if (specificTo == SpecificTo.JOB){
            if (!configurationSection.isSet(menuJobName)){
                bukkitWrapper.warning("You did not define a job for the the job menu ${configurationSection.name}!!")
                return
            }else{
                job = jobManager.getJob(configurationSection.getString(menuJobName))

                if (job == null){
                    bukkitWrapper.warning("Could not find the job defined for the job menu ${configurationSection.name}")
                    return
                }
            }
        }


        val menu = MenuFactory.newMenu(name, isGuiMenu, specificTo, parsedOptions, job)
        menus.add(menu)

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
        const val optionNameName = "name"
        const val menusName = "menus"
        const val menuNameName = "name"
        const val menuJobName = "job"
        const val menuOptionsName = "options"
        const val isGuiMenuName = "isGuiMenu"
        const val menuSpecificToName = "specificTo"
    }
}