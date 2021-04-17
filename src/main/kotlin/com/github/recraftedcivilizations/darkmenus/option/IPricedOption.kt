package com.github.recraftedcivilizations.darkmenus.option

/**
 * Represents an action that costs a certain amount of money
 */
interface IPricedOption : IOption{
    val price: Int

}