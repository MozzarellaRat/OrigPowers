package pink.rat.Powers;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import pink.rat.Orig.Application;
import pink.rat.Orig.Power;
import pink.rat.Orig.PowerTicker;

public class DummyPower extends Power implements PowerTicker {

	@Override
	public String getName() {
		return "dummy"; //The name should be simple, and easy to remember. 
	}

	@Override
	public String getFancyName() { 
		return ChatColor.GOLD+"Dummy"; //This is your "fancy name", it will go in chat messages.
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
	public void playerSwapHands(PlayerSwapHandItemsEvent e) { //Example Event
		Power power = Application.PowerManager.getPower(e.getPlayer());
		if (!(power.getClass().equals(this.getClass()))) return;

		if (getCooldown() <= 0) {
			e.setCancelled(true);
			e.getPlayer().sendMessage("Poof!");
			setCooldown(1);
		}
	}

	@Override
	public int tickDelay() {
		return 60; //Tells the PowerManager to fire you tick method every 60 ticks (3 seconds).
	}

	@Override
	public void tick(Player p) {
		p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("Hello, Dummy power "));
		
	}
}
