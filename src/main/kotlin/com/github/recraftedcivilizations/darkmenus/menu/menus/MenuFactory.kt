package com.github.recraftedcivilizations.darkmenus.menu.menus

import com.github.recraftedcivilizations.darkcitizens.jobs.IJob
import com.github.recraftedcivilizations.darkmenus.BukkitWrapper
import com.github.recraftedcivilizations.darkmenus.menu.IMenu
import com.github.recraftedcivilizations.darkmenus.option.IOption

object MenuFactory {
    private var bukkitWrapper = BukkitWrapper()

    fun setBukkitWrapper(bukkitWrapper: BukkitWrapper){
        this.bukkitWrapper = BukkitWrapper()
    }

    fun newMenu(name: String, isGui: Boolean, isJobSpecific: Boolean, options: List<IOption>, job: IJob? = null): IMenu{

        if (isGui && isJobSpecific){
            return  JobGuiMenu(name, options, job!!)
        }else{
            bukkitWrapper.severe("There is no such menu as you defined! If you really need this create an issue at my Github!")
            throw NotImplementedError("There is no such menu as you defined")
        }
    }
}