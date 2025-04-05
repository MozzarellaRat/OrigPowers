package pink.rat.Powers;

import java.util.function.Supplier;

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
        PowerManager = new PowerManager();
        DummyPower rp = new DummyPower();
        AustraliaPower r2 = new AustraliaPower();
        PowerManager.addPower(DummyPower::new);
        instance = this;
        this.getCommand("setpower").setExecutor(new Setpower());
        this.getCommand("power").setExecutor(new GetPower());
    }

    @Override
    public void onDisable() {}

    
    @EventHandler
    public void playerJoin(PlayerJoinEvent e) {
    	e.getPlayer().sendMessage("You'll die alone, afraid, gasping for life, pillow over your head and a smile on my face.");
    }
    
    public static Application getInstance() {
    	return instance;
    }
    
}
