package com.github.recraftedcivilizations.darkmenus

import com.github.darkvanityoflight.recraftedcore.ARecraftedPlugin
import com.github.recraftedcivilizations.darkcitizens.DarkCitizens
import com.github.recraftedcivilizations.darkmenus.parser.ConfigParser
import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit
import org.bukkit.plugin.RegisteredServiceProvider

class Main : ARecraftedPlugin(){
    lateinit var configParser: ConfigParser
    lateinit var econ: Economy

    override fun onEnable() {
        val jobManager = DarkCitizens.jobManager


        if (Bukkit.getPluginManager().isPluginEnabled("Vault")){
            val rsp : RegisteredServiceProvider<Economy>? =  server.servicesManager.getRegistration(Economy::class.java)
            if (rsp != null) {
                econ = rsp.provider
            }
        }

        configParser = ConfigParser(config, econ, jobManager)
        configParser.read()


    }
}