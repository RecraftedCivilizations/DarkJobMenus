package com.github.recraftedcivilizations.darkmenus.menu.menus

import com.github.recraftedcivilizations.darkcitizens.DarkCitizens
import com.github.recraftedcivilizations.darkcitizens.groups.Group
import com.github.recraftedcivilizations.darkcitizens.jobs.IJob
import com.github.recraftedcivilizations.darkmenus.BukkitWrapper
import com.github.recraftedcivilizations.darkmenus.menu.IGroupMenu
import com.github.recraftedcivilizations.darkmenus.menu.IJobMenu
import com.github.recraftedcivilizations.darkmenus.menu.IMenu
import com.github.recraftedcivilizations.darkmenus.option.IOption
import org.bukkit.entity.Player

/**
 * @author DarkVanityOfLight
 */
/**
 * All things a menu can be specific to
 */
enum class SpecificTo{
    JOB{
        override fun canExecute(player: Player, menu: IMenu): Boolean {
            val dPlayer = DarkCitizens.dPlayerManager.getDPlayer(player)!!

            if (menu !is IJobMenu) {
                throw IllegalArgumentException("The supplied menu isn't a job menu!!")
            }

            return menu.job.name == dPlayer.job
        }
       },
    GROUP{
        override fun canExecute(player: Player, menu: IMenu): Boolean {
            val dPlayer = DarkCitizens.dPlayerManager.getDPlayer(player)!!
            if (menu !is IGroupMenu){
                throw  IllegalArgumentException("The supplied menu isn't a group menu!!")
            }
            val group = DarkCitizens.jobManager.getJob(dPlayer.job)!!.group
            return menu.group.name == group
        }
    },

    NOTHING{
        override fun canExecute(player: Player, menu: IMenu): Boolean {
           return true
        }
    };

    abstract fun canExecute(player: Player, menu: IMenu): Boolean
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
    fun newMenu(name: String, isGui: Boolean, specificTo: SpecificTo, options: List<IOption>, job: IJob? = null, group: Group? = null): IMenu{

        if (isGui){
            return when (specificTo) {
                SpecificTo.JOB -> {
                    JobGuiMenu(name, options, job!!)
                }
                SpecificTo.GROUP -> {
                    GroupGuiMenu(name, options, group!!)
                }
                SpecificTo.NOTHING -> {
                    GuiMenu(name, options)
                }
                else -> {
                    bukkitWrapper.severe("There is no such menu as you defined! If you really need this create an issue at my Github!")
                    throw NotImplementedError("There is no such menu as you defined")
                }
            }
        }else{
            bukkitWrapper.severe("There is no such menu as you defined! If you really need this create an issue at my Github!")
            throw NotImplementedError("There is no such menu as you defined")
        }
    }
}