package pink.rat.Powers;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import pink.rat.Orig.Application;
import pink.rat.Orig.Power;

public class LactosePower extends Power {

	@Override
	public void powerActivate(Player p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void powerDeactivate(Player p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		return "LACTOSE";
	}

	@Override
	public String getFancyName() {
		return ChatColor.translateAlternateColorCodes('&',"C12H22O11");
	}

	@Override
	public List<String> getDescriptionName() {
		return Arrays.asList("You can no longer drink milk, but you can use this to your advantage.");
	}
	
	@EventHandler
	public void drinkMilk(PlayerItemConsumeEvent e) {
		
		Power power = Application.PowerManager.getPower(e.getPlayer());
		if (!(power.getClass().equals(this.getClass()))) return;
		
		if (e.getItem().getType().equals(Material.MILK_BUCKET)) {
			e.getPlayer().sendMessage("Congrats you shit yourself");
		}
	}

}
