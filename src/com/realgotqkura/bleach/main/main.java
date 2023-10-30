package com.realgotqkura.bleach.main;

import com.realgotqkura.bleach.characters.events.DefaultEvents;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin {

    @Override
    public void onEnable(){
        this.saveDefaultConfig();
        this.getCommand("bleachCharacter").setExecutor(new Commands(this));
        this.getServer().getPluginManager().registerEvents(new DefaultEvents(this), this);
    }

    @Override
    public void onDisable(){
        System.out.println("Disabling Bleach.");
    }

}
