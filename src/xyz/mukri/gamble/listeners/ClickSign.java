package xyz.mukri.gamble.listeners;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import xyz.mukri.gamble.Core;
import xyz.mukri.gamble.timers.GameTimer.State;
import xyz.mukri.gamble.utils.Utils;

public class ClickSign implements Listener {

	public Core plugin;

	public ClickSign(Core plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onClickSign(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {

			if (e.getClickedBlock().getType() != null) {
				if (e.getClickedBlock().getType() == Material.OAK_SIGN
						|| e.getClickedBlock().getType() == Material.OAK_WALL_SIGN) {
					Sign sign = (Sign) e.getClickedBlock().getState();

					if (Utils.isPlayerInArena(p)) {
						if (sign.getLine(0).equals("[Judi]")) {
							int bet = Integer.parseInt(sign.getLine(1));
							String color = sign.getLine(3);
							
							if (Utils.isItDay()) {
								p.sendMessage("§cCasino only opens at night time!");
							} else {

								if (plugin.gameTimer.getState() == State.INGAME) {
									p.sendMessage("§7You miss the bet! Try again next round.");
								} else {
									Core.getInstance().blockLoc.getWorld().playSound(Core.getInstance().blockLoc,
											Sound.BLOCK_NOTE_BLOCK_PLING, 2.0f, 2.0f);

									plugin.gameTimer.changeSate(State.COUNTDOWN);
									Utils.addBet(p, bet, color);
								}
							}
						}

						if (sign.getLine(0).equals("Judi Stats")) {
							int winning = plugin.trackFile.getWinning();
							int losing = plugin.trackFile.getLosing();
							int red = plugin.trackFile.getColor("red");
							int black = plugin.trackFile.getColor("black");
							int green = plugin.trackFile.getColor("green");

							p.sendMessage(" ");
							p.sendMessage("§7Overall Win: §a" + winning);
							p.sendMessage("§7Overal Loss: §a" + losing);
							p.sendMessage(" ");
							p.sendMessage("§7Red Color Hit: §a" + red);
							p.sendMessage("§7Black Color Hit: §a" + black);
							p.sendMessage("§7Green Color Hit: §a" + green);
							p.sendMessage(" ");
						}

					}
				}
			}
		}
	}

}
