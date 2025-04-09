package pink.rat.Orig;

import org.bukkit.entity.Player;

/**
 * This will be used for "heartbeat" loops in powers without implementing a BukkitRunnable.
 */
public interface PowerTicker {
	/**
	 * Should return a value between 1-10.
	 * May support larger values in the future.
	 * 
	 * @return int
	 */
	public int tickDelay();
	
	/**
	 * This will get fired at the specified tick delay.
	 * 
	 * Ran synchronously with the main Bukkit thread.
	 * 
	 * @param p The given player using the power.
	 */
	
	public void tick(Player p);
}
