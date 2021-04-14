package com.github.recraftedcivilizations.darkmenus.menu.menus

import com.github.recraftedcivilizations.darkcitizens.jobs.IJob
import com.github.recraftedcivilizations.darkmenus.BukkitWrapper
import com.github.recraftedcivilizations.darkmenus.menu.IMenu
import com.github.recraftedcivilizations.darkmenus.option.IOption

/**
 * @author DarkVanityOfLight
 */
/**
 * All things a menu can be specific to
 */
enum class SpecificTo{
    JOB,
    NOTHING,
}

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
     * @param specificTo Is the job specific to anything, like group or job
     * @param options All options this menu should contain
     * @param job Only has to be provided if you want a job specific menu
     */
    fun newMenu(name: String, isGui: Boolean, specificTo: SpecificTo, options: List<IOption>, job: IJob? = null): IMenu{

        if (isGui && specificTo == SpecificTo.JOB){
            return  JobGuiMenu(name, options, job!!)
        }else{
            bukkitWrapper.severe("There is no such menu as you defined! If you really need this create an issue at my Github!")
            throw NotImplementedError("There is no such menu as you defined")
        }
    }
}