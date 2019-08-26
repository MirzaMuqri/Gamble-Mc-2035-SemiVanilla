package xyz.mukri.gamble.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import xyz.mukri.gamble.Core;

public class OnQuitEvent implements Listener {

	public Core plugin;

	public OnQuitEvent(Core plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		
		if (plugin.players.containsKey(p)) {
			int diamonds = plugin.players.get(p);
			
			p.getInventory().addItem(new ItemStack(Material.DIAMOND, diamonds));
			
			plugin.players.remove(p);
			plugin.playersColor.remove(p);
		}
	}
}
