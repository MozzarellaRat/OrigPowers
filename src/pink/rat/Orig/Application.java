package pink.rat.Orig;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import org.bukkit.plugin.java.JavaPlugin;

public class Application extends JavaPlugin implements Listener {
	protected static Application instance;
	public static PowerManager PowerManager;
	
    @Override
    public void onEnable() {
        System.out.println("The sparrow prince lies somewhere way up ahead.");
        instance = this;
        PowerManager = new PowerManager();
        PowerManager.startTicker();
        this.getCommand("setpower").setExecutor(new Setpower());
        this.getCommand("power").setExecutor(new GetPower());
        this.getServer().getPluginManager().registerEvents(PowerManager, this);
    }

    @Override
    public void onDisable() {
    	for (Player p : this.getServer().getOnlinePlayers()) {
    		PowerManager.savePower(p);
    		PowerManager.removePower(p);
    	}
    }

    
    @EventHandler
    public void playerJoin(PlayerJoinEvent e) {
    }
    
    public static Application getInstance() {
    	return instance;
    }
    
    public static PowerManager getPM() {
    	return PowerManager;
    }
    
}
