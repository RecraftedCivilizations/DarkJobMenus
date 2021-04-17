package com.github.recraftedcivilizations.darkmenus.menu

import com.github.recraftedcivilizations.darkcitizens.jobs.IJob

/**
 * @author DarkVanityOfLight
 */

/**
 * Represents a menu that is specific for a job
 * @see IMenu
 * @property job The job this Menu is for
 */
interface IJobMenu : IMenu {
    val job: IJob

}