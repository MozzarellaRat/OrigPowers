package pink.rat.Powers;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.util.Vector;

import pink.rat.Orig.Application;
import pink.rat.Orig.Power;

public class EggPower extends Power implements Listener {
public class EggPower extends Power {

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
		// TODO Auto-generated method stub
		return "egg";
	}

	@Override
	public String getFancyName() {
		// TODO Auto-generated method stub
		return hex("#ddff9b⭘#819954Egg#ddff9b⭘");
	}

	@Override
	public List<String> getDescriptionName() {
		// TODO Auto-generated method stub
		return Arrays.asList("egg?s");
	}
	
	@EventHandler
	public void playerChat(AsyncPlayerChatEvent e) {
		Power power = Application.PowerManager.getPower(e.getPlayer());
		if (!(power.getClass().equals(this.getClass()))) return;
		
		List<String> eggs = Arrays.asList("egg", "Egg", "eGg", "EGG", "eGg", "eGG", "Egg", "egg");
		List<String> end = Arrays.asList(".", "!", "~", "!?!?", " ", "?");
		int cn2 = e.getMessage().split(" ").length;
		String newString = "";
		Random rand = new Random();

		for (int i = 0; i < cn2; i++) {
		    newString = newString.concat(" "+eggs.get(rand.nextInt(eggs.size()))); 
		}

		newString = newString.concat(end.get(rand.nextInt(end.size())));
		e.setMessage(newString);
		System.out.println(newString);
	}
	
	@EventHandler
	public void playerSwapHands(PlayerSwapHandItemsEvent e) {
		Power power = Application.PowerManager.getPower(e.getPlayer());
		if (!(power.getClass().equals(this.getClass()))) return;
		
		Player p = e.getPlayer();
		if (getCooldown() <= 0) {
			e.setCancelled(true);
			
			Egg egg = (Egg) p.getWorld().spawnEntity(e.getPlayer().getEyeLocation(), EntityType.EGG);
		    Vector direction = p.getLocation().getDirection().normalize(); 
		    Random rand = new Random();
		    Vector velocity = direction.multiply(rand.nextDouble()*4); 
		    egg.setVelocity(velocity);
			setCooldown(0.25);
		}
	}
	
    public static String hex(String message) { //copied code
        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            String hexCode = message.substring(matcher.start(), matcher.end());
            String replaceSharp = hexCode.replace('#', 'x');
           
            char[] ch = replaceSharp.toCharArray();
            StringBuilder builder = new StringBuilder("");
            for (char c : ch) {
                builder.append("&" + c);
            }
           
            message = message.replace(hexCode, builder.toString());
            matcher = pattern.matcher(message);
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
