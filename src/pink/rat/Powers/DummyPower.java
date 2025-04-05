package pink.rat.Powers;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class DummyPower extends Power {

	public DummyPower() {
		
	}

	@Override
	public String getName() {
		return "dummy";
	}

	@Override
	public String getFancyName() {
		return ChatColor.GOLD+"Dummy";
	}
	
	@Override
	public List<String> getDescriptionName() {
		return Arrays.asList("Your basic testing power.", "If you have this you may have spelled the name of one of your power wrong, you may have also forgotten to register your power.");
	}

	@Override
	public void powerDeactivate(Player p) {
		
	}

	@Override
	public void powerActivate(Player p) {

	}
	
	@EventHandler
	public void playerSwapHands(PlayerSwapHandItemsEvent e) {
		if (getCooldown() <= 0) {
			e.setCancelled(true);
			e.getPlayer().sendMessage("Poof!");
			setCooldown(1);
		}
	}
}
