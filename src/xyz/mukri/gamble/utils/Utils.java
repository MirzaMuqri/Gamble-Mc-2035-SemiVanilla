package xyz.mukri.gamble.utils;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import xyz.mukri.gamble.Core;

public class Utils {

	public static boolean isPlayerInArea(Player p, Location loc1, Location loc2) {
		double[] dim = new double[2];

		dim[0] = loc1.getX();
		dim[1] = loc2.getX();
		Arrays.sort(dim);
		if (p.getLocation().getX() > dim[1] || p.getLocation().getX() < dim[0])
			return false;

		dim[0] = loc1.getZ();
		dim[1] = loc2.getZ();
		Arrays.sort(dim);
		if (p.getLocation().getZ() > dim[1] || p.getLocation().getZ() < dim[0])
			return false;

		dim[0] = loc1.getY();
		dim[1] = loc2.getY();
		Arrays.sort(dim);
		if (p.getLocation().getY() > dim[1] || p.getLocation().getY() < dim[0])
			return false;

		return true;
	}

	public static void sendMessageInArea(Location loc1, Location loc2, String msg) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (isPlayerInArea(p, loc1, loc2)) {
				p.sendMessage(msg);
			}
		}
	}

	public static void addBet(Player p, int diamonds) {
		if (p.getInventory().getItemInMainHand().getType() != Material.DIAMOND) {
			p.sendMessage("You don't have any diamonds in your hand! MISKIN, GO GET ONE!");
		} else {
			ItemStack item = p.getInventory().getItemInMainHand();

			if (item.getAmount() >= diamonds) {
				Core.getInstance().players.put(p, diamonds);

				if (item.getAmount() == diamonds) {
					item.setType(Material.AIR);
				} else {
					item.setAmount(item.getAmount() - diamonds);
				}
			}
		}
	}

	public static void startRound() {
		Core.instance.counting = true;
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
			}
		};
	}

}
