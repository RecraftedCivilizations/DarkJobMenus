package com.github.recraftedcivilizations.darkmenus.menu.menus

import com.github.recraftedcivilizations.darkcitizens.jobs.IJob
import com.github.recraftedcivilizations.darkmenus.BukkitWrapper
import com.github.recraftedcivilizations.darkmenus.menu.IMenu
import com.github.recraftedcivilizations.darkmenus.option.IOption

/**
 * @author DarkVanityOfLight
 */

/**
 * Create new menus here
 */
object MenuFactory {
    private var bukkitWrapper = BukkitWrapper()

    fun setBukkitWrapper(bukkitWrapper: BukkitWrapper){
        this.bukkitWrapper = BukkitWrapper()
    }

    /**
     * Create a new menu here
     * @param name The name of the menu
     * @param isGui Should the menu be a GUI
     * @param isJobSpecific Should the menu be specific to a job
     * @param options All options this menu should contain
     * @param job Only has to be provided if you want a job specific menu
     */
    fun newMenu(name: String, isGui: Boolean, isJobSpecific: Boolean, options: List<IOption>, job: IJob? = null): IMenu{

        if (isGui && isJobSpecific){
            return  JobGuiMenu(name, options, job!!)
        }else{
            bukkitWrapper.severe("There is no such menu as you defined! If you really need this create an issue at my Github!")
            throw NotImplementedError("There is no such menu as you defined")
        }
    }
}