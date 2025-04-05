package pink.rat.Orig;

import org.bukkit.event.Listener;

public abstract class Power implements PowerInterface, Listener {
	
	private long cooldown = 0;
	
	public Power() {
	}
	
	public final long getCooldown() {
		return cooldown-System.currentTimeMillis();
	}
	
	public final void setCooldown(int cooldown) {
		this.cooldown = (cooldown*1000)+System.currentTimeMillis();
	}
	
}
