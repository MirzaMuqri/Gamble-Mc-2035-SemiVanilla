package xyz.mukri.gamble;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import xyz.mukri.gamble.listeners.ClickSign;

public class Core extends JavaPlugin {
	
	public Location loc1;
	public Location loc2;
	
	public HashMap<Player, Integer> players = new HashMap<Player, Integer>();
	
	public Boolean start = false;
	public Boolean counting = false;
	
	public static Core instance;
	
	public void onEnable() {
		instance = this;
		
		loc1 = new Location(Bukkit.getWorld("2035_smp"), -146, 73, 146);
		loc2 = new Location(Bukkit.getWorld("2035_smp"), -185, 61, 107);
		
		registerListeners();
		
		getLogger().info("Gamble is up and running");
	}
	
	public void onDisable() {
		getLogger().info("Gamble has been unloaded");
	}
	
	public void registerListeners() {
		getServer().getPluginManager().registerEvents(new ClickSign(this), this);
	}
	
	public static Core getInstance() {
		return instance;
	}

}
