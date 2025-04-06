package pink.rat.Orig;

import org.bukkit.event.Listener;
/**
 * The base power class that allows for easy implementation of Powers.
 */
public abstract class Power implements PowerInterface, Listener {
	/**
	 * The current players cooldown.
	 */
	private long cooldown = 0;
	
	public Power() {
	}
	
	public final long getCooldown() {
		return cooldown-System.currentTimeMillis();
	}
	
	public final void setCooldown(int cooldown) {
		this.cooldown = (cooldown*1000)+System.currentTimeMillis();
	}
	
	public final void setCooldown(double cooldown) {
		this.cooldown = (long) (cooldown*1000)+System.currentTimeMillis();
	}
	
}
