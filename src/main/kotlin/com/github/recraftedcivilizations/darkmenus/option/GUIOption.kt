package com.github.recraftedcivilizations.darkmenus.option

import com.github.darkvanityoflight.recraftedcore.gui.Clickable

/**
 * An option that can be represented in an Inventory GUI
 */
interface GUIOption : IOption {
    val displayItem: Clickable

}