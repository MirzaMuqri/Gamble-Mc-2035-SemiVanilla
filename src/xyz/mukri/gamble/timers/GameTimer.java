package xyz.mukri.gamble.timers;

import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

import xyz.mukri.gamble.Core;
import xyz.mukri.gamble.utils.Utils;

public class GameTimer extends BukkitRunnable {

	private int countdown;
	private int start;
	private State state;
	private String winning;
	
	public enum State {
		COUNTDOWN, INGAME, IDLE
	}
	
	public GameTimer() {
		countdown = 10;
		start = 10;
		state = State.IDLE;
	}
	
	public void start() {
		this.runTaskTimer(Core.getInstance(), 0, 20);
	}
	
	public void reset() {
		this.countdown = 10;
		this.start = 10;
		state = State.IDLE;
		
		Core.getInstance().players.clear();
		Core.getInstance().playersColor.clear();
	}
	
	public void changeSate(State state) {
		this.state = state;
	}
	
	public State getState() {
		return state;
	}
	
	@Override
	public void run() {
		if (state == State.COUNTDOWN) {
			countdown--;
			
			if (countdown  >= 1) {
				Utils.sendMessageInArea("Bet will start in " + countdown);
			}
			
			if (countdown == 0) {
				this.state = State.INGAME;
			}
		}
		
		if (state == State.INGAME) {
			start--;
			
			if (start > 1) {
				Core.getInstance().blockLoc.getWorld().playSound(Core.getInstance().blockLoc, Sound.BLOCK_NOTE_BLOCK_PLING, 2.0f, 2.0f);
				Core.getInstance().blockLoc.getBlock().setType(Utils.getRandomMaterial());	
			}
			
			if (start == 1) {
				winning = Utils.getRandomColor();
				Core.getInstance().blockLoc.getBlock().setType(Utils.getMaterial(winning));
				
				Utils.sendMessageInArea("Winning color: " + winning );
				
				Utils.givePrizes(winning);
			}
			
			if (start == 0) {
				reset();
			}
		}
	}
	
}
