package com.github.recraftedcivilizations.darkmenus.menu

import com.github.recraftedcivilizations.darkcitizens.groups.Group

/**
 * Represents a menu that is specific to a Group
 * @see IMenu
 * @property group The group this menu belongs too
 */
interface IGroupMenu : IMenu{
    val group: Group
}