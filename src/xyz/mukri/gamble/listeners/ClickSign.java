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
					int bet = Integer.parseInt(sign.getLine(1));
					String color = sign.getLine(3);

					if (sign.getLine(0).equals("[Judi]")) {

						if (Utils.isItDay()) {
							p.sendMessage("Casino only opens at night time!");
						} else {

							if (plugin.gameTimer.getState() == State.INGAME) {
								p.sendMessage("You miss the bet! Try again next round.");
							} else {
								p.sendMessage("You bet " + bet + " on color " + color);
								Core.getInstance().blockLoc.getWorld().playSound(Core.getInstance().blockLoc,
										Sound.BLOCK_NOTE_BLOCK_PLING, 2.0f, 2.0f);

								plugin.gameTimer.changeSate(State.COUNTDOWN);
								Utils.addBet(p, bet, color);
							}
						}
					}
				}
			}
		}
	}

}
