package com.github.recraftedcivilizations.darkmenus.option

/**
 * @author DarkVanityOfLight
 */

/**
 * Represents an option that executes an command on call
 * @property command The command to execute when called
 */
interface ICommandOption: IOption {
    val command: String
}