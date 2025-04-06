package pink.rat.Powers;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import pink.rat.Orig.Application;
import pink.rat.Orig.Power;

public class AustraliaPower extends Power {

	@Override
	public void powerActivate(Player p) {
		// TODO Auto-generated method stub
		p.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, Integer.MAX_VALUE, 5, false, false));
		p.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, Integer.MAX_VALUE, 2, false, false));
	}

	@Override
	public void powerDeactivate(Player p) {
		// TODO Auto-generated method stub
		p.removePotionEffect(PotionEffectType.LEVITATION);
		p.removePotionEffect(PotionEffectType.HASTE);
		p.removePotionEffect(PotionEffectType.SLOW_FALLING);
	}

	@Override
	public String getName() {
		return "aussie";
	}

	@Override
	public String getFancyName() {
		return ChatColor.LIGHT_PURPLE+"Ɐnsʇɹɐꞁᴉɐ";
	}

	@Override
	public List<String> getDescriptionName() {
		return Arrays.asList("Basically your average day is australia");
	}
	
	@EventHandler
	public void respawn(PlayerRespawnEvent e) {
		Power power = Application.PowerManager.getPower(e.getPlayer());
		if (!(power.getClass().equals(this.getClass()))) return;
		
		e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, Integer.MAX_VALUE, 3, false, false));
	}
	
	@EventHandler
	public void sneak(PlayerToggleSneakEvent e) {
		Power power = Application.PowerManager.getPower(e.getPlayer());
		if (!(power.getClass().equals(this.getClass()))) return;
		
		Player p = e.getPlayer();
		if (e.isSneaking()) {
			p.removePotionEffect(PotionEffectType.LEVITATION);
			p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, Integer.MAX_VALUE, 3, false, false));
		} else {
			p.removePotionEffect(PotionEffectType.SLOW_FALLING);
			p.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, Integer.MAX_VALUE, 5, false, false));
		}
	}
}
