package pink.rat.Orig;

import java.util.List;

import org.bukkit.entity.Player;

public interface PowerInterface {
	
	public void powerActivate(Player p);
	public void powerDeactivate(Player p);

    String getName(); //Whenochainsama
    String getFancyName(); //Wifenloof
	List<String> getDescriptionName();
}