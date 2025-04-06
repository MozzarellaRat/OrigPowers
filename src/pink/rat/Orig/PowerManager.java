package pink.rat.Orig;

import java.util.HashMap;
import java.util.UUID;
import java.util.function.Supplier;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import pink.rat.Powers.DummyPower;

public class PowerManager implements Listener {

    private final HashMap<String, Supplier<Power>> externalPowers = new HashMap<>();
    private final HashMap<UUID, Power> playerPowers = new HashMap<>();
   
    
    @SafeVarargs @Deprecated
    //After plethora of confirmation this is infact type safe, or is it? mwahhahahah!.
    public final void addPower(String ID, Supplier<Power>... power) { //Yeah uhm one id rules all ig? I'll fix this later, but it is a redundant method.
        for (Supplier<Power> p : power) externalPowers.put(ID, p);
    }
    /**
     * Used to add powers created "out of plugin".
     * 
     * @param ID Used to idenify and store the power on players.
     * @param power The power registed to the id.
     */
    public final void addPower(String ID, Supplier<Power> power) {
        externalPowers.put(ID, power);
    }
    
    public final Power getPower(Player player) {
    	Power p = playerPowers.get(player.getUniqueId());
    	if (p != null) return p;
        NamespacedKey key = new NamespacedKey(Application.getInstance(), "orig_power");
        PersistentDataContainer dataContainer = player.getPersistentDataContainer();
        String powerName = dataContainer.get(key, PersistentDataType.STRING);
        if (powerName == null) return new DummyPower();
        return getPowerFromString(powerName);
    }
    
    public Power getPowerFromString(String powerName) {
    	PowerType p = PowerType.fromString(powerName);
    	Power power = new DummyPower();
    	if (p != null) {
    		power = p.get();
    	} else {
    		Supplier<Power> i = externalPowers.get(powerName);
    		if (i != null) power = i.get();
    	}
        return power;
    }

    public final void printPowerDescription(Player player, Power power) {
        player.sendMessage(ChatColor.ITALIC + " " + ChatColor.GRAY + "Power: " + power.getFancyName());
        for (String description : power.getDescriptionName()) {
            player.sendMessage(" " + ChatColor.GRAY + description);
        }
    }

    public Power setPower(Player player, Power power) {
        UUID uuid = player.getUniqueId();
        Power currentPower = playerPowers.get(uuid);
        if (currentPower != null) {
            currentPower.powerDeactivate(player);
            HandlerList.unregisterAll(currentPower); 
        }
        power.powerActivate(player);
        Application.getInstance().getServer().getPluginManager().registerEvents(power, Application.getInstance());
        playerPowers.put(uuid, power);
        printPowerDescription(player, power);
        
        System.out.println("The power: " + power.getName() + " has been registered to player: " + player.getName() );
        
        return power;
    }

    public Power setPower(Player player, String powerName) {
        return setPower(player, getPowerFromString(powerName));
    }

    public void removePower(Player player) {
        UUID uuid = player.getUniqueId(); 
        Power currentPower = playerPowers.get(uuid);
        currentPower.powerDeactivate(player);
        HandlerList.unregisterAll(currentPower);
        playerPowers.remove(uuid);
        System.out.println("The power: " + currentPower.getName() + " has been deregistered from player: " + player.getName() );
    }
    
    public Boolean savePower(Player player ) {
        NamespacedKey key = new NamespacedKey(Application.getInstance(), "orig_power");
        PersistentDataContainer dataContainer = player.getPersistentDataContainer();

        Power currentPower = playerPowers.get(player.getUniqueId());
        if (currentPower != null) {
            dataContainer.set(key, PersistentDataType.STRING, currentPower.getName());
            return true;
        }
        return false;
    }
   

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Power p = getPower(player);
        setPower(player, p);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
    	if (savePower(event.getPlayer())) {
    		removePower(event.getPlayer());
    	}
    }
    
    @EventHandler
    public void playerChat(AsyncPlayerChatEvent event) {						
    	event.setFormat(getPower(event.getPlayer()).getFancyName()+ChatColor.GRAY+"%s » %s");
    }

}