package xyz.mukri.gamble.utils;

import java.util.Arrays;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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

	public static void sendMessageInArea(String msg) {
		Location loc1 = Core.getInstance().loc1;
		Location loc2 = Core.getInstance().loc2;

		for (Player p : Bukkit.getOnlinePlayers()) {
			if (isPlayerInArea(p, loc1, loc2)) {
				p.sendMessage(msg);
			}
		}
	}

	public static void addBet(Player p, int diamonds, String color) {
		if (!Core.getInstance().players.containsKey(p)) {
			if (p.getInventory().getItemInMainHand().getType() != Material.DIAMOND) {
				p.sendMessage("You don't have any diamonds in your hand! MISKIN, GO GET ONE!");
			} else {
				ItemStack item = p.getInventory().getItemInMainHand();

				if (item.getAmount() >= diamonds) {
					Core.getInstance().players.put(p, diamonds);
					Core.getInstance().playersColor.put(p, color);

					if (item.getAmount() == diamonds) {
						item.setType(Material.AIR);
					} else {
						item.setAmount(item.getAmount() - diamonds);
					}
				}
			}
		} else {
			p.sendMessage("Abiskita sudah menaruh bet biskita, tunggu next round baru dapat kita membet lagi.");
		}
	}

	public static void givePrizes(String color) {
		for (Player p : Core.getInstance().players.keySet()) {
			if (p != null) {
				if (color.equals("Red")) {
					if (Core.getInstance().playersColor.get(p).equalsIgnoreCase("RED")) {
						int amt = Core.getInstance().players.get(p);

						p.getInventory().addItem(new ItemStack(Material.DIAMOND, amt * 2));

						p.sendMessage("You've won " + (amt * 2) + " diamonds!");
					}
				}

				else if (color.equals("Black")) {
					if (Core.getInstance().playersColor.get(p).equalsIgnoreCase("BLACK")) {
						int amt = Core.getInstance().players.get(p);

						p.getInventory().addItem(new ItemStack(Material.DIAMOND, amt * 2));

						p.sendMessage("You've won " + (amt * 2) + " diamonds!");
					}
				}

				else if (color.equals("Green")) {
					if (Core.getInstance().playersColor.get(p).equalsIgnoreCase("GREEN")) {
						int amt = Core.getInstance().players.get(p);

						p.getInventory().addItem(new ItemStack(Material.DIAMOND, amt * 5));

						p.sendMessage("You've won " + (amt * 5) + " diamonds!");
					}
				} else {
					p.sendMessage("Kalah! Winner color is: " + color);
				}

			}
		}
	}

	public static String getRandomColor() {
		int r = new Random().nextInt(100 - 0 + 1) + 0;
				
		Bukkit.broadcastMessage(r + " number hashed");
		
		if (r < 50) {
			return "Red";
		} else if (r < 99) {
			return "Black";
		} else {
			return "Green";
		}
	}

	public static Material getRandomMaterial() {
		int foo = (int) (Math.random() * 100);

		if (foo < 50) {
			return Material.REDSTONE_BLOCK;
		} else if (foo < 99) {
			return Material.COAL_BLOCK;
		} else {
			return Material.EMERALD_BLOCK;
		}
	}

	public static Material getMaterial(String color) {
		if (color.equals("Red")) {
			return Material.REDSTONE_BLOCK;
		} else if (color.equals("Black")) {
			return Material.COAL_BLOCK;
		} else {
			return Material.EMERALD_BLOCK;
		}
	}
	
	public static boolean isItDay() {
		long time = Core.getInstance().blockLoc.getWorld().getTime();
		
		return time < 12300 || time > 23850;
	}

}
