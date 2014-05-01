package com.mydeblob.main;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	public void onEnable(){
		File config = new File(this.getDataFolder(), "config.yml");
		if(!config.exists()){
			this.saveDefaultConfig();
			getLogger().info("---------------------------------------");
			getLogger().info("<Plugin name here>");
			getLogger().info("No config.yml found! Creating a new one!");
			getLogger().info("---------------------------------------");
			getCommand("basecommand").setExecutor(new Commands());
		}
	}
	public void onDisable(){
		
	}
}
